package ru.job4j.crud.persistent;

import ru.job4j.crud.model.User;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$. Singleton of MemoryStore.
 * @since 13.02.2019.
 */
public enum MemoryStore implements Store {
    INSTANCE;
    private Map<Integer, User> cache = new ConcurrentHashMap<>();
    private AtomicInteger sysId = new AtomicInteger(0);

    @Override
    public boolean add(final User user) {
        return this.notInList().apply(this.cache, user) &&
                this.cache.put(this.sysId.incrementAndGet(), user) == null;
    }

    @Override
    public boolean update(final int id, final User upd) {
        final boolean[] result = {true};
        this.cache.computeIfPresent(id, (integer, user) -> {
            if (upd.equals(user)) {
                return upd;
            }
            if (this.notInList().apply(cache, upd)) {
                return upd;
            }
            result[0] = false;
            return user;
        });
        return result[0];
    }

    @Override
    public boolean delete(final int id) {
        return this.cache.remove(id) != null;
    }

    @Override
    public Map<Integer, User> findAll() {
        return this.cache;
    }

    @Override
    public User findById(int id) {
        return this.cache.get(id);
    }

    private BiFunction<Map<Integer, User>, User, Boolean> notInList() {
        return (map, user) -> {
            Optional<User> search = map.values()
                    .stream()
                    .filter(presentUsr ->
                            presentUsr.getLogin().equals(user.getLogin()) || presentUsr.getEmail().equals(user.getEmail()))
                    .findFirst();
            return search.isEmpty();
        };
    }
}

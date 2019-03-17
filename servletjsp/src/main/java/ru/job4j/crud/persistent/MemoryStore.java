package ru.job4j.crud.persistent;

import ru.job4j.crud.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
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
    public boolean add(User user) {
        return this.cache.put(this.sysId.incrementAndGet(), user) == null;
    }

    @Override
    public boolean update(int id, User upd) {
        return this.cache.replace(id, upd) != null;
    }

    @Override
    public boolean delete(int id) {
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
}

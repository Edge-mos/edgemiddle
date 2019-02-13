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
        this.cache.put(this.sysId.incrementAndGet(), user);
        return true;
    }

    @Override
    public boolean update(int id, User upd) {
        this.cache.replace(id, upd);
        return true;
    }

    @Override
    public boolean delete(int id) {
        this.cache.remove(id);
        return true;
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

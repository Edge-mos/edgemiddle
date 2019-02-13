package ru.job4j.crud.logic;

import ru.job4j.crud.model.User;
import ru.job4j.crud.persistent.MemoryStore;
import ru.job4j.crud.persistent.Store;

import java.util.Map;
/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$. Singleton of ValidateService.
 * @since 13.02.2019.
 */
public enum ValidateService implements Validate {
    INSTANCE;
    private Store store = MemoryStore.INSTANCE;

    @Override
    public boolean add(User user) {
        if (!this.store.findAll().containsValue(user)) {
            return this.store.add(user);
        }
        return false;
    }

    @Override
    public boolean update(int id, User updUser) {
        if (this.check(id)) {
            return this.store.update(id, updUser);
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if (this.check(id)) {
            return this.store.delete(id);
        }
        return false;
    }

    @Override
    public Map<Integer, User> findAll() {
        if (!this.store.findAll().isEmpty()) {
            return this.store.findAll();
        }
        return null;
    }

    @Override
    public User findById(int id) {
        if (this.check(id)) {
            return this.store.findById(id);
        }
        return null;
    }

    private boolean check(int id) {
        return this.store.findAll().containsKey(id);
    }
}

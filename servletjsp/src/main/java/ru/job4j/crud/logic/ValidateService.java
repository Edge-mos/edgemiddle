package ru.job4j.crud.logic;

import ru.job4j.crud.model.User;
import ru.job4j.crud.persistent.MemoryStore;
import ru.job4j.crud.persistent.Store;

import java.util.Map;
import java.util.function.Function;

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
        return this.condition(this.store, str -> !str.findAll().containsValue(user) && str.add(user));
    }

    @Override
    public boolean update(int id, User updUser) {
        return this.condition(this.store, str ->
                str.findById(id) != null &&
                !str.findAll().containsValue(updUser) &&
                str.update(id, updUser));
    }

    @Override
    public boolean delete(int id) {
        return this.condition(this.store, str -> str.findAll().containsKey(id) && str.delete(id));
    }

    @Override
    public Map<Integer, User> findAll() {
        return this.store.findAll();
    }

    @Override
    public User findById(int id) {
        return this.condition(this.store, str -> str.findAll().containsKey(id)) ? this.store.findById(id) : null;
    }

    private boolean condition(Store str, Function<Store, Boolean> function) {
        return function.apply(str);
    }

}

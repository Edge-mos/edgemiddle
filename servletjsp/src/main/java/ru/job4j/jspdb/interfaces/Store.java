package ru.job4j.jspdb.interfaces;
import ru.job4j.jspdb.model.User;

import java.util.Map;

public interface Store<T> {
    boolean add(T user);
    boolean update(int id, T user);
    boolean delete(int id);
    Map<Integer, T> findAll();
    T findById(int id);
    void init();
    void drop();
}

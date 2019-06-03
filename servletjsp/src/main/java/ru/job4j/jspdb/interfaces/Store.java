package ru.job4j.jspdb.interfaces;
import ru.job4j.jspdb.model.User;

import java.util.Map;
import java.util.Optional;

public interface Store<T> {
    boolean add(T user);
    boolean update(int id, T user);
    boolean delete(int id);
    Map<Integer, T> findAll();
    T findById(int id);
    int getUserId(String login, String password);
    Optional<T> getLoggedUser(String login, String password);
    void init();
    void drop();
}

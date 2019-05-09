package ru.job4j.jspdb.interfaces;
import ru.job4j.crud.model.User;

import java.util.List;
import java.util.Map;

public interface ReStore {
    boolean add(User user);
    boolean update(int id, User upd);
    boolean delete(int id);
    Map<Integer, User> findAll();
    User findById(int id);
}

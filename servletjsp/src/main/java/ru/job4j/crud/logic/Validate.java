package ru.job4j.crud.logic;

import ru.job4j.crud.model.User;

import java.util.Map;

public interface Validate {

    boolean add(User user);
    boolean update(int id, User updUser);
    boolean delete(int id);
    Map<Integer, User> findAll();
    User findById(int id);
}

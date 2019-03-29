package ru.job4j.appusers.logic;

import ru.job4j.appusers.presentation.Html;
import ru.job4j.crud.model.User;
import ru.job4j.crud.persistent.MemoryStore;
import ru.job4j.crud.persistent.Store;

import java.util.*;
import java.util.function.Function;

public enum  ValidateService {
    INSTANCE;
    private Store store = MemoryStore.INSTANCE;
    private Map<String, Function<Map<String, String[]>, Map<String, String[]>>> dispatcher = new HashMap<>();

    private Function<Map<String, String[]>, String[]> addOperation() {
        return stringMap -> {
            User user = new User(
                    stringMap.get("name")[0],
                    stringMap.get("login")[0],
                    stringMap.get("email")[0],
                    stringMap.get("create")[0]);
            String op = stringMap.get("operation")[0];
            return this.store.add(user) ? Html.resultMessage(op, true, user) : Html.resultMessage(op, false, user);
        };
    }



    public Map<Integer, User> getAllUsers() {
        return this.store.findAll();
    }

    public Store getStore() {
        return this.store;
    }
}

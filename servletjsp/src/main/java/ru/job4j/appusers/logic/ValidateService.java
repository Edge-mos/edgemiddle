package ru.job4j.appusers.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.appusers.presentation.Utils;
import ru.job4j.crud.model.User;
import ru.job4j.crud.persistent.MemoryStore;
import ru.job4j.crud.persistent.Store;
import ru.job4j.crud.presentation.UserServlet;

import java.util.*;
import java.util.function.Function;

public enum  ValidateService {
    INSTANCE;
    private Store store = MemoryStore.INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServlet.class);
    private Map<String, Function<Map<String, String[]>, String[]>> dispatcher = Map.of(
            "ADD", this.addOperation(),
            "UPDATE", this.updateOperation(),
            "DELETE", this.deleteOperation()
    );

    private Function<Map<String, String[]>, String[]> addOperation() {
        return stringMap -> {
            User user = new User(
                    stringMap.get("name")[0],
                    stringMap.get("login")[0],
                    stringMap.get("email")[0],
                    stringMap.get("create")[0]);
            String op = stringMap.get("operation")[0];
            return this.store.add(user) ?
                    Utils.resultMessage(op, true, user) :
                    Utils.resultMessage(op, false, user);
        };
    }

    private Function<Map<String, String[]>, String[]> updateOperation() {
        return stringMap -> {
            User user = new User(
                    stringMap.get("name")[0],
                    stringMap.get("login")[0],
                    stringMap.get("email")[0],
                    stringMap.get("create")[0]);
            String op = stringMap.get("operation")[0];
            String updateId = stringMap.get("id")[0];
            return this.store.update(Integer.parseInt(updateId), user) ?
                    Utils.resultMessage(op, true, user) :
                    Utils.resultMessage(op, false, user);
        };
    }

    private Function<Map<String, String[]>, String[]> deleteOperation() {
        return stringMap -> {
            String deleteId = stringMap.get("id")[0];
            User user = this.findById(Integer.parseInt(deleteId));
            String op = stringMap.get("operation")[0];
            return this.store.delete(Integer.parseInt(deleteId)) ?
                    Utils.resultMessage(op, true, user) :
                    Utils.resultMessage(op, false, user);
        };
    }

    public Function<Map<String, String[]>, String[]> getOperation(String key) {
        return this.dispatcher.getOrDefault(key, stringMap -> {
            LOGGER.error(String.format("Key %s not found", key));
            throw new UnsupportedOperationException(String.format("Key %s not found", key));
        });
    }

    public Map<Integer, User> getAllUsers() {
        return this.store.findAll();
    }

    public User findById(int id) {
        Optional<User> result =  Optional.of(this.store.findById(id));
        return result.orElseGet(() -> new User("-", "-", "-", "-"));
    }

}

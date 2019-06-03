package ru.job4j.jspdb.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.jspdb.interfaces.Store;
import ru.job4j.jspdb.model.User;
import ru.job4j.jspdb.persistent.StoreDb;
import ru.job4j.jspdb.presentation.Utils;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class ValidateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateService.class);
    private final Store<User> store = StoreDb.getInstance();
    private static final ValidateService INSTANCE = new ValidateService();
    private Map<String, Function<Map<String, String[]>, String[]>> dispatcher = Map.of(
            "ADD", this.addOperation(),
            "UPDATE", this.updateOperation(),
            "DELETE", this.deleteOperation()
    );

    private ValidateService() {
    }

    public static ValidateService getInstance() {
        return INSTANCE;
    }

    private Function<Map<String, String[]>, String[]> addOperation() {
        return stringMap -> {
            User user = this.buildUser(stringMap).get();
            String operation = stringMap.get("operation")[0];
            return Utils.resultMessage(operation, this.store.add(user), user);
        };
    }

    private Function<Map<String, String[]>, String[]> updateOperation() {
        return stringMap -> {
            User user = this.buildUser(stringMap).get();
            String operation = stringMap.get("operation")[0];
            String updId = stringMap.get("id")[0];
            return Utils.resultMessage(operation, this.store.update(Integer.parseInt(updId), user), user);
        };
    }

    private Function<Map<String, String[]>, String[]> deleteOperation() {
        return stringMap -> {
            String operation = stringMap.get("operation")[0];
            String deleteId = stringMap.get("id")[0];
            User user = this.store.findById(Integer.parseInt(deleteId));
            return Utils.resultMessage(operation, this.store.delete(Integer.parseInt(deleteId)), user);
        };
    }

    public Function<Map<String, String[]>, String[]> getOperation(String operation) {
        return this.dispatcher.getOrDefault(operation, stringMap -> {
            LOGGER.error(String.format("Key %s not found", operation));
            throw new UnsupportedOperationException(String.format("Key %s not found", operation));
        });
    }

    public User findById(int id) {
        Optional<User> result = Optional.of(this.store.findById(id));
        return result.orElseGet(() -> new User("-", "-", "-", "-", "-"));
    }

    public Map<Integer, User> getAllUsers() {
        return this.store.findAll();
    }


    public Optional<User> findLoggedUser(String login, String password) {
        return this.store.getLoggedUser(login, password);
    }

    public int getUserId(String login, String password) {
        return this.store.getUserId(login, password);
    }

    private Supplier<User> buildUser(final Map<String, String[]> params) {
        return () -> new User(
                params.get("name")[0],
                params.get("login")[0],
                params.get("password")[0],
                params.get("email")[0],
                params.get("role")[0]
        );
    }

}

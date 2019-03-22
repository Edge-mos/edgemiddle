package ru.job4j.crud.logic;

import ru.job4j.crud.model.User;
import ru.job4j.crud.persistent.MemoryStore;
import ru.job4j.crud.persistent.Store;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$. Singleton of ValidateService.
 * @since 13.02.2019.
 */
public enum ValidateService {
    INSTANCE;
    private Store store = MemoryStore.INSTANCE;

    private final Map<String, Function<List<String>, String>> dispatcher = Map.of(
            "add", this.addOperation(),
            "delete", this.deleteOperation(),
            "update", this.updateOperation()
    );

    private Function<List<String>, String> addOperation() {
        return strings -> this.store.add(new User(strings.get(1), strings.get(2), strings.get(3), strings.get(4))) ?
                ValidateMsg.USER_ADD : ValidateMsg.UNABLE_ADD;
    }

    private Function<List<String>, String> deleteOperation() {
        return strings -> this.store.delete(Integer.parseInt(strings.get(1))) ?
                ValidateMsg.USER_DEL : ValidateMsg.UNABLE_DEL;
    }

    private Function<List<String>, String> updateOperation() {
        return strings -> this.store.update(Integer.parseInt(strings.get(1)),
                new User(strings.get(2), strings.get(3), strings.get(4), strings.get(5))) ?
                ValidateMsg.USER_UPD : ValidateMsg.UNABLE_UPD;
    }

    public Function<List<String>, String> getOperation(String key) {
      return this.dispatcher.getOrDefault(key, strings -> {
          throw new UnsupportedOperationException(String.format("Key %s not found", key));
      });
    }
}

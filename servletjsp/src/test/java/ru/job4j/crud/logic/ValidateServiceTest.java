package ru.job4j.crud.logic;

import org.junit.Test;
import ru.job4j.crud.model.User;
import ru.job4j.crud.persistent.MemoryStore;
import ru.job4j.crud.persistent.Store;

import java.util.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ValidateServiceTest {
    @Test
    public void whenAddAbsentUserThanTrue() {
        ValidateService validate = ValidateService.INSTANCE;
        List<String> params = List.of("add", "Ivan", "LOG", "google.com", "21/03/2019");
        String msg = validate.getOperation(params.get(0)).apply(params);
        assertThat(msg, is(ValidateMsg.USER_ADD));
    }

    @Test
    public void whenAddPresentUserThanFalse() {
        ValidateService validate = ValidateService.INSTANCE;
        List<String> params = List.of("add", "Ivan", "LOG", "google.com", "21/03/2019");
        String msg = validate.getOperation(params.get(0)).apply(params);
        String msgFalse = validate.getOperation(params.get(0)).apply(params);
        assertThat(msgFalse, is(ValidateMsg.UNABLE_ADD));
    }

    @Test
    public void whenDeleteUserFromListThanTrue() {
        Store store = MemoryStore.INSTANCE;
        ValidateService validate = ValidateService.INSTANCE;
        User user = new User("test", "testLogin", "testEmail", "11/12/2018");
        store.add(user);
        List<String> params = List.of("delete", "1");
        String msg = validate.getOperation(params.get(0)).apply(params);
        assertThat(msg, is(ValidateMsg.USER_DEL));
    }

    @Test
    public void whenDeleteAbsentUserThanFalse() {
        ValidateService validate = ValidateService.INSTANCE;
        List<String> params = List.of("delete", "1");
        String msg = validate.getOperation(params.get(0)).apply(params);
        assertThat(msg, is(ValidateMsg.UNABLE_DEL));
    }

    @Test
    public void whenUpdateCurrentUserByIdTAndSetUniqueLoginAndEmailThanTrue() {
        ValidateService validate = ValidateService.INSTANCE;
        Store store = MemoryStore.INSTANCE;
        User user = new User("test", "testLogin", "testEmail", "11/12/2018");
        store.add(user);
        List<String> params = List.of("update", "1", "test2", "testLogin2", "testEmail2", "12/12/2018");
        String msg = validate.getOperation(params.get(0)).apply(params);
        assertThat(msg, is(ValidateMsg.USER_UPD));
    }

    @Test
    public void whenUpdateUserThatNotInListThanFalse() {
        ValidateService validate = ValidateService.INSTANCE;
        List<String> params = List.of("update", "1", "test2", "testLogin2", "testEmail2", "12/12/2018");
        String msg = validate.getOperation(params.get(0)).apply(params);
        assertThat(msg, is(ValidateMsg.UNABLE_UPD));

    }
}
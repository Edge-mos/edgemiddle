package ru.job4j.crud.logic;

import org.junit.Test;
import ru.job4j.crud.model.User;

import java.util.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ValidateServiceTest {
    @Test
    public void whenAddAbsentUserThanTrue() {
        Validate validate = ValidateService.INSTANCE;
        User user = new User("test", "testLogin", "testEmail", "11/12/2018");
        assertThat(validate.add(user), is(true));
        assertThat(Objects.equals(validate.findById(1), user),is(true));
    }

    @Test
    public void whenAddPresentUserThanFalse() {
        Validate validate = ValidateService.INSTANCE;
        User user = new User("test", "testLogin", "testEmail", "11/12/2018");
        User user2 = new User("test", "testLogin", "testEmail", "11/12/2018");
        assertThat(validate.add(user), is(true));
        assertThat(validate.add(user2), is(false));
    }

    @Test
    public void whenDeleteUserFromListThanTrue() {
        Validate validate = ValidateService.INSTANCE;
        User user = new User("test", "testLogin", "testEmail", "11/12/2018");
        validate.add(user);
        assertThat(validate.delete(1), is(true));
    }

    @Test
    public void whenDeleteAbsentUserThanFalse() {
        Validate validate = ValidateService.INSTANCE;
        User user = new User("test", "testLogin", "testEmail", "11/12/2018");
        validate.add(user);
        validate.delete(1);
        assertThat(validate.delete(1), is(false));
        assertThat(validate.delete(2), is(false));
    }

    @Test
    public void whenUpdateCurrentUserByIdTAndSetUniqueLoginAndEmailThanTrue() {
        Validate validate = ValidateService.INSTANCE;
        User user = new User("test", "testLogin", "testEmail", "11/12/2018");
        validate.add(user);
        User updUser = new User("test2", "testLogin2", "testEmail2", "12/12/2018");
        assertThat(validate.update(1, updUser), is(true));
        assertThat(Objects.equals(validate.findById(1), updUser), is(true));
    }

    @Test
    public void whenUpdateUserThatNotInListThanFalse() {
        Validate validate = ValidateService.INSTANCE;
        User updUser = new User("test2", "testLogin2", "testEmail2", "12/12/2018");
        assertThat(validate.update(1, updUser), is(false));
    }

    @Test
    public void whenUpdateUserWithNonUniqueLoginAndEmailThanFalse() {
        Validate validate = ValidateService.INSTANCE;
        User user = new User("test", "Login", "Email@google.com", "11/12/2018");
        User user2 = new User("test2", "TEST", "Email@yandex.com", "11/12/2018");
        validate.add(user);
        validate.add(user2);
        assertThat(validate.update(2, new User("Some Name", "Login", "Email@google.com",
                "11/12/2018")), is(false));
    }
}
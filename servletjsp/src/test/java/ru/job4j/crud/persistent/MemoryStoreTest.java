package ru.job4j.crud.persistent;

import org.junit.Test;
import ru.job4j.appusers.presentation.Html;
import ru.job4j.crud.model.User;

import java.util.Arrays;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MemoryStoreTest {

    @Test
    public void whenAddAbsentUserThanTrue() {
        MemoryStore store = MemoryStore.INSTANCE;
        User user = new User("test", "testLogin", "testEmail", "11/12/2018");
        assertThat(store.add(user), is(true));
        assertThat(Objects.equals(store.findById(1), user),is(true));
    }

    @Test
    public void whenAddPresentUserThanFalse() {
        MemoryStore store = MemoryStore.INSTANCE;
        User user = new User("test", "login", "mail", "11/12/2018");
        User user2 = new User("test", "login", "mail", "11/12/2018");
        store.add(user);
        assertThat(store.add(user2), is(false));
    }

    @Test
    public void whenDeletePresentUserFromListThanTrue() {
        MemoryStore store = MemoryStore.INSTANCE;
        User user = new User("test", "testLogin", "testEmail", "11/12/2018");
        store.add(user);
        assertThat(store.delete(1), is(true));
    }

    @Test
    public void whenDeleteAbsentUserThanFalse() {
        MemoryStore store = MemoryStore.INSTANCE;
        User user = new User("test", "testLogin", "testEmail", "11/12/2018");
        store.add(user);
        store.delete(1);
        assertThat(store.delete(1), is(false));
        assertThat(store.delete(2), is(false));
    }

    @Test
    public void whenUpdateCurrentUserByIdTAndSetUniqueLoginAndEmailThanTrue() {
        MemoryStore store = MemoryStore.INSTANCE;
        User user = new User("test", "testLogin", "testEmail", "11/12/2018");
        store.add(user);
        User updUser = new User("test2", "testLogin2", "testEmail2", "12/12/2018");
        assertThat(store.update(1, updUser), is(true));
        assertThat(Objects.equals(store.findById(1), updUser), is(true));
    }

    @Test
    public void whenUpdateAbsentUserThanFalse() {
        MemoryStore store = MemoryStore.INSTANCE;
        User updUser = new User("test2", "testLogin2", "testEmail2", "12/12/2018");
        assertThat(store.update(1, updUser), is(false));
    }

    @Test
    public void whenUpdateUserWithNonUniqueLoginAndEmailThanFalse() {
        MemoryStore store = MemoryStore.INSTANCE;
        User user = new User("test", "Login", "Email@google.com", "11/12/2018");
        User user2 = new User("test2", "TEST", "Email@yandex.com", "11/12/2018");
        store.add(user);
        store.add(user2);
        assertThat(store.update(2, new User("Some Name", "Login", "Email@google.com",
                "11/12/2018")), is(false));

    }

    @Test
    public void whenUpdateUserWithNonUniqueLoginAndEmailThanFalse2() {
        MemoryStore store = MemoryStore.INSTANCE;
        User user = new User("test", "Login", "Email@google.com", "11/12/2018");
        store.add(user);
        assertThat(store.update(1, new User("Some Name", "Login", "Email@google.com",
                "11/12/2018")), is(true));

    }

    @Test
    public void test() {
        System.out.println(Arrays.toString(Html.resultMessage("ADD", true, new User("name", "login", "mail", "now"))));

    }
}
package ru.job4j.jspdb.persistent;

import org.junit.After;
import org.junit.Test;
import ru.job4j.jspdb.interfaces.Store;
import ru.job4j.jspdb.model.User;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class StoreDbTest {
    private final Store<User> sdb = StoreDb.getInstance();

//    @After
//    public void dropDb() {
//        this.sdb.drop();
//    }

    @Test
    public void whenAddAbsentUserThanTrue() {
        User user = new User("Ivan", "Login", "123", "Ivan@Yandex.ru", "ADMIN");
        assertThat(this.sdb.add(user), is(true));
        assertThat(this.sdb.findById(1), is(user));
    }

    @Test
    public void whenAddUserWithSameLoginOrEmailThanFalse() {
        User user = new User("Ivan", "Login", "123", "Ivan@Yandex.ru", "ADMIN");
        User user2 = new User("Petr", "Login", "456", "Ivan@Mail.ru", "USER");
        assertThat(this.sdb.add(user), is(true));
        assertThat(this.sdb.add(user2), is(false));
        assertThat(this.sdb.findAll().size(), is(1));
    }

    @Test
    public void whenUpdatePresentUserToUniqueLoginOrUniqueMailThanTrue() {
        User user = new User("Ivan", "Login", "123", "Yandex@ru", "USER");
        this.sdb.add(user);
        assertThat(this.sdb.update(1, new User(
                "Petr", "Petr", "456", "job4j@mail.ru", "ADMIN")),
                is(true));
    }

    @Test
    public void whenUpdatePresentUserToNonuniqueLoginOrNonniqueMailThanTrue() {
        User user = new User("Ivan", "Login", "123", "Yandex@ru", "USER");
        User user1 = new User("Petr", "Petr", "456", "job4j@mail.ru", "ADMIN");
        this.sdb.add(user);
        this.sdb.add(user1);
        assertThat(this.sdb.update(2,
                new User("Petr", "Login", "456", "Petr@inbox.ru", "ADMIN")), is(false));
        assertThat(this.sdb.update(2,
                new User("Petr", "Petr", "456", "Yandex@ru", "ADMIN")), is(false));
    }

    @Test
    public void whenDeletePresentUserThanSizeIsZero() {
        User user = new User("Ivan", "Login", "123", "Yandex@ru", "USER");
        this.sdb.add(user);
        assertThat(this.sdb.delete(1), is(true));
        assertThat(this.sdb.findAll().isEmpty(), is(true));
    }

    @Test
    public void whenUserIsPresentThanFindByIdIsTrue() {
        User user = new User("Ivan", "Login", "123", "Ivan@Yandex.ru", "ADMIN");
        User user2 = new User("Petr", "Log", "456", "Ivan@Mail.ru", "USER");
        this.sdb.add(user);
        this.sdb.add(user2);
        assertThat(this.sdb.findById(1), is(user));
        assertThat(this.sdb.findById(2), is(user2));
    }

}
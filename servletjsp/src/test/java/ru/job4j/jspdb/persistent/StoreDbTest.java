package ru.job4j.jspdb.persistent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.util.PSQLException;
import ru.job4j.crud.model.User;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StoreDbTest {
    private final StoreDb sdb = StoreDb.INSTANCE;

    @Before
    public void createTable() {
        this.sdb.initTable();
    }

    @After
    public void dropTable() {
        this.sdb.dropTable();
    }

    @Test
    public void whenAddAbsentUserThanTrueAndSizeMoreThanZero() {
        User user = new User("Ivan", "Log", "Ivan@gmail.com");
        assertThat(this.sdb.add(user), is(true));
        assertThat(this.sdb.findAll().size(), is(1));
        assertThat(this.sdb.findById(1), is(user));
    }

    public void whenAddUserWithSameLoginOrEmailThanFalse() {
        User user = new User("Ivan", "Log", "Ivan@gmail.com");
        User user1 = new User("Petr", "Log", "Test@gmail.com");
        User user2 = new User("TestName", "TestLog", "Ivan@gmail.com");
        this.sdb.add(user);
        assertThat(this.sdb.add(user1), is(false));
    }

    @Test
    public void whenUpdatePresentUserToUniqueLoginOrUniqueMailThanTrue() {
        User user = new User("Ivan", "Log", "Ivan@gmail.com");
        User user1 = new User("Petr", "Petr", "Petr@gmail.com");
        this.sdb.add(user);
        this.sdb.add(user1);
        assertThat(this.sdb.update(2, new User("Petr2", "Petr2", "Petr@inbox.ru")), is(true));
    }

    @Test
    public void whenUpdatePresentUserToNonuniqueLoginOrNonniqueMailThanTrue() {
        User user = new User("Ivan", "Log", "Ivan@gmail.com");
        User user1 = new User("Petr", "Petr", "Petr@gmail.com");
        this.sdb.add(user);
        this.sdb.add(user1);
        assertThat(this.sdb.update(2, new User("Petr", "Log", "Petr@inbox.ru")), is(false));
        assertThat(this.sdb.update(2, new User("Petr", "Petr", "Ivan@gmail.com")), is(false));
    }

    @Test
    public void whenUpdateCurrentPresentUserForNameAndNotUpdateUniqueValuesThanTrue() {
        User user = new User("Ivan", "Log", "Ivan@gmail.com");
        this.sdb.add(user);
        assertThat(this.sdb.update(1, new User("Petr", "Log", "Ivan@gmail.com")), is(true));
    }

    @Test
    public void whenDeletePresentUserThanSizeIsZero() {
        User user = new User("Ivan", "Log", "Ivan@gmail.com");
        this.sdb.add(user);
        assertThat(this.sdb.delete(1), is(this.sdb.findAll().isEmpty()));
    }

    @Test
    public void whenDeleteAbsentUserThanFalse() {
        User user = new User("Ivan", "Log", "Ivan@gmail.com");
        this.sdb.add(user);
        assertThat(this.sdb.delete(2), is(false));
    }

    @Test
    public void whenAddTwoUniqueUsersThanSizeIsTwo() {
        User user = new User("Ivan", "Log", "Ivan@gmail.com");
        User user1 = new User("Petr", "Petr", "Petr@gmail.com");
        this.sdb.add(user);
        this.sdb.add(user1);
        assertThat(this.sdb.findAll().size(), is(2));
    }

    @Test
    public void whenFindByIdPresentUserThanTrue() {
        User user = new User("Ivan", "Log", "Ivan@gmail.com");
        this.sdb.add(user);
        assertThat(this.sdb.findById(1), is(user));
    }
}
package ru.job4j.crud.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class User {
    private final String name;
    private final String login;
    private final String email;
    private final String createDate;

    public User(String name, String login, String email) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.createDate = null;
    }

    public User(final String name, final String login, final String email, final String createDate) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getCreateDate() {
        return createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(login, user.login) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, login, email);
    }

    @Override
    public String toString() {
        return String.format(
                "Name:%s, Login:%s, Email:%s, CreateDate:%s",
                this.name, this.login, this.email, this.createDate
        );
    }
}

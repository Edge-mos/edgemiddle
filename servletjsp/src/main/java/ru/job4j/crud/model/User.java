package ru.job4j.crud.model;

import java.util.Objects;

public class User {
    private String name;
    private String login;
    private String email;
    private String createDate;

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
        return login.equals(user.login) &&
                email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, email);
    }

    @Override
    public String toString() {
        return String.format(
                "Name:%s, Login:%s, Email:%s, CreateDate:%s",
                this.name, this.login, this.email, this.createDate
        );
    }
}

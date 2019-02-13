package ru.job4j.crud.model;

import java.util.Objects;

public class User {
    private String name;
    private String login;
    private String email;
    private String createDate;

    public User(String name, String login, String email, String createDate) {
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
                Objects.equals(email, user.email) &&
                Objects.equals(createDate, user.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, login, email, createDate);
    }

    @Override
    public String toString() {
        return "{ "
                .concat(" name= ")
                .concat(this.name)
                .concat(" login= ")
                .concat(this.login)
                .concat(" email= ")
                .concat(this.email)
                .concat(" create date= ")
                .concat(this.createDate)
                .concat(" }");
    }
}

package ru.job4j.jspdb.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class User {
    private final String name;
    private final String login;
    private final String password;
    private final String email;
    private final String role;
    private LocalDateTime createDate;
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
    public static final DateTimeFormatter ISOformatter = DateTimeFormatter.ISO_LOCAL_DATE;

    public User(String name, String login, String password, String email, String role) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.createDate = LocalDateTime.now();
    }

    public User(String name, String login, String password, String email, String role, LocalDateTime createDate) {
        this(name, login, password, email, role);
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, login, password, email, role);
    }

    @Override
    public String toString() {
        return String.format(
                "Name:%s, Login:%s, Password:%s Email:%s, Role:%s, CreateDate:%s",
                this.name, this.login, this.password, this.email, this.role, this.createDate.format(formatter)
        );
    }
}

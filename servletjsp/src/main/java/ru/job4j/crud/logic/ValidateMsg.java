package ru.job4j.crud.logic;

import ru.job4j.crud.model.User;

import java.util.Map;

public interface ValidateMsg {
    String USER_ADD = "User successfully added";
    String UNABLE_ADD = "Unable to add user";
    String USER_DEL = "User successfully deleted";
    String UNABLE_DEL = "No such user";
    String USER_UPD = "User successfully updated";
    String UNABLE_UPD = "Unable to update. No such user or incorrect data";


}

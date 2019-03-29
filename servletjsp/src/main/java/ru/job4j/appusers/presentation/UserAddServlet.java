package ru.job4j.appusers.presentation;

import ru.job4j.crud.model.User;
import ru.job4j.crud.persistent.Store;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class UserAddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Store store = Html.getSessionStore(req);
        Map<String, String[]> pm = req.getParameterMap();


        System.out.println("Параметры DoPost сервлета UserAdd: ");
        pm.forEach((s, strings) -> System.out.println(s + Arrays.toString(strings)));
        User user = new User(pm.get("name")[0], pm.get("login")[0], pm.get("email")[0], pm.get("create")[0]);
        store.add(user);
        // TODO: 3/27/19 сделать невидимую форму с сообщением
        RequestDispatcher dispatcher = req.getRequestDispatcher("/userslist");
        dispatcher.forward(req, resp);
    }
}

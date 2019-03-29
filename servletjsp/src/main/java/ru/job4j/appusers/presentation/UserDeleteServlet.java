package ru.job4j.appusers.presentation;

import ru.job4j.crud.persistent.Store;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Store store = Html.getSessionStore(req);
        store.delete(Integer.parseInt(req.getParameter("id")));
        RequestDispatcher dispatcher = req.getRequestDispatcher("/userslist");
        dispatcher.forward(req, resp);

        // TODO: 3/27/19 сделать невидимую форму с сообщением
    }
}

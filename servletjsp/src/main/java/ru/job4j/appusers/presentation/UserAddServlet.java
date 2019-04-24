package ru.job4j.appusers.presentation;

import ru.job4j.jspdb.logic.ValidateServiceDb;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class UserAddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        ValidateService store = Utils.getSessionStore(req);
        ValidateServiceDb store = (ValidateServiceDb) req.getSession().getAttribute("store"); // Это для DB

        Map<String, String[]> params = req.getParameterMap();
        String[] operations = store.getOperation(params.get("operation")[0]).apply(params);
        req.getSession().setAttribute("markupMessage", operations);

//        RequestDispatcher dispatcher = req.getRequestDispatcher("/userslist");
//        dispatcher.forward(req, resp);

//        RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/jspdb.jsp");
        dispatcher.forward(req, resp);
    }
}

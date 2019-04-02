package ru.job4j.appusers.presentation;

import ru.job4j.appusers.logic.ValidateService;
import ru.job4j.crud.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class UserUpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ValidateService store = Utils.getSessionStore(req);
        String id = req.getParameter("id");
        User user = store.findById(Integer.parseInt(id));
        this.showUpdateForm(resp, user, id, req.getContextPath());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> params = req.getParameterMap();
        ValidateService store = Utils.getSessionStore(req);
        String[] operations = store.getOperation(params.get("operation")[0]).apply(params);
        req.getSession().setAttribute("markupMessage", operations);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/userslist");
        dispatcher.forward(req, resp);
    }

    private void showUpdateForm(HttpServletResponse resp, User user, String updId, String contextPath) throws IOException {
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(Utils.updateMarkUp(user, updId, contextPath));
        writer.flush();
    }
}

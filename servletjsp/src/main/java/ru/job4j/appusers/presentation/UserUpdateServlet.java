package ru.job4j.appusers.presentation;

import ru.job4j.crud.model.User;
import ru.job4j.crud.persistent.Store;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

public class UserUpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Store store = Html.getSessionStore(req);
        String id = req.getParameter("id");
        User user = store.findById(Integer.parseInt(id));
        this.showUpdateForm(resp, user, id, req.getContextPath());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Store store = Html.getSessionStore(req);;
        Map<String, String[]> pm = req.getParameterMap();



        System.out.println("Параметры DoPost сервлета UserUpdate: ");

        pm.forEach((s, strings) -> System.out.println(s + Arrays.toString(strings)));

        User user = new User(pm.get("name")[0], pm.get("login")[0], pm.get("email")[0], pm.get("create")[0]);
        store.update(Integer.parseInt(pm.get("id")[0]), user);

        // TODO: 3/27/19 сделать невидимую форму с сообщением
        RequestDispatcher dispatcher = req.getRequestDispatcher("/userslist");
        dispatcher.forward(req, resp);
    }

    private void showUpdateForm(HttpServletResponse resp, User user, String updId, String contextPath) throws IOException {
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(Html.updateMarkUp(user, updId, contextPath));
        writer.flush();
    }


}

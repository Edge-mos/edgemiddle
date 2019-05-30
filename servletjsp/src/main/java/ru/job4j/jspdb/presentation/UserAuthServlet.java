package ru.job4j.jspdb.presentation;

import ru.job4j.jspdb.logic.ValidateService;
import ru.job4j.jspdb.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class UserAuthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/userAuth.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        ValidateService vs = ValidateService.getInstance();
        int idLogged = vs.findLoggedUser(login, password);

        if (idLogged != -1) {
            HttpSession session = req.getSession();

            session.setAttribute("validate", vs);
            session.setAttribute("markupMessage", new String[] {"", ""});
            User loggedUser = vs.findById(idLogged);
            session.setAttribute("loggedUser", loggedUser);
            req.getRequestDispatcher("/userslist").forward(req, resp);

        } else {
            req.setAttribute("absent", "User not found!");
            this.doGet(req, resp);
        }


    }
}

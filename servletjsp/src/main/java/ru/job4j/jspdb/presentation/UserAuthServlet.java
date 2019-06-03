package ru.job4j.jspdb.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/userAuth.jsp").forward(req, resp);
//        resp.sendRedirect(req.getContextPath().concat("/userAuth.jsp"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        System.out.println("POST AUTH");
        ValidateService vs = ValidateService.getInstance();

//        vs.findLoggedUser(login, password).ifPresent(user -> {
//            HttpSession session = req.getSession();
//            session.setAttribute("validate", vs);
//            session.setAttribute("markupMessage", new String[] {"", ""});
//            session.setAttribute("loggedUser", user);
//            try {
//                req.setAttribute("logged", new String[] {user.getRole(), "123"});
//                req.getRequestDispatcher("/userslist").forward(req, resp);
//            } catch (ServletException | IOException e) {
//                LOGGER.error(e.getMessage(), e);
//            }
//        });
        vs.findLoggedUser(login, password).ifPresent(user -> {
            HttpSession session = req.getSession();
            session.setAttribute("validate", vs);
            session.setAttribute("markupMessage", new String[] {"", ""});
            session.setAttribute("loggedUser", user);
            try {
                req.setAttribute("logged", new String[] {user.getRole(), "123"});
                req.getRequestDispatcher("/userslist").forward(req, resp);
            } catch (ServletException | IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        });
        req.setAttribute("absent", "User not found!");
        this.doGet(req, resp);
    }
}

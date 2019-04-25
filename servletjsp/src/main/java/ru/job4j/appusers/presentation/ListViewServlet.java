package ru.job4j.appusers.presentation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.appusers.logic.ValidateService;
import ru.job4j.crud.model.User;
import ru.job4j.crud.presentation.UserServlet;

public class ListViewServlet extends HttpServlet {
    // исходная метка
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        HttpSession session = req.getSession();
        ValidateService vs = (ValidateService) session.getAttribute("validate");
        String[] messages = (String[]) session.getAttribute("markupMessage");
        if (vs == null) {
            vs = ValidateService.INSTANCE;
            session.setAttribute("validate", vs);
        }
        if (messages == null) {
            messages = new String[]{"", ""};
            session.setAttribute("markupMessage", messages);
        }
        req.getRequestDispatcher("/usersList.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

}

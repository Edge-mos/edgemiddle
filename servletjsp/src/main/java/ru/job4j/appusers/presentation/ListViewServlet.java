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
import ru.job4j.crud.presentation.UserServlet;

public class ListViewServlet extends HttpServlet {
    private final ValidateService service = ValidateService.INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("store", this.service);
        String[] messages = (String[]) session.getAttribute("markupMessage");
        if (messages == null) {
            messages = new String[]{"", ""};
            session.setAttribute("markupMessage", messages);
        }
        this.show(messages[1], resp, req.getContextPath(), messages[0]);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    private void show(String msgColor, HttpServletResponse resp, String contextPath, String msg) throws IOException {
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        StringBuilder sb = new StringBuilder();
        this.service.getAllUsers().forEach((integer, user) -> sb.append(Utils.userMarkup(integer, user, contextPath)));
        writer.append(Utils.markup(msgColor, sb.toString(), contextPath, msg));
        writer.flush();
    }

}

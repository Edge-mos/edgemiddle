package ru.job4j.jspdb.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.jspdb.logic.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.function.Consumer;

public class ListViewServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListViewServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        //this.initSessionParams().accept(req);
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            req.getRequestDispatcher("/userauth").forward(req, resp);
        }
        req.getRequestDispatcher("/usersList.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

//    private Consumer<HttpServletRequest> initSessionParams() {
//        return request -> {
//            HttpSession session = request.getSession();
//            if (session.isNew()) {
//                session.setAttribute("validate", this.vs);
//                session.setAttribute("markupMessage", this.messages);
//            }
//        };
//    }
}

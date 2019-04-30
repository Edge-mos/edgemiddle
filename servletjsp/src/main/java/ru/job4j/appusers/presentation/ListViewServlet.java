package ru.job4j.appusers.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.crud.presentation.UserServlet;
import ru.job4j.jspdb.logic.ValidateServiceDb;

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
        this.initSessionParams().accept(req);
        req.getRequestDispatcher("/usersList.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    private Consumer<HttpServletRequest> initSessionParams() {
        return request -> {
          HttpSession session = request.getSession();
          if (session.isNew()) {
              ValidateServiceDb vs = ValidateServiceDb.INSTANCE;
              session.setAttribute("validate", vs);
              String[] messages = new String[]{"", ""};
              session.setAttribute("markupMessage", messages);
          }
        };
    }
}

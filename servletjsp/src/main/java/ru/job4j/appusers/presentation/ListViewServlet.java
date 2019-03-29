package ru.job4j.appusers.presentation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.appusers.logic.ValidateService;
import ru.job4j.crud.model.User;
import ru.job4j.crud.persistent.MemoryStore;
import ru.job4j.crud.persistent.Store;
import ru.job4j.crud.presentation.UserServlet;

public class ListViewServlet extends HttpServlet {
    //private final Store store = MemoryStore.INSTANCE; замена
    private final ValidateService service = ValidateService.INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServlet.class);

    @Override
    public void init() throws ServletException {
        Store store = this.service.getStore();
        store.add(new User("Test", "login", "yandex", "now"));
        store.add(new User("Test2", "testLOGIn", "google", "25/03/2019"));
        store.add(new User("Ivan", "Ivan", "mail", "25/03/2019"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Добавляем в сессию
        HttpSession session = req.getSession();
        session.setAttribute("store", this.store);
        this.show(resp, req.getContextPath());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("RETURN");
        this.doGet(req, resp);
    }

    private void show(HttpServletResponse resp, String contextPath) throws IOException {
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        StringBuilder sb = new StringBuilder();
        this.store.findAll().forEach((integer, user) -> sb.append(Html.userMarkup(integer, user, contextPath)));
        writer.append(Html.markup(sb.toString(), contextPath));
        writer.flush();
    }

}

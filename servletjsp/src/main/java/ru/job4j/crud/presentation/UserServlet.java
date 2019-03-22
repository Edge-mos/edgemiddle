package ru.job4j.crud.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.crud.logic.ValidateService;
import ru.job4j.crud.persistent.MemoryStore;
import ru.job4j.crud.persistent.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$. UserServlet.
 * @since 13.02.2019.
 */
public class UserServlet extends HttpServlet {
    private Store store = MemoryStore.INSTANCE;
    private ValidateService validate = ValidateService.INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        this.show(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        List<String> collect = req.getParameterMap().values().stream().flatMap(Arrays::stream).collect(Collectors.toList());
        String result = validate.getOperation(collect.get(0)).apply(collect);
        this.show(resp, result);
    }

    /**
     * Shows Users in list.
     * @param resp response param.
     */
    private void show(HttpServletResponse resp) {
        try (PrintWriter writer = new PrintWriter(resp.getOutputStream())) {
            writer.append("List of Users: \n");
            this.store.findAll().forEach((integer, user) -> writer.append(String.format("id: %d User: %s", integer, user)));
            writer.flush();
        } catch (IOException e) {
            LOGGER.error("Input/Output Error!", e);
        }
    }

    private void show(HttpServletResponse resp, String result) {
        try (PrintWriter writer = new PrintWriter(resp.getOutputStream())) {
            writer.append("Operation result: \n");
            writer.append(result);
            writer.flush();
        } catch (IOException e) {
            LOGGER.error("Input/Output Error!", e);
        }
    }
}

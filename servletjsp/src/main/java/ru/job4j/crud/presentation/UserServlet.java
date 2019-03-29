package ru.job4j.crud.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.crud.logic.ValidateService;
import ru.job4j.crud.model.User;
import ru.job4j.crud.persistent.MemoryStore;
import ru.job4j.crud.persistent.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        resp.setContentType("text/html; charset=utf-8");
        this.showResult("List of users:").accept(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        resp.setContentType("text/html; charset=utf-8");
        List<String> collect = req.getParameterMap().values().stream().flatMap(Arrays::stream).collect(Collectors.toList());
        String result = validate.getOperation(collect.get(0)).apply(collect);
        this.showResult(result).accept(resp);
    }

    private Consumer<HttpServletResponse> showResult(String msg) {
        return httpServletResponse -> {
            try(PrintWriter pw = new PrintWriter(httpServletResponse.getOutputStream())) {
                pw.append(msg.concat("\n"));
                this.store.findAll().forEach((integer, user) -> pw.append(String.format("id: %d User: %s", integer, user))
                        .append("\n"));
                pw.flush();
            } catch (IOException e) {
                LOGGER.error("Input/Output Error!", e);
            }
        };
    }
}

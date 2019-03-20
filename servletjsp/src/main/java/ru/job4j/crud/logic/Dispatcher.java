package ru.job4j.crud.logic;

import ru.job4j.crud.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$. UserServlet.
 * @since 20.03.2019.
 */
public class Dispatcher {
    private final Map<Actions, Consumer<Validate>> disp;

    public Dispatcher(final HttpServletRequest request) {
        this.disp = Map.of(
                Actions.ADD, this.actionAdd(request),
                Actions.DELETE, this.actionDel(request),
                Actions.UPDATE, this.actionUpd(request));
    }

    private Consumer<Validate> actionAdd(HttpServletRequest req) {
        return validate ->
                validate.add(new User(
                        req.getParameter("name"),
                        req.getParameter("login"),
                        req.getParameter("email"),
                        req.getParameter("create")
                )
        );
    }

    private Consumer<Validate> actionDel(HttpServletRequest req) {
        return validate ->
                validate.delete(Integer.parseInt(req.getParameter("id")));
    }

    private Consumer<Validate> actionUpd(HttpServletRequest req) {
        return validate ->
                validate.update(
                        Integer.parseInt(req.getParameter("id")),
                        new User(
                                req.getParameter("name"),
                                req.getParameter("login"),
                                req.getParameter("email"),
                                req.getParameter("create")
                ));
    }

    public Consumer<Validate> getAction(Actions key) {
        return disp.getOrDefault(key, validate -> {
            throw new UnsupportedOperationException(String.format("Key %s not found", key.toString()));
        });
    }
}

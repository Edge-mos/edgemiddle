package ru.job4j.jspdb.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.jspdb.logic.ValidateService;
import ru.job4j.jspdb.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface Utils {

    static String[] resultMessage(String op, Boolean res, User user) {
        String userPrint = String.format("User with Login:%s and Email:%s ", user.getLogin(), user.getEmail());
        Map<String, Map<Boolean, String[]>> dispMess =
                Map.of("ADD",
                        Map.of(true, new String[]{userPrint.concat("added."), "green"}, false, new String[]{userPrint.concat("can not be added!"), "red"}),
                        "UPDATE",
                        Map.of(true, new String[]{userPrint.concat("updated."), "green"}, false, new String[]{userPrint.concat("can not be updated!"), "red"}),
                        "DELETE",
                        Map.of(true, new String[]{userPrint.concat("deleted."), "green"}, false, new String[]{userPrint.concat("is absent!."), "red"}));
        return dispMess.get(op).get(res);
    }

    static void doPostProceed(HttpServletRequest req, HttpServletResponse resp, String className) {
        final Logger LOGGER = LoggerFactory.getLogger(className);
        try {
//            req.setCharacterEncoding("UTF-8");
//            resp.setContentType("text/html;charset=UTF-8");
            HttpSession session = req.getSession();

            ValidateService vs = (ValidateService) session.getAttribute("validate");
            Map<String, String[]> params = req.getParameterMap();
            //params.forEach((s, strings) -> System.out.println(String.format("%s : %s", s, strings[0])));
            String[] operations = vs.getOperation(params.get("operation")[0]).apply(params);
            req.getSession().setAttribute("markupMessage", operations);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/userslist");
            dispatcher.forward(req, resp);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Problem in " + className, e);
        } catch (ServletException er) {
            LOGGER.error("Problem in " + className, er);
        } catch (IOException err) {
            LOGGER.error("Problem in " + className, err);
        }

    }
}

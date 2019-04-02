package ru.job4j.appusers.presentation;

import ru.job4j.appusers.logic.ValidateService;
import ru.job4j.crud.model.User;
import ru.job4j.crud.persistent.Store;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public interface Utils {
    static String markup(String msgColor, String userMarkup, String contextPath, String msg) {
        return String.format("<!DOCTYPE html>" +
                "<html lang=\"en\" xmlns:float=\"http://www.w3.org/1999/xhtml\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <title>Users App</title>" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" +
                "    <style type=\"text/css\">" +
                "        .usersListDiv {" +
                "            width: 60%%;" +
                "            background: #C8DDFC;" +
                "            padding: 2px;" +
                "            margin: 10px;" +
                "        }" +
                "        .tableUsers {" +
                "            width: 100%%;" +
                "            min-width: 590px;" +
                "            text-align: center;" +
                "        }" +
                "        td {" +
                "            border: 1px solid #000;" +
                "        }" +
                "        #customTD {" +
                "            text-align: left;" +
                "            border: 1px;" +
                "        }" +
                "        #floatDiv {" +
                "            float: left;" +
                "            padding-left: 10px;" +
                "            padding-right: 20px;" +
                "        }" +
                "" +
                "        #inputCap {" +
                "            text-align: left;" +
                "        }" +
                "        .addFormDiv {" +
                "            margin-left: 10px;" +
                "            float: left;" +
                "            background: #C8FCD1;" +
                "            width: 60%%;" +
                "        }" +
                "        .inputFormDiv {" +
                "            width: 50%%;" +
                "            background: #C8FCD1;" +
                "            float: left;" +
                "            border: solid 1px black;" +
                "        }" +
                        ".resultArea {" +
                            "color: %s;" +
                        "}" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "<div class=\"usersListDiv\">" +
                "    <table class=\"tableUsers\">" +
                "        <caption><h2>List of users</h2></caption>" +
                "        <tr>" +
                "            <th>Name</th>" +
                "            <th>Login</th>" +
                "            <th>Email</th>" +
                "            <th>Create_date</th>" +
                "        </tr>" +
                "%s" +
                "    </table>" +
                "</div>" +
                "" +
                "<div class=\"addFormDiv\">" +
                "    <div class=\"inputFormDiv\">" +
                "        <form method=\"POST\" action=\"%s/useradd\">" +
                "            <table class=\"addTable\" cellpadding =\"7px\">" +
                "                <caption id=\"inputCap\"><h4>Add new user</h4></caption>" +
                "                <tr>" +
                "                    <input type=\"text\" hidden name=\"operation\" value=\"ADD\"/>" +
                "                    <td>Name:</td>" +
                "                    <td><input type=\"text\" name=\"name\"></td>" +
                "                </tr>" +
                "                <tr>" +
                "                    <td>Login:</td>" +
                "                    <td><input type=\"text\" name=\"login\"></td>" +
                "                </tr>" +
                "                <tr>" +
                "                    <td>Email:</td>" +
                "                    <td><input type=\"text\" name=\"email\"></td>" +
                "                </tr>" +
                "                <tr>" +
                "                    <td>Create date:</td>" +
                "                    <td><input type=\"text\" name=\"create\"></td>" +
                "                </tr>" +
                "                <tr>" +
                "                    <td><input type=\"submit\" value=\"Add\"></td>" +
                "                </tr>" +
                "            </table>" +
                "        </form>" +
                "    </div>" +
                "<div>" +
                    "<textarea class=\"resultArea\" cols=\"50\" rows=\"3\" readonly>%s</textarea>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>",msgColor, userMarkup, contextPath, msg);

    }

    static String userMarkup(Integer id, User user, String contextPath) {
        return String.format("<tr>" +
                        "            <td>%s</td>" +
                        "            <td>%s</td>" +
                        "            <td>%s</td>" +
                        "            <td>%s</td>" +
                        "            <td id=\"customTD\">" +
                        "                <div id=\"floatDiv\">" +
                        "                    <form action=\"%s/userdelete\" method=\"POST\">" +
                        "                        <input type=\"submit\" name=\"operation\" value=\"DELETE\"/>" +
                        "                        <input type=\"number\" hidden name=\"id\" value=\"%s\"/>" +
                        "                    </form>" +
                        "                </div>" +
                        "                <div>" +
                        "                    <form action=\"%s/userupdate\" method=\"GET\">" +
                        "                        <input type=\"submit\" name=\"operation\" value=\"UPDATE\"/>" +
                        "                        <input type=\"number\" hidden name=\"id\" value=\"%s\"/>" +
                        "                    </form>" +
                        "                </div>" +
                        "            </td>" +
                        "        </tr>",
                user.getName(), user.getLogin(),
                user.getEmail(), user.getCreateDate(),
                contextPath, id, contextPath, id);
    }

    static String updateMarkUp(User user, String updId, String contextPath) {
        return String.format("<!DOCTYPE html>" +
                "<html lang=\"en\" xmlns:float=\"http://www.w3.org/1999/xhtml\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <title>Update User</title>" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" +
                "    <style type=\"text/css\">" +
                "        #cap {" +
                "            text-align: left;" +
                "        }" +
                "        #outer {" +
                "            margin-left: 10px;" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "<div id=\"outer\">" +
                "    <form method=\"POST\" action=\"%s/userupdate\">" +
                "        <table cellpadding=\"7px\">" +
                "            <caption id=\"cap\"><h2>Update user</h2></caption>" +
                "            <tr>" +
                "                <input type=\"text\" hidden name=\"operation\" value=\"UPDATE\"/>" +
                "                <input type=\"text\" hidden name=\"id\" value=\"%s\"/>" +
                "                <td>Name:</td>" +
                "                <td><input type=\"text\" name=\"name\" value=\"%s\"></td>" +
                "            </tr>" +
                "            <tr>" +
                "                <td>Login:</td>" +
                "                <td><input type=\"text\" name=\"login\" value=\"%s\"></td>" +
                "            </tr>" +
                "            <tr>" +
                "                <td>Email:</td>" +
                "                <td><input type=\"text\" name=\"email\" value=\"%s\"></td>" +
                "            </tr>" +
                "            <tr>" +
                "                <td>Create date:</td>" +
                "                <td><input type=\"text\" name=\"create\" value=\"%s\"></td>" +
                "            </tr>" +
                "            <tr>" +
                "                <td><input type=\"submit\" value=\"Update\"></td>" +
                "            </tr>" +
                "        </table>" +
                "    </form>" +
                "</div>" +
                "</body>" +
                "</html>", contextPath, updId, user.getName(), user.getLogin(), user.getEmail(), user.getCreateDate());
    }

    static ValidateService getSessionStore(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return ((ValidateService) session.getAttribute("store"));
    }

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
}

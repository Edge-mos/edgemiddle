package ru.job4j.appusers.presentation;

import ru.job4j.crud.model.User;
import ru.job4j.crud.persistent.Store;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public interface Html {
    static String markup(String userMarkup, String contextPath) {
        return String.format("<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns:float=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>COMPLETE FORM V2</title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "    <style type=\"text/css\">\n" +
                "        .usersListDiv {\n" +
                "            width: 60%%;\n" +
                "            background: #fc0;\n" +
                "            padding: 2px;\n" +
                "            margin: 10px;\n" +
                "        }\n" +
                "        .tableUsers {\n" +
                "            width: 100%%;\n" +
                "            min-width: 590px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        td {\n" +
                "            border: 1px solid #000;\n" +
                "        }\n" +
                "        #customTD {\n" +
                "            text-align: left;\n" +
                "            border: 1px;\n" +
                "        }\n" +
                "        #floatDiv {\n" +
                "            float: left;\n" +
                "            padding-left: 10px;\n" +
                "            padding-right: 20px;\n" +
                "        }\n" +
                "\n" +
                "        #inputCap {\n" +
                "            text-align: left;\n" +
                "        }\n" +
                "        .addFormDiv {\n" +
                "            margin-left: 10px;\n" +
                "            float: left;\n" +
                "            background: deeppink;\n" +
                "            width: 60%%;\n" +
                "        }\n" +
                "        .inputFormDiv {\n" +
                "            width: 50%%;\n" +
                "            background: #9FD9A0;\n" +
                "            float: left;\n" +
                "            border: solid 1px black;\n" +
                "        }\n" +
                        ".resultArea {" +
                            "color: blue;" +
                        "}" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"usersListDiv\">\n" +
                "    <table class=\"tableUsers\">\n" +
                "        <caption><h2>List of users</h2></caption>\n" +
                "        <tr>\n" +
                "            <th>Name</th>\n" +
                "            <th>Login</th>\n" +
                "            <th>Email</th>\n" +
                "            <th>Create_date</th>\n" +
                "        </tr>\n" +
                "%s" +
                "    </table>\n" +
                "</div>\n" +
                "\n" +
                "<div class=\"addFormDiv\">\n" +
                "    <div class=\"inputFormDiv\">\n" +
                "        <form method=\"POST\" action=\"%s/useradd\">\n" +
                "            <table class=\"addTable\" cellpadding =\"7px\">\n" +
                "                <caption id=\"inputCap\"><h4>Add new user</h4></caption>\n" +
                "                <tr>\n" +
                "                    <input type=\"text\" hidden name=\"operation\" value=\"ADD\"/>\n" +
                "                    <td>Name:</td>\n" +
                "                    <td><input type=\"text\" name=\"name\"></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>Login:</td>\n" +
                "                    <td><input type=\"text\" name=\"login\"></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>Email:</td>\n" +
                "                    <td><input type=\"text\" name=\"email\"></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>Create date:</td>\n" +
                "                    <td><input type=\"text\" name=\"create\"></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td><input type=\"submit\" value=\"Add\"></td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </form>\n" +
                "    </div>\n" +
                "<div>" +
                    "<textarea class=\"resultArea\" cols=\"50\" rows=\"10\" readonly>!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!</textarea>" +
                "</div>" +
                "</div>\n" +
                "</body>\n" +
                "</html>", userMarkup, contextPath);

    }

    static String userMarkup(Integer id, User user, String contextPath) {
        return String.format("<tr>\n" +
                        "            <td>%s</td>\n" +
                        "            <td>%s</td>\n" +
                        "            <td>%s</td>\n" +
                        "            <td>%s</td>\n" +
                        "            <td id=\"customTD\">\n" +
                        "                <div id=\"floatDiv\">\n" +
                        "                    <form action=\"%s/userdelete\" method=\"POST\">\n" +
                        "                        <input type=\"submit\" name=\"operation\" value=\"DELETE\"/>\n" +
                        "                        <input type=\"number\" hidden name=\"id\" value=\"%s\"/>\n" +
                        "                    </form>\n" +
                        "                </div>\n" +
                        "                <div>\n" +
                        "                    <form action=\"%s/userupdate\" method=\"GET\">\n" +
                        "                        <input type=\"submit\" name=\"operation\" value=\"UPDATE\"/>\n" +
                        "                        <input type=\"number\" hidden name=\"id\" value=\"%s\"/>\n" +
                        "                    </form>\n" +
                        "                </div>\n" +
                        "            </td>\n" +
                        "        </tr>",
                user.getName(), user.getLogin(),
                user.getEmail(), user.getCreateDate(),
                contextPath, id, contextPath, id);
    }

    static String updateMarkUp(User user, String updId, String contextPath) {
        return String.format("<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns:float=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Update User</title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "    <style type=\"text/css\">\n" +
                "        #cap {\n" +
                "            text-align: left;\n" +
                "        }\n" +
                "        #outer {\n" +
                "            margin-left: 10px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div id=\"outer\">\n" +
                "    <form method=\"POST\" action=\"%s/userupdate\">\n" +
                "        <table cellpadding=\"7px\">\n" +
                "            <caption id=\"cap\"><h2>Update user</h2></caption>\n" +
                "            <tr>\n" +
                "                <input type=\"text\" hidden name=\"operation\" value=\"UPDATE\"/>\n" +
                "                <input type=\"text\" hidden name=\"id\" value=\"%s\"/>" +
                "                <td>Name:</td>\n" +
                "                <td><input type=\"text\" name=\"name\" value=\"%s\"></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>Login:</td>\n" +
                "                <td><input type=\"text\" name=\"login\" value=\"%s\"></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>Email:</td>\n" +
                "                <td><input type=\"text\" name=\"email\" value=\"%s\"></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>Create date:</td>\n" +
                "                <td><input type=\"text\" name=\"create\" value=\"%s\"></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td><input type=\"submit\" value=\"Update\"></td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "    </form>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>", contextPath, updId, user.getName(), user.getLogin(), user.getEmail(), user.getCreateDate());
    }

    static Store getSessionStore(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return ((Store) session.getAttribute("store"));
    }

    static String[] resultMessage(String op, Boolean res, User user) {
        String userPrint = String.format("User with Login: %s and Email: %s ", user.getLogin(), user.getEmail());
        Map<String, Map<Boolean, String[]>> dispMess =
                Map.of("ADD",
                        Map.of(true, new String[]{userPrint.concat("added."), "green"}, false, new String[]{userPrint.concat("can not be added!"), "red"}),
                        "UPDATE",
                        Map.of(true, new String[]{userPrint.concat("updated."), "green"}, false, new String[]{userPrint.concat("can not be updated!"), "red"}),
                        "DELETE",
                        Map.of(true, new String[]{userPrint.concat("deleted.")}, false, new String[]{userPrint.concat("is absent!."), "red"}));
        return dispMess.get(op).get(res);
    }
}

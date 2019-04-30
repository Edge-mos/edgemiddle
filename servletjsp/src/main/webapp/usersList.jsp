<%--
  Created by IntelliJ IDEA.
  User: edge
  Date: 4/11/19
  Time: 9:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.job4j.appusers.logic.ValidateService" %>
<%@ page import="ru.job4j.crud.model.User" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.io.IOException" %>
<%@ page import="ru.job4j.jspdb.logic.ValidateServiceDb" %>
<%
    ValidateServiceDb vs = (ValidateServiceDb) session.getAttribute("validate");
    String[] messages = (String[]) session.getAttribute("markupMessage");
%>

<!DOCTYPE html>
<html>
<head>
    <!--<meta http-equiv="Content-Type" content="text/html"; charset="UTF-8"> -->
    <meta charset="UTF-8">
    <title>Jsp App</title>
    <link rel="stylesheet" href="mainjsp.css">
</head>
<body>
<div class="container">
    <div class="table_layer">
        <table>
            <caption><h3>Users List</h3></caption>
            <tr>
                <th>id</th>
                <th>Name</th>
                <th>Login</th>
                <th>Mail</th>
                <th>Create_date</th>
            </tr>
            <%
                // Проходим список пользователей и добавляем в форму
                for (Map.Entry<Integer, User> entry : vs.getAllUsers().entrySet()) {
            %>
            <tr>
                <td><%=entry.getKey()%></td>
                <td><%=entry.getValue().getName()%></td>
                <td><%=entry.getValue().getLogin()%></td>
                <td><%=entry.getValue().getEmail()%></td>
                <td><%=entry.getValue().getCreateDate().substring(0, 19)%></td>
                <td class="td_form">
                    <form class="form" method="POST" action="<%=request.getContextPath()%>/userdelete">
                        <input class="form_btn delBtn" type="submit" name="operation" value="DELETE"/>
                        <input type="number" hidden name="id" value="<%=entry.getKey().toString()%>"/>
                    </form>
                    <form class="form" method="POST" action="<%=request.getContextPath()%>/userupd.jsp" >
                        <input class="form_btn updBtn" type="submit" name="operation" value="UPDATE"/>
                        <input type="number" hidden name="id" value="<%=entry.getKey().toString()%>"/>
                    </form>
                </td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
    <div class="list_count">users in list: <%= vs.getAllUsers().size()%></div>

    <div class="container_footer">
        <div class="add_user">
            <p>Add new user</p>
            <form method="POST" action="<%=request.getContextPath()%>/useradd">
                <input type="text" hidden name="operation" value="ADD"/>
                <input class="vis" type="text" name="name" placeholder="Name">
                <input class="vis" type="text" name="login" placeholder="Login">
                <input class="vis" type="text" name="email" placeholder="Email">
               <!-- <input class="vis" type="text" name="create" placeholder="Create_date"> -->
                <input class="add_btn" type="submit" value="ADD">
            </form>

        </div>
        <div class="message">
            <span style="color:<%=messages[1]%> "><%=messages[0]%></span>
            <%
                messages[0] = "";
                messages[1] = "";
            %>
        </div>
    </div>
</div>
</body>
</html>



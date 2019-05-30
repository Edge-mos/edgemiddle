<%--
  Created by IntelliJ IDEA.
  User: edge
  Date: 4/11/19
  Time: 9:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Jsp App</title>
    <link rel="stylesheet" href="mainjsp.css">
</head>
<body>
<div class="container">
    <jsp:useBean id="loggedUser" scope="session" type="ru.job4j.jspdb.model.User"/>
    <span>Welcome: ${loggedUser.login}. Granted as: ${loggedUser.role}. LogOUT.</span>
    <div class="table_layer">
        <table>
            <caption><h3>Users List</h3></caption>
            <tr>
                <th>id</th>
                <th>Name</th>
                <th>Login</th>
                <th>Password</th>
                <th>Mail</th>
                <th>Role</th>
                <th>Create_date</th>
            </tr>
            <jsp:useBean id="validate" scope="session" type="ru.job4j.jspdb.logic.ValidateService"/>
            <c:forEach var="user" items="${validate.allUsers}">
                <tr>
                    <td><c:out value="${user.key}"/></td>
                    <td><c:out value="${user.value.name}"/></td>
                    <td><c:out value="${user.value.login}"/></td>
                    <td><c:out value="${user.value.password}"/></td>
                    <td><c:out value="${user.value.email}"/></td>
                    <td><c:out value="${user.value.role}"/></td>
                    <td><c:out value="${user.value.createDate.format(user.value.formatter)}"/></td>
                    <td class="td_form">
                        <form class="form" method="POST" action="${pageContext.servletContext.contextPath}/userdelete">
                            <input class="form_btn delBtn" type="submit" name="operation" value="DELETE"/>
                            <input type="number" hidden name="id" value="<c:out value="${user.key}"/>"/>
                        </form>
                        <form class="form" method="POST" action="${pageContext.servletContext.contextPath}/userupd.jsp" >
                            <input class="form_btn updBtn" type="submit" name="operation" value="UPDATE"/>
                            <input type="number" hidden name="id" value="<c:out value="${user.key}"/>"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="list_count">
        users in list: <c:out value="${validate.allUsers.size()}"/>
    </div>
    <div class="container_footer">
        <div class="add_user">
            <p>Add new user</p>
            <form method="POST" action="${pageContext.servletContext.contextPath}/useradd">
                <input type="text" hidden name="operation" value="ADD"/>
                <input class="vis" type="text" name="name" placeholder="Name">
                <input class="vis" type="text" name="login" placeholder="Login">
                <input class="vis" type="text" name="password" placeholder="Password">
                <input class="vis" type="text" name="email" placeholder="Email">
                <div class="selector">
                    Role: <select name="role">
                    <option value="USER">USER</option>
                    <option value="ADMIN">ADMIN</option>
                    </select>
                </div>
                <input class="add_btn" type="submit" value="ADD">
            </form>
        </div>
        <div class="message">
            <jsp:useBean id="markupMessage" scope="session" type="java.lang.String[]"/>
            <span style="color:<c:out value="${markupMessage[1]}"/>"><c:out value="${markupMessage[0]}"/></span>
            ${markupMessage[0] = ""}
            ${markupMessage[1] = ""}
        </div>
    </div>
</div>
</body>
</html>



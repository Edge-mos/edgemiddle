<%--
  Created by IntelliJ IDEA.
  User: edge
  Date: 6/1/19
  Time: 2:01 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>USER PAGE</title>
    <link rel="stylesheet" href="mainjsp.css">
</head>
<body>
<div class="container">
    <%--<jsp:useBean id="loggedUser" scope="session" type="ru.job4j.jspdb.model.User"/>--%>
    <span>Welcome: ${loggedUser.login}. Granted as: ${loggedUser.role}.
        <c:set var="id" value="${validate.getUserId(loggedUser.login, loggedUser.password)}"/>
        <a href="${pageContext.servletContext.contextPath}/logout">LogOut</a></span>

    <div class="table_layer">
        <table>
            <caption><h3>Users List</h3></caption>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Login</th>
                <th>Password</th>
                <th>Mail</th>
                <th>Role</th>
                <th>Create_date</th>
            </tr>
                <tr>
                    <td><c:out value="${id}"/></td>
                    <td><c:out value="${loggedUser.name}"/></td>
                    <td><c:out value="${loggedUser.login}"/></td>
                    <td><c:out value="${loggedUser.password}"/></td>
                    <td><c:out value="${loggedUser.email}"/></td>
                    <td><c:out value="${loggedUser.role}"/></td>
                    <td><c:out value="${loggedUser.createDate.format(loggedUser.formatter)}"/></td>
                    <td class="td_form">
                        <form class="form" method="POST" action="${pageContext.servletContext.contextPath}/userdelete">
                            <input class="form_btn delBtn" type="submit" name="operation" value="DELETE"/>
                            <input type="number" hidden name="id" value="<c:out value="${id}"/>"/>
                        </form>
                        <form class="form" method="POST" action="${pageContext.servletContext.contextPath}/userupd.jsp" >
                            <input class="form_btn updBtn" type="submit" name="operation" value="UPDATE"/>
                            <input type="number" hidden name="id" value="<c:out value="${id}"/>"/>
                        </form>
                    </td>
                </tr>
        </table>
    </div>
    <div class="container_footer">
        <div class="message">
            <%--<jsp:useBean id="markupMessage" scope="session" type="java.lang.String[]"/>--%>
            <span style="color:<c:out value="${markupMessage[1]}"/>"><c:out value="${markupMessage[0]}"/></span>
            ${markupMessage[0] = ""}
            ${markupMessage[1] = ""}
        </div>
    </div>
</div>
</body>
</html>


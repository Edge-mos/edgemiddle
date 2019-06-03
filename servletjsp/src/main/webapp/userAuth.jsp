<%--
  Created by IntelliJ IDEA.
  User: edge
  Date: 5/29/19
  Time: 1:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page session="false" %>

<!DOCTYPE html>
<html>
<head>
    <title>User authentication</title>
    <link rel="stylesheet" href="userauth.css">
</head>
<body>

<div class="welc_mess">
    Insert your Login And Password
</div>
<div class="user_auth">

    <form method="POST" action="${pageContext.servletContext.contextPath}/userauth">
        <input type="text" hidden name="operation" value="AUTH"/>
        <input class="vis" type="text" name="login" placeholder="Login">
        <input class="vis" type="password" name="password" placeholder="Password">
        <input class="login_btn" type="submit" value="LogIn">
    </form>
</div>
<c:if test="${absent != ''}">
    <div>
        <c:out value="${absent}"/>
    </div>
</c:if>



</body>
</html>

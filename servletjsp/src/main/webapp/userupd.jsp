<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="validate" scope="session" type="ru.job4j.jspdb.logic.ValidateServiceDb"/>
<c:set var="updUser" value="${validate.findById(param['id'])}"/>
<!DOCTYPE html>
<html>
<head>
    <title>User Update</title>
    <link rel="stylesheet" href="userupd.css">
</head>
<body>
    <div class="container_upd">
        <span class="caption">Update user</span>
        <form method="POST" action="${pageContext.servletContext.contextPath}/userupdate">
            <input type="text" hidden name="operation" value="UPDATE"/>
            <input type="text" hidden name="id" value="${param['id']}"/>
            <div>
                Name:<input class="vis" type="text" name="name" value="${updUser.name}">
            </div>
            <div>
                Login:<input class="vis" type="text" name="login" value="${updUser.login}">
            </div>
            <div>
                Email:<input class="vis" type="text" name="email" value="${updUser.email}">
            </div>
            <input class="upd_btn" type="submit" value="Update">
        </form>
        <div class="ref"><a href="${pageContext.servletContext.contextPath}/userslist">back to users list</a></div>
    </div>
</body>
</html>

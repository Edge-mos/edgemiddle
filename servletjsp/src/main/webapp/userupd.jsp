<%@ page import="ru.job4j.appusers.logic.ValidateService" %>
<%@ page import="ru.job4j.appusers.presentation.Utils" %>
<%@ page import="ru.job4j.crud.model.User" %>
<%@ page import="ru.job4j.jspdb.logic.ValidateServiceDb" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
//    ValidateService vs = (ValidateService) session.getAttribute("validate"); без базы данных
    ValidateServiceDb vs = (ValidateServiceDb) session.getAttribute("validate");
    String id = request.getParameter("id");
    User user = vs.findById(Integer.parseInt(id));
%>
<!DOCTYPE html>
<html>
<head>
    <title>User Update</title>
    <link rel="stylesheet" href="userupd.css">
</head>
<body>
    <div class="container_upd">
        <span class="caption">Update user</span>
        <form method="POST" action="<%=request.getContextPath()%>/userupdate">
            <input type="text" hidden name="operation" value="UPDATE"/>
            <input type="text" hidden name="id" value="<%=id%>"/>
            <div>
                Name:<input class="vis" type="text" name="name" value="<%=user.getName()%>">
            </div>
            <div>
                Login:<input class="vis" type="text" name="login" value="<%=user.getLogin()%>">
            </div>
            <div>
                Email:<input class="vis" type="text" name="email" value="<%=user.getEmail()%>">
            </div>
            <input class="upd_btn" type="submit" value="Update">
        </form>
        <div class="ref"><a href="<%=request.getContextPath()%>/userslist">back to users list</a></div>
    </div>
</body>
</html>

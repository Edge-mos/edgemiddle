<%@ page import="ru.job4j.appusers.logic.ValidateService" %>
<%@ page import="ru.job4j.appusers.presentation.Utils" %>
<%@ page import="ru.job4j.crud.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ValidateService store = Utils.getSessionStore(request);
    String id = request.getParameter("id");
    User user = store.findById(Integer.parseInt(id));
%>
<!DOCTYPE html>
<html>
<head>
    <title>User Update</title>
    <link rel="stylesheet" href="userupd.css">
</head>
<body>
    <div class="container_upd">
        <span>Update user</span>
        <form method="POST" action="<%=request.getContextPath()%>/userupdate">
            <input type="text" hidden name="operation" value="UPDATE"/>
            <input type="text" hidden name="id" value="<%=id%>"/>
            <input class="vis" type="text" name="name" value="<%=user.getName()%>">
            <input class="vis" type="text" name="login" value="<%=user.getLogin()%>">
            <input class="vis" type="text" name="email" value="<%=user.getEmail()%>">
            <input class="vis" type="text" name="create" value="<%=user.getCreateDate()%>">
            <input class="upd_btn" type="submit" value="Update">
        </form>
        <div class="ref"><a href="<%=request.getContextPath()%>/index.jsp">back to users list</a></div>
    </div>
</body>
</html>

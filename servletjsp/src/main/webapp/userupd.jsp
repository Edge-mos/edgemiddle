<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="validate" scope="session" type="ru.job4j.jspdb.logic.ValidateService"/>
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

            <div class="tab">
                <table>
                    <tr>
                        <td class="properties">
                            Name:
                        </td>
                        <td class="content">
                            <input type="text" name="name" value="${updUser.name}">
                        </td>
                    </tr>
                    <tr>
                        <td class="properties">
                            Login:
                        </td>
                        <td class="content">
                            <input type="text" name="login" value="${updUser.login}">
                        </td>
                    </tr>
                    <tr>
                        <td class="properties">
                            Password:
                        </td>
                        <td class="content">
                            <input type="text" name="password" value="${updUser.password}">
                        </td>
                    </tr>
                    <tr>
                        <td class="properties">
                            Email:
                        </td>
                        <td class="content">
                            <input type="text" name="email" value="${updUser.email}">
                        </td>
                    </tr>
                    <tr>
                        <td class="properties">
                            <span class="role">Role:</span>
                        </td>
                        <td class="content">
                            <select class="selector" name="role">
                                <option selected value="${updUser.role}"> ${updUser.role} </option>
                                <option value="USER">USER</option>
                                <option value="ADMIN">ADMIN</option>
                            </select>
                        </td>
                    </tr>
                </table>
            </div>
            <input class="upd_btn" type="submit" value="Update">
        </form>
        <div class="ref"><a href="${pageContext.servletContext.contextPath}/userslist">back to users list</a></div>
    </div>
</body>
</html>

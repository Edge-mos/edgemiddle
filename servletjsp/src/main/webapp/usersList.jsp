<%--
  Created by IntelliJ IDEA.
  User: edge
  Date: 4/11/19
  Time: 9:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<c:if test="${loggedUser.role == 'ADMIN'}">
    <%@ include file="admin.jsp" %>
</c:if>
<%@ include file="user.jsp" %>




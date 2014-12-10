<%--
  Created by IntelliJ IDEA.
  User: Andreea
  Date: 25/11/14
  Time: 19:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    <%
        out.println("Welcome " + session.getAttribute("username"));

    %>
</body>
</html>

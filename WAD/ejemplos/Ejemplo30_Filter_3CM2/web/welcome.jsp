<%-- 
    Document   : welcome
    Created on : 5/10/2015, 07:45:21 AM
    Author     : Dragon1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Welcome! <%=session.getAttribute("userName") %></h1>
    </body>
</html>

<%@page import="admin.LoginBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <%! LoginBean loginBean = new LoginBean();%>
        <% if (loginBean.validateUser(request.getParameter("id"), request.getParameter("pass"))) {
                session.setAttribute("user", request.getParameter("id"));
                out.println("Welcome " + request.getParameter("id"));
        %>
        <%
            } else {
                out.println("Not a valid User or password");
            }
        %>
    </body>
</html>

<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Set"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Success</title>
    </head>
    <body>
        <h1>Welcome  <%= session.getAttribute("user")%></h1>
        <% if (session.getAttribute("type").equals("admin")) {%>
        <table>
            <thead>
                <th>User</th>
                <th>Password</th>
                <th>Type</th>
            </thead>
            <% HashMap<String, String[]> users = (HashMap<String, String[]>) session.getAttribute("users");
                for (Map.Entry<String, String[]> pair : users.entrySet()) {
            %>
            <tr><td><%= pair.getKey() %></td><td><%= pair.getValue()[0] %></td><td><%= pair.getValue()[1] %></td></tr>
            <% } %>

        </table>
        <% }%>

</body>
</html>

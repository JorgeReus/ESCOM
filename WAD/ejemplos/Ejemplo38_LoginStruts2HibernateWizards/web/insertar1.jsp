<%@taglib  uri="/struts-tags" prefix="s" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insertar</title>
    </head>
    <body>
        <s:form action="Insertar" >
            <s:textfield name="idLogin" key="id:" />
            <s:textfield name="userName" key="User:" /> 
            <s:textfield name="password" key="password:" />            
            <s:submit value="Insertar" />
            <s:actionmessage  />
        </s:form>
    </body>
</html>

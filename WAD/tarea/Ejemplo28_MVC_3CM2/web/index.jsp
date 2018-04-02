<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html:html>
    <head>
        
    </head>
    <body style="background-color: white">
        <html:form action="MVC" method="get">
            <html:text property="parametro1"></html:text>
            <html:submit/>
        </html:form>
    </body>
</html:html>
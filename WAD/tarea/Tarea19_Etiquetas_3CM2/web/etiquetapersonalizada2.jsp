<%@ taglib uri="/WEB-INF/bodyp.tld" prefix="QueryBody" %>
<html>
    <body>
        <QueryBody:Select>
            * FROM Country;
        </QueryBody:Select>
        <QueryBody:Insert>
            ('Finland')
        </QueryBody:Insert>
        <QueryBody:Update>
            Peru, 6
        </QueryBody:Update>
    </body>
</html>

package com.escom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import static javax.servlet.jsp.tagext.Tag.EVAL_PAGE;

public class UpdateBody extends BodyTagSupport {

    private String lineSep = ",";

    public int doEndTag() throws JspException {
        String bodyText = bodyContent.getString();
        String[] values = bodyText.split(lineSep);
        try {
            Statement s;
            Connection con = null;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/Countries", "root", "root");
            s = con.createStatement();
            s.executeUpdate("UPDATE Country SET name='" + values[0].trim() + "' where id=" + values[1].trim());
            con.close();
        } catch (SQLException e) {
            System.err.println("Error sql al intentar conectarse con la base de datos");
            e.printStackTrace();
        } catch (ClassNotFoundException ee) {
            System.err.println("No se puede encontrar la clase:" + ee);
        }
        return EVAL_PAGE;
    }

}

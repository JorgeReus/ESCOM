package com.escom;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class SelectBody extends BodyTagSupport
{
    public int doEndTag() throws JspException
    {
       String bodyText = bodyContent.getString();

        try {
            Statement s;
            ResultSet rs;
            Connection con = null;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/Countries", "root", "root");
            s = con.createStatement();
            rs = s.executeQuery("SELECT " + bodyText);
            while (rs.next()) {
                pageContext.getOut().println(rs.getString("name") + "<br>");
            }
            con.close();
        } catch (IOException e) {
            System.err.println(e);
        } catch (SQLException e) {
            System.err.println("Error sql al intentar conectarse con la base de datos");
            e.printStackTrace();
        } catch (ClassNotFoundException ee) {
            System.err.println("No se puede encontrar la clase:" + ee);
        }
       return EVAL_PAGE;
    }

}


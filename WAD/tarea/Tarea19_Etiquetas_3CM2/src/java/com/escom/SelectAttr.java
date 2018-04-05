package com.escom;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.jsp.tagext.*;

public class SelectAttr extends TagSupport {

    private String queryString;

    public void setqueryString(String queryString) {
        this.queryString = queryString;
    }

    @Override
    public int doEndTag() {
        try {
            Statement s;
            ResultSet rs;
            Connection con = null;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/Countries", "root", "root");
            s = con.createStatement();
            rs = s.executeQuery("SELECT " + queryString);
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

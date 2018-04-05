package com.escom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.jsp.tagext.*;
import static javax.servlet.jsp.tagext.Tag.EVAL_PAGE;

public class UpdateAttr extends TagSupport {

    private String id;
    private String name;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int doEndTag() {
        try {
            Statement s;
            Connection con = null;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/Countries", "root", "root");
            s = con.createStatement();
            s.executeUpdate("update Country set name='" + name + "' where id=" + id);
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

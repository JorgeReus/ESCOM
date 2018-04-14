package com.myapp.struts;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Reus Gaming PC
 */
public class LoginBean {

    private Map<String, String[]> users = new HashMap<>();

    public LoginBean() throws IOException, PropertyVetoException {
        try {
            Statement s;
            ResultSet rs;
            Connection con = DataSource.getInstance().getConnection();
            s = con.createStatement();
            rs = s.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                String user = rs.getString("user");
                String[] values = {rs.getString("password"), rs.getString("type")};
                users.put(user, values);
            }
            con.close();
        } catch (SQLException e) {
            System.err.println("Error sql al intentar conectarse con la base de datos");
            e.printStackTrace();
        }
    }

    public boolean validate(String user, String password) {
        boolean isValid = false;
        if (users.get(user)[0].equals(password)) {
            isValid = true;
        }
        return isValid;
    }

    public String getType (String user) {
        return users.get(user)[1];
    }

    public Map<String, String[]> getUsers() {
        return users;
    }
    
    
    
}

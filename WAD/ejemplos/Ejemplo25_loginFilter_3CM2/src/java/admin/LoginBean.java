package admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 *
 * @author reus
 */
public class LoginBean {

    private HashMap validUsers = new HashMap<>();

    public LoginBean() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/login", "root", "root");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM login");
            while (rs.next()) {
                validUsers.put(rs.getString("id"), rs.getString("pass"));
            }
        } catch (ClassNotFoundException notFoundException) {
            System.err.println("Class not found: " + notFoundException);
        } catch (SQLException sqlException) {
            System.err.println(sqlException);
        }
    }

    public boolean validateUser(String userName, String password) {
        System.out.println("User: " + userName);
        System.out.println("Pass: " + password);
        if (validUsers.containsKey(userName)) {
            String thePassword = (String) validUsers.get(userName);
            if (thePassword.equals(password)) {
                return true;
            }
        }
        return false;
    }
}

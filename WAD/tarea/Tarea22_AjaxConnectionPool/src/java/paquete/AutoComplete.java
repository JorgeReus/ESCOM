package paquete;

import java.util.ArrayList;
import java.util.List;
import com.opensymphony.xwork2.ActionSupport;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AutoComplete extends ActionSupport {

    private List<String> paises;
    private String pais;

    public String execute() throws IOException, PropertyVetoException {
        paises = findPaises();
        return "sucess";
    }

    private ArrayList<String> findPaises() throws IOException, PropertyVetoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<String> results = new ArrayList<>();
        try {
            connection = DataSource.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Country");
            while (resultSet.next()) {
                results.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.err.println(e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println(e);
                }
            }
        }

        return results;
    }

    public String getPais() {
        return this.pais;
    }

    public List<String> getPaises() {
        return paises;
    }

    public void setPaises(List<String> paises) {
        this.paises = paises;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}

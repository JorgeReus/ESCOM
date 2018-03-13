package applicacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author reus
 */
public class ProductsDB {
    
    private ResultSet rs;
    private Statement s;
    private Connection con;
    private List<String> products;
    
    public ProductsDB(String dep){
        products = getByDepartamento(dep);
    }
    private List<String> getByDepartamento(String departamento) {
        List<String> prods = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/EComerce", "root", "root");
            s = con.createStatement();
            rs = s.executeQuery("SELECT * FROM product WHERE department='" + departamento + "'");
            while(rs.next()){
                prods.add(rs.getString("name"));
            }
        } catch (SQLException sqle){
            System.err.println("Hubo un error SQL: " + sqle);
        } catch (ClassNotFoundException notFoundException){    
            System.err.println("No se pudo encontar la clase: " + notFoundException);
        } 
        return prods;
    }
    
    public List<String> getProducts(){
        return products;
    }
}

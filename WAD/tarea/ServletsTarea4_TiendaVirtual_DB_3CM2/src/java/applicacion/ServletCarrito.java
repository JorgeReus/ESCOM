package applicacion;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alyne
 */
public class ServletCarrito extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet ServletProductos</title>");
        out.println("</head>");
        out.println("<body>");
        try {
            Statement s;
            ResultSet rs;
            Connection con = null;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/cart", "root", "root");
            s = con.createStatement();
            rs = s.executeQuery("SELECT nombre FROM cart WHERE selected='1'");
            List<String> productos = new ArrayList<>();
            while (rs.next()) {
                productos.add(rs.getString("cart.nombre"));
            }
            if (productos.isEmpty()){
                out.println("No hay productos en el carrito");
            } else {
                for(String p : productos){
                    out.println(p + "<br>");
                }
            }
            con.close();
        } catch (SQLException e) {
            System.err.println("Error sql al intentar conectarse con la base de datos");
            e.printStackTrace();
        } catch (ClassNotFoundException ee) {
            System.err.println("No se puede encontrar la clase:" + ee);
        }
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}

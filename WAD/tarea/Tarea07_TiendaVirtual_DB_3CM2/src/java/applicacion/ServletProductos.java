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
 *
 */
public class ServletProductos extends HttpServlet {

    private List<String> productos = new ArrayList<>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            Statement s;
            ResultSet rs;
            Connection con = null;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/cart", "root", "root");
            s = con.createStatement();
            if (request.getParameterValues("productos") != null) {
                String query = "UPDATE cart SET selected=CASE WHEN nombre in "
                        + "(" + formatString(request.getParameterValues("productos"))
                        + ") THEN 1 ELSE 0 END";
                s.executeUpdate(query);
            }
            productos = new ArrayList<>();
            rs = s.executeQuery("SELECT nombre FROM cart WHERE selected='1'");
            while (rs.next()) {
                productos.add(rs.getString("cart.nombre"));
            }

            con.close();
        } catch (SQLException e) {
            System.err.println("Error sql al intentar conectarse con la base de datos");
            e.printStackTrace();
        } catch (ClassNotFoundException ee) {
            System.err.println("No se puede encontrar la clase:" + ee);
        }
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet ServletProductos</title>");
        out.println("<link rel=\"stylesheet\" href=\"./css/myStyle.css\"/>");
        out.println("<script src=\"./js/jquery-3.3.1.min.js\"></script>");
        out.println("<script src=\"./js/functions.js\" ></script>");
        out.println("</head>");
        out.println("<body>");
        out.println("<form id='prodForm' method='GET' action='ServletProductos'>");
        out.println("<h1>Departamento de Electrónicos</h1>");
        try {
            Statement s;
            ResultSet rs;
            Connection con = null;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/cart", "root", "root");
            s = con.createStatement();
            rs = s.executeQuery("SELECT nombre FROM cart WHERE departamento='Electronicos'");
            while (rs.next()) {
                String inp = "<input type='checkbox' id='" +rs.getString("cart.nombre") 
                        + "' name='productos' value='" + rs.getString("cart.nombre") +  "'"
                        + (productos.contains(rs.getString("cart.nombre")) ? "checked>\n" : ">\n")
                        + "<label for='" + rs.getString("cart.nombre") + "'>" 
                        + rs.getString("cart.nombre") +  "</label><br>";
                out.println(inp);
            }
            out.println("<h1>Departamento de Ropa</h1>");
            rs = s.executeQuery("SELECT nombre FROM cart WHERE departamento='Ropa'");
            while (rs.next()) {
                String inp = "<input type='checkbox' id='" +rs.getString("cart.nombre") 
                        + "' name='productos' value='" + rs.getString("cart.nombre") +  "'"
                        + (productos.contains(rs.getString("cart.nombre")) ? "checked>\n" : ">\n")
                        + "<label for='" + rs.getString("cart.nombre") + "'>" 
                        + rs.getString("cart.nombre") +  "</label><br>";
                out.println(inp);
            }
            out.println("<h1>Departamento de Electrodomésticos</h1>");
            rs = s.executeQuery("SELECT nombre FROM cart WHERE departamento='Electrodomesticos'");
            while (rs.next()) {
                String inp = "<input type='checkbox' id='" +rs.getString("cart.nombre") 
                        + "' name='productos' value='" + rs.getString("cart.nombre") +  "'"
                        + (productos.contains(rs.getString("cart.nombre")) ? "checked>\n" : ">\n")
                        + "<label for='" + rs.getString("cart.nombre") + "'>" 
                        + rs.getString("cart.nombre") +  "</label><br>";
                out.println(inp);
            }
            out.println("<button id='checkout'>Checkout</button></form>");
            con.close();
        } catch (SQLException e) {
            System.err.println("Error sql al intentar conectarse con la base de datos");
            e.printStackTrace();
        } catch (ClassNotFoundException ee) {
            System.err.println("No se puede encontrar la clase:" + ee);
        }
        String modal = "<button id=\"myBtn\" type=\"button\">Ver Carrito</button>\n"
                + "\n"
                + "            <!-- The Modal -->\n"
                + "            <div id=\"myModal\" class=\"modal\">\n"
                + "\n"
                + "                <!-- Modal content -->\n"
                + "                <div class=\"modal-content\">\n"
                + "                    <span class=\"close\">&times;</span>\n"
                + "                    <div id=\"result\">\n"
                + "                    </div>\n"
                + "                </div>\n"
                + "\n"
                + "            </div>\n"
                + "        </form>\n";
        out.println(modal);
        String borrar = "<button id=\"borrarBtn\" type=\"button\">Borrar Carrito</button>";
        out.println(borrar);
        out.println("</body>");
        out.println("</html>");
    }

    private String formatString(String[] productos) {
        String result = "";
        for (String p : productos) {
            result += "'" + p + "',";
        }
        result = result.substring(0, result.length() - 1);
        return result;
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

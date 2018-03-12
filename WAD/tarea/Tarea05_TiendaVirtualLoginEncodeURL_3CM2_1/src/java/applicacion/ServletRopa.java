package applicacion;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alyne
 */
public class ServletRopa extends HttpServlet {

    private List<String> productos = new ArrayList<>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext application = request.getServletContext();
        if (request.getParameterValues("productos") != null) {
            productos = new ArrayList<>();
            for (String producto : request.getParameterValues("productos")) {
                if (!productos.contains(producto)) {
                    productos.add(producto);
                }
            }
            application.setAttribute("productos", productos);
        }
        if (application.getAttribute("productos") == null) {
            productos.clear();
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
        out.println("<h1>Departamento de Electrónicos</h1>");
        String html = "<form id=\"prodForm\" method=\"GET\" action=\"ServletProductos\">\n"
                + "    <input id=\"cbXboxOne\" type=\"checkbox\" name=\"productos\" value=\"XboxOne\" "
                + (productos.contains("XboxOne") ? "checked>\n" : ">\n")
                + "    <label for=\"cbXboxOne\">Xbox One</label><br>\n"
                + "\n"
                + "    <input id=\"cbPs4\" type=\"checkbox\" name=\"productos\" value=\"Ps4\" "
                + (productos.contains("Ps4") ? "checked>\n" : ">\n")
                + "    <label for=\"cbPs4\">Playstation 4</label><br>\n"
                + "\n"
                + "    <input id=\"cbSwitch\" type=\"checkbox\" name=\"productos\" value=\"Switch\" "
                + (productos.contains("Switch") ? "checked>\n" : ">\n")
                + "    <label for=\"cbSwitch\">Nintendo Switch</label><br>\n"
                + "\n"
                + "<h1>Departamento de Ropa</h1>"
                + "    <input id=\"cbPlayera\" type=\"checkbox\" name=\"productos\" value=\"Playera\" "
                + (productos.contains("Playera") ? "checked>\n" : ">\n")
                + "    <label for=\"cbPlayera\">Playera</label><br>\n"
                + "\n"
                + "    <input id=\"cbChamarra\" type=\"checkbox\" name=\"productos\" value=\"Chamarra\" "
                + (productos.contains("Chamarra") ? "checked>\n" : ">\n")
                + "    <label for=\"cbChamarra\">Chamarra</label><br>\n"
                + "\n"
                + "    <input id=\"cbZapatos\" type=\"checkbox\" name=\"productos\" value=\"Zapatos\" "
                + (productos.contains("Zapatos") ? "checked>\n" : ">\n")
                + "    <label for=\"cbZapatos\">Zapatos</label><br>\n"
                + "\n"
                + "<h1>Departamento de Electrodomésticos</h1>"
                + "    <input id=\"cbLavadora\" type=\"checkbox\" name=\"productos\" value=\"Lavadora\" "
                + (productos.contains("Lavadora") ? "checked>\n" : ">\n")
                + "    <label for=\"cbLavadora\">Lavadora</label><br>\n"
                + "\n"
                + "    <input id=\"cbLicuadora\" type=\"checkbox\" name=\"productos\" value=\"Licuadora\" "
                + (productos.contains("Licuadora") ? "checked>\n" : ">\n")
                + "    <label for=\"cbLicuadora\">Licuadora</label><br>\n"
                + "\n"
                + "    <input id=\"cbRefrigerador\" type=\"checkbox\" name=\"productos\" value=\"Refrigerador\" "
                + (productos.contains("Refrigerador") ? "checked>\n" : ">\n")
                + "    <label for=\"cbRefrigerador\">Refrigerador</label><br>\n"
                + "\n"
                + "    <button id=\"checkout\">Checkout</button>\n"
                + "</form>";
        out.println(html);
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
        String borrarBtn = "<button id=\"borrarBtn\" type=\"button\">Borrar Carrito</button>\n";
        out.println(borrarBtn);
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

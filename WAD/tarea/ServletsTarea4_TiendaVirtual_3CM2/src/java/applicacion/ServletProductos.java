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
public class ServletProductos extends HttpServlet {

    private List<String> productos = new ArrayList<>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext application = request.getServletContext();
        productos = new ArrayList<>();
        if (request.getParameterValues("productos") != null) {
            for (String producto : request.getParameterValues("productos")) {
                if (!productos.contains(producto)) {
                    productos.add(producto);
                }
            }
        }
        application.setAttribute("productos", productos);
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet ServletProductos</title>");
        out.println("<link rel=\"stylesheet\" href=\"./css/myStyle.css\"/>");
        out.println("<script src=\"https://code.jquery.com/jquery-2.1.1.min.js\"></script>");
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

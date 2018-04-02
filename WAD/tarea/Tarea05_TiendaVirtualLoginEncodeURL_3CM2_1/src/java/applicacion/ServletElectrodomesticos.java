package applicacion;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author alyne
 */
public class ServletElectrodomesticos extends HttpServlet {

    private List<String> productos = new ArrayList<>();
    private final List<String> electrodomesticos = Arrays.asList("Lavadora", "Licuadora", "Refrigerador");

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        if (request.getParameterValues("productos") != null) {
            if (session.getAttribute("productos") == null) {
                productos = new ArrayList<>();
                for (String producto : request.getParameterValues("productos")) {
                    productos.add(producto);
                }
            } else {
                productos = (ArrayList<String>) session.getAttribute("productos");
                removeTheseProducts();
                for (String producto : request.getParameterValues("productos")) {
                    if (!productos.contains(producto)) {
                        productos.add(producto);
                    }
                }
            }
            
            session.setAttribute("productos", productos);
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
        String html = "<form id=\"prodForm\" method=\"GET\" action=\"ServletElectrodomesticos\">\n"
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
        String electronicos = "<br/><a href='" + response.encodeURL("ServletElectronicos") 
                +  "' >Departamento de Electrónicos</a><br/>";
        out.println(electronicos);
        String ropa = "<br/><a href='" + response.encodeURL("ServletRopa") 
                +  "' >Departamento de Ropa</a><br/>";
        out.println(ropa);
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
    
    private void removeTheseProducts(){
        for (String prod : electrodomesticos){
            if(productos.remove(prod));
        }
    }
}

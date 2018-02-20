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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext application = request.getServletContext();
        List<String> productos = new ArrayList<>();
        application.setAttribute("productos", productos);
        PrintWriter out = response.getWriter();
        if (request.getParameterValues("productos") != null) {
            for (String producto : request.getParameterValues("productos")) {
                if (!productos.contains(producto)) {
                    productos.add(producto);
                }
            }
        }
        System.out.println(">>>>>>>>>>>" + productos);
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet ServletProductos</title>");
        out.println("</head>");
        out.println("<body>");

        String html = "<form id=\"prodForm\" method=\"GET\" action=\"ServletProductos\">\n"
                + "    <input id=\"cbXboxOne\" type=\"checkbox\" name=\"productos\" value=\"XboxOne\">\n"
                + "    <label for=\"cbXboxOne\">Xbox One</label><br>\n"
                + "\n"
                + "    <input id=\"cbPs4\" type=\"checkbox\" name=\"productos\" value=\"Ps4\">\n"
                + "    <label for=\"cbPs4\">Playstation 4</label><br>\n"
                + "\n"
                + "    <input id=\"cbSwitch\" type=\"checkbox\" name=\"productos\" value=\"Switch\" " + (productos.contains("Switch") ? "checked\">\n" : ">\n")
                + "    <label for=\"cbSwitch\">Nintendo Switch</label><br>\n"
                + "\n"
                + "    <button id=\"checkout\">Checkout</button>\n"
                + "</form>";
        out.println(html);
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

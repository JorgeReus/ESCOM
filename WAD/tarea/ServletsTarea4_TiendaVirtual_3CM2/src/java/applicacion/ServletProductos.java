package applicacion;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author alyne
 */
public class ServletProductos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession httpSession = request.getSession();
        PrintWriter out = response.getWriter();
        try {
            String[] paramValues = request.getParameterValues("productos");
            httpSession.setAttribute("productos", paramValues);
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletProductos</title>");
            out.println("</head>");
            out.println("<body>");
            String[] res = (String[]) httpSession.getAttribute("productos");
            for (String val : res) {
                out.println(val);
            }
            out.println("</body>");
            out.println("</html>");
        } catch (NullPointerException nullPointer) {
            out.println("No seleccionó Productos");
        }

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

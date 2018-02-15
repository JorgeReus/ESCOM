package paquete;

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
public class Servlet1 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        double x1 = Double.parseDouble(request.getParameter("x1"));
        double y1 = Double.parseDouble(request.getParameter("y1"));
        double x2 = Double.parseDouble(request.getParameter("x2"));
        double y2 = Double.parseDouble(request.getParameter("y2"));
        double c1 = Double.parseDouble(request.getParameter("c1"));
        double c2 = Double.parseDouble(request.getParameter("c2"));
        double detG = x1 * y2 - x2 * y1;
        double detX = c1 * y2 - y1 * c2;
        double detY = x1 * c2 - c1 * x2;
        double x = detX / detG;
        double y = detY / detG;
        HttpSession session = request.getSession();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet ServletEq</title>");
        out.println("</head>");
        out.println("<body>");
        Object att1 = session.getAttribute("x");
        Object att2 = session.getAttribute("y");
        if(att1 != null && att2 != null){
            out.println("<h3>Resultado Anterior: x0=" + att1.toString() + " y0=" + att2.toString() + "<h3>");
        }
        out.println("<h1>Resultado: x=" + x + " y=" + y + "</h1>");
        out.println("</body>");
        out.println("</html>");
        session.setAttribute("x", x);
        session.setAttribute("y", y);
    }

}

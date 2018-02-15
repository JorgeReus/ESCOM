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
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet ServletEq</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("Este no es el servlet");
        out.println("</body>");
        out.println("</html>");
        session.setAttribute("x1", request.getParameter("x1"));
        session.setAttribute("y1", request.getParameter("y1"));
        session.setAttribute("c1", request.getParameter("c1"));
        session.setAttribute("x2", request.getParameter("x2"));
        session.setAttribute("y2", request.getParameter("y2"));
        session.setAttribute("c2", request.getParameter("c2"));
        response.sendRedirect(request.getContextPath() + "/Servlet2");
    }

}

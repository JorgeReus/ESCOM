package administrator;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Servlet0 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession();
        
        String encodeURL1 = response.encodeURL("Servlet1");
        String encodeURL2 = response.encodeURL("Servlet2");
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet Servlet0</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<a href=\""+encodeURL1+"\">Servlet1");
        out.println("<a href=\""+encodeURL2+"\">Servlet2");
        out.println("</body>");
        out.println("</html>");
    }
}

package admin;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author reus
 */
public class Servlet1 extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext application = this.getServletContext();
        String resource = request.getParameter("resource");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Servlet1</title>");
            out.println("</head>");
            out.println("<body>");
            if (resource == null) {
                out.println("<h3>Error, no resource sumbitted<h3>");
            } else {
                URL url = application.getResource("/" + resource);
                ByteArrayInputStream in = (ByteArrayInputStream) url.getContent();
                int n = in.available();
                byte[] bytes = new byte[n];
                in.read(bytes, 0, n);
                String s = new String(bytes, StandardCharsets.UTF_8); // Or any encoding.
                out.println("<textarea rows='30' name='code' cols='130'>");
                out.println(s);
                out.println("</textarea><br>");
                out.println("<div id='result2'></div>");
                
            }
            out.println("</body>");
            out.println("</html>");
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

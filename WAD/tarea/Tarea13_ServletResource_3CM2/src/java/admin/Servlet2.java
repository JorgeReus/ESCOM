package admin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author reus
 */
public class Servlet2 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String code = request.getParameter("code");
        String file = request.getParameter("resource");
        try (FileWriter writer = new FileWriter(new File(this.getServletContext().getRealPath("/") + file))) {
            writer.write(code);
            out.println("Saved");
        } catch (IOException e) {
            out.println("Error");
        }
    }
}

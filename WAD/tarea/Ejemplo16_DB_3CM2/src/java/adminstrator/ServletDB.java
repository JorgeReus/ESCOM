package adminstrator;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author reus
 */
public class ServletDB extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletDB</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServletDB at " + request.getContextPath() + "</h1>");
            try {
                Statement s;
                ResultSet rs;
                Connection con = null;
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost/DBautocomplete", "root", "root");
                s = con.createStatement();
                rs = s.executeQuery("SELECT Palabra.texto FROM Palabra");
                while (rs.next()) {
                    out.println(rs.getString("Palabra.texto"));
                }
                con.close();
            } catch (SQLException e) {
                System.err.println("Error sql al intentar conectarse con la base de datos");
                e.printStackTrace();
            } catch (ClassNotFoundException ee) {
                System.err.println("No se puede encontrar la clase:" + ee);
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

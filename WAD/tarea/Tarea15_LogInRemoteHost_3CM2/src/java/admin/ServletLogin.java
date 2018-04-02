package admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author reus
 */
public class ServletLogin extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext app = this.getServletContext();
        Integer tries = (Integer) app.getAttribute("tries");
        if (tries == null) {
            app.setAttribute("tries", 0);
            tries = (Integer) app.getAttribute("tries");
        }
        HttpSession session = request.getSession();
        String id = request.getParameter("id");
        String pass = request.getParameter("pass");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("</head>");
        out.println("<body>");
        if (id == null || pass == null) {
            out.println("Error, enter password and id");
        }
        try {
            Statement s;
            ResultSet rs;
            Connection con = null;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/login", "root", "root");
            s = con.createStatement();
            rs = s.executeQuery("SELECT pass FROM login WHERE id='" + id + "'");
            while (rs.next()) {
                if (rs.getString("pass").equals(pass)) {
                    session.setAttribute("aid", id);
                    String user = (String) session.getAttribute("aid");
                    out.println("<h1>Login Succesful User: " + user + "</h1>");
                } else {
                    out.println("Error, Try again");
                    
                    if (tries < 3){
                        Integer aux = 3 - tries;
                        out.println("Tries left: " + aux.toString());
                        tries++;
                        app.setAttribute("tries", tries);
                        System.out.println(tries);
                    } else {
                        out.println("Logged");
                        app.log("User: " + id + "IP:" + request.getRemoteHost() +  " To much tries");
                    }
                }
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}

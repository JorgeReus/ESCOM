package admin;

import java.io.IOException;
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
        String id = (String) request.getParameter("id");
        String pass = (String) request.getParameter("pass");
        LoginBean lb = new LoginBean();
        if (lb.validateUser(id, pass)){           
            HttpSession session = request.getSession(true);
            session.setAttribute("userName", id);
            response.sendRedirect("ServletWelcome");
        } else {
            response.sendRedirect("ServletFailure");
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

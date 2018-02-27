package applicacion;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author alyne
 */
public class ServletCarrito extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JDOMException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet ServletProductos</title>");
        out.println("</head>");
        out.println("<body>");
        SAXBuilder builder = new SAXBuilder();
        File productosXML = new File(this.getServletContext().getRealPath("/") + "productos.xml");
        List<Element> elements = new ArrayList<>();
        List<String> productos = new ArrayList<>();
        try {
            Document document = (Document) builder.build(productosXML);
            Element rootNode = document.getRootElement();
            elements = rootNode.getChildren("producto");
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
        if (elements.isEmpty()) {
            for (Element e : elements) {
                productos.add(e.getChildText("nombre"));
            }
        } else {
            out.println("No hay productos en el carrito");
        }
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JDOMException ex) {
            Logger.getLogger(ServletCarrito.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JDOMException ex) {
            Logger.getLogger(ServletCarrito.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

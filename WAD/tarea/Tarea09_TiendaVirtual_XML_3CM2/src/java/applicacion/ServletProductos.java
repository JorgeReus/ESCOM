package applicacion;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author alyne
 *
 */
/*
<?xml version="1.0" encoding="UTF-8"?>
<productos>
    <producto>
        <nombre>XboxOne</nombre>
    </producto>
    <producto>
        <nombre>Ps4</nombre>
    </producto>
    <producto>
        <nombre>Switch</nombre>
    </producto>
</productos>

 */
public class ServletProductos extends HttpServlet {

    private List<Element> elements = new ArrayList<>();
    private List<String> productos = new ArrayList<>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JDOMException {
        response.setContentType("text/html;charset=UTF-8");
        SAXBuilder builder = new SAXBuilder();
        File productosXML = new File(this.getServletContext().getRealPath("/") + "productos.xml");
        try {
            Document document = (Document) builder.build(productosXML);
            Element rootNode = document.getRootElement();
            elements = rootNode.getChildren("producto");
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
        if (!elements.isEmpty()) {
            for (Element e : elements) {
                productos.add(e.getChildText("nombre"));
            }
        }
        if (request.getParameterValues("productos") != null) {
            productos = new ArrayList<>();
            File file = new File(this.getServletContext().getRealPath("/") + "productos.xml");
            file.delete();
            try {
                Element carrito = new Element("productos");
                Document doc = new Document(carrito);
                // Create Document with it's products
                for (String parameter : request.getParameterValues("productos")) {
                    Element producto = new Element("producto");
                    producto.addContent(new Element("nombre").setText(parameter));
                    doc.getRootElement().addContent(producto);
                    productos.add(parameter);
                }
                XMLOutputter xmlOutput = new XMLOutputter();
                xmlOutput.setFormat(Format.getPrettyFormat());
                xmlOutput.output(doc, new FileWriter(this.getServletContext().getRealPath("/") + "productos.xml"));
            } catch (IOException io) {
                System.out.println(io.getMessage());
            }
        }
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet ServletProductos</title>");
        out.println("<link rel=\"stylesheet\" href=\"./css/myStyle.css\"/>");
        out.println("<script src=\"./js/jquery-3.3.1.min.js\"></script>");
        out.println("<script src=\"./js/functions.js\" ></script>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Departamento de Electrónicos</h1>");
        String html = "<form id=\"prodForm\" method=\"GET\" action=\"ServletProductos\">\n"
                + "    <input id=\"cbXboxOne\" type=\"checkbox\" name=\"productos\" value=\"XboxOne\" "
                + (productos.contains("XboxOne") ? "checked>\n" : ">\n")
                + "    <label for=\"cbXboxOne\">Xbox One</label><br>\n"
                + "\n"
                + "    <input id=\"cbPs4\" type=\"checkbox\" name=\"productos\" value=\"Ps4\" "
                + (productos.contains("Ps4") ? "checked>\n" : ">\n")
                + "    <label for=\"cbPs4\">Playstation 4</label><br>\n"
                + "\n"
                + "    <input id=\"cbSwitch\" type=\"checkbox\" name=\"productos\" value=\"Switch\" "
                + (productos.contains("Switch") ? "checked>\n" : ">\n")
                + "    <label for=\"cbSwitch\">Nintendo Switch</label><br>\n"
                + "\n"
                + "<h1>Departamento de Ropa</h1>"
                + "    <input id=\"cbPlayera\" type=\"checkbox\" name=\"productos\" value=\"Playera\" "
                + (productos.contains("Playera") ? "checked>\n" : ">\n")
                + "    <label for=\"cbPlayera\">Playera</label><br>\n"
                + "\n"
                + "    <input id=\"cbChamarra\" type=\"checkbox\" name=\"productos\" value=\"Chamarra\" "
                + (productos.contains("Chamarra") ? "checked>\n" : ">\n")
                + "    <label for=\"cbChamarra\">Chamarra</label><br>\n"
                + "\n"
                + "    <input id=\"cbZapatos\" type=\"checkbox\" name=\"productos\" value=\"Zapatos\" "
                + (productos.contains("Zapatos") ? "checked>\n" : ">\n")
                + "    <label for=\"cbZapatos\">Zapatos</label><br>\n"
                + "\n"
                + "<h1>Departamento de Electrodomésticos</h1>"
                + "    <input id=\"cbLavadora\" type=\"checkbox\" name=\"productos\" value=\"Lavadora\" "
                + (productos.contains("Lavadora") ? "checked>\n" : ">\n")
                + "    <label for=\"cbLavadora\">Lavadora</label><br>\n"
                + "\n"
                + "    <input id=\"cbLicuadora\" type=\"checkbox\" name=\"productos\" value=\"Licuadora\" "
                + (productos.contains("Licuadora") ? "checked>\n" : ">\n")
                + "    <label for=\"cbLicuadora\">Licuadora</label><br>\n"
                + "\n"
                + "    <input id=\"cbRefrigerador\" type=\"checkbox\" name=\"productos\" value=\"Refrigerador\" "
                + (productos.contains("Refrigerador") ? "checked>\n" : ">\n")
                + "    <label for=\"cbRefrigerador\">Refrigerador</label><br>\n"
                + "\n"
                + "    <button id=\"checkout\">Checkout</button>\n"
                + "</form>";
        out.println(html);
        String modal = "<button id=\"myBtn\" type=\"button\">Ver Carrito</button>\n"
                + "\n"
                + "            <!-- The Modal -->\n"
                + "            <div id=\"myModal\" class=\"modal\">\n"
                + "\n"
                + "                <!-- Modal content -->\n"
                + "                <div class=\"modal-content\">\n"
                + "                    <span class=\"close\">&times;</span>\n"
                + "                    <div id=\"result\">\n"
                + "                    </div>\n"
                + "                </div>\n"
                + "\n"
                + "            </div>\n"
                + "        </form>\n";
        out.println(modal);
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JDOMException ex) {
            Logger.getLogger(ServletProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JDOMException ex) {
            Logger.getLogger(ServletProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

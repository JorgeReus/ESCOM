package administrator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
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
        response.setContentType("image/jpeg");
        try {
            int size = Integer.parseInt(request.getParameter("size"));
            int r = Integer.parseInt(request.getParameter("r"));
            int g = Integer.parseInt(request.getParameter("g"));
            int b = Integer.parseInt(request.getParameter("b"));
            BufferedImage bufferedImage = new BufferedImage(1000,1000, 
            BufferedImage.TYPE_INT_RGB);
            Graphics gr = bufferedImage.getGraphics();
            gr.setColor(new Color(r, g, b));
            gr.fillOval(150, 150, 150*size, 150*size);
            gr.dispose();
            ImageIO.write(bufferedImage, "jpg", response.getOutputStream());          
        }catch (IOException e) {
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

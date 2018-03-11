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
            BufferedImage bufferedImage = new BufferedImage(200,200, 
            BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferedImage.getGraphics();
            g.setColor(Color.blue);
            g.fillOval(0,0,199,199);
            g.dispose();
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

package paquete;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

/* modificador de clase/ final, abstract=algunos metodos son abstractos*/
/*interface= contrato de programacion como un esqueleto, para poder seguir especificacion e implementar estos, 
comportamiento generico*/
public class Servlet12 extends HttpServlet {
    /*notacion,se define metodos clases padre y se redefine en las clases hijas, "patron de diseño metodo template"*/
    @Override
    /*modificador de acceso/ cancelado o implementado / las privadas no se heredan*/
    protected void doGet(HttpServletRequest request, HttpServletResponse response)         
    {
        response.setContentType("image/jpeg");
        try{
            //get output strem = flujo de salida binaria 
            int x1=Integer.parseInt(request.getParameter("x"));
        int y1=Integer.parseInt(request.getParameter("y"));
            BufferedImage bufferedImage= new BufferedImage(x1,y1,BufferedImage.TYPE_INT_RGB);
                Graphics g= bufferedImage.getGraphics();
                g.setColor(Color.red);
                g.fillRect(0,0,x1,y1);
                g.dispose();
                ImageIO.write(bufferedImage,"jpg",response.getOutputStream());
        }
        catch (IOException e)
                {
                    e.printStackTrace();
                }
    }
}
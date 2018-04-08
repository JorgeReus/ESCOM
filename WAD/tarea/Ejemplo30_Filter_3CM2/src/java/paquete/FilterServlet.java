package paquete;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class FilterServlet implements Filter {
private ServletContext context;
@Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        String id=request.getParameter("id");
        context.log("User:"+id);
        System.out.println("User:"+id);
        chain.doFilter(req, res);
    }
@Override
    public void init(FilterConfig config) throws ServletException {
    context=config.getServletContext();
    }
@Override
    public void destroy() {
    }
}
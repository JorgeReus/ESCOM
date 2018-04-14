package com.myapp.struts;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author Reus Gaming PC
 */
public class LonginFilter implements Filter {

    private static final boolean debug = true;
    private Integer tries = 3;
    private FilterConfig filterConfig = null;

    public LonginFilter() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        LoginBean loginBean = new LoginBean();
        if (tries <= 0) {
            request.getServletContext().log(request.getParameter("userName"));
        }
        if (loginBean.validate(request.getParameter("userName"), request.getParameter("password"))) {
            request.getServletContext().setAttribute("user", request.getParameter("userName"));
            request.getServletContext().setAttribute("type", loginBean.getType(request.getParameter("userName")));
            request.getServletContext().setAttribute("users", loginBean.getUsers());
            request.getServletContext().setAttribute("valid", true);
        } else {
            tries--;
            request.getServletContext().setAttribute("valid", false);
        }
        chain.doFilter(request, response);
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                System.out.println("LonginFilter:Initializing filter");
            }
        }
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}

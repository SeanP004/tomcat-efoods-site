package controller;

import java.io.*;	
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet implementation class EndPointServlet
 * Represents an endpoint servlet, which will make a
 * targetted request to a file or JSP.
 */
public abstract class EndPointServlet extends HttpServlet {
   
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = getServletContext();
        context.setAttribute("target://" + getServletName(), config.getInitParameter("target"));
    }
    
    protected void doRequest(String method, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setAttribute("target", (String)getServletContext().getAttribute("target://" + getServletName()));

        //System.out.println(this.getClass().getName()
        //        + " :: " + req.getRequestURI()
        //        + " : "  + req.getAttribute("pathInfo")
        //        + " => " + req.getAttribute("target"));

    } // doRequest

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doRequest("GET", req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doRequest("POST", req, res);
    }
    
} // EndPointServlet

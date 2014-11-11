package ctrl;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import model.*;

/**
 * Servlet implementation class Start
 */
@WebServlet("/index.jsp")
public class Catalog extends HttpServlet {

    public Catalog() {}

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext sc = getServletContext();
        sc.setAttribute("model", new FoodSys());
    }

    // ----- Request Handler -----

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException
    {
        doRequest("GET", req, res);
    } // doGet

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException
    {
        doRequest("POST", req, res);
    } // doPost
    
    private String getParameter(String param, HttpSession sess, HttpServletRequest req) {
        String value = req.getParameter(param);

        if (value == null) {value = (String)sess.getAttribute(param);}
        if (value == null) {value = getServletContext().getInitParameter(param);}

        req.setAttribute(param, value);
        sess.setAttribute(param, value);
        return value;
    }

    private void doRequest(String method, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException
    {
        String          jspx = "/WEB-INF/views/Catalog.jspx";
        ServletContext  sc   = getServletContext();
        FoodSys         fs   = (FoodSys)(sc.getAttribute("model"));
        
        HttpSession   sess = req.getSession();
        String searchTerm = getParameter("searchTerm", sess, req);

    	try {
            req.setAttribute("categories", fs.getCategories());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
        	if (req.getParameter("searchTerm") != null) {
        		req.setAttribute("items", fs.getItems(searchTerm));
        	}
        	else {
        		req.setAttribute("items", fs.getItems());
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher(jspx).forward(req, res);
    } // doRequest

} // Start

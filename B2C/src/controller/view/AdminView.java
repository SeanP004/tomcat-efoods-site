package controller.view;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import controller.*;

/**
 * Servlet implementation class AdminView
 * this will output the target to admin
 */
//@WebServlet("/admin")
public class AdminView extends EndPointServlet {

    @Override
    protected void doRequest(String method, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        super.doRequest(method, req, res);
        
        if (res.isCommitted()) {return;}

        ServletContext sc           = getServletContext();
        String         target       = (String)req.getAttribute("target");
        double         cartAvg      = ((sc.getAttribute("avgCartTime") == null) 
        		? 0 : (double)(sc.getAttribute("avgCartTime")));
        double         checkoutAvg  = ((sc.getAttribute("avgCheckoutTime") == null) 
        		? 0 : (double)(sc.getAttribute("avgCheckoutTime")));

        req.setAttribute("cartAvg", cartAvg);
        req.setAttribute("checkoutAvg", checkoutAvg);
        req.setAttribute("cartAvgSec", cartAvg / 1000);
        req.setAttribute("checkoutAvgSec", checkoutAvg / 1000);

        req.getRequestDispatcher(target).forward(req, res);
    } // doRequest

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doRequest("GET", req, res);
    }

} //CheckoutView
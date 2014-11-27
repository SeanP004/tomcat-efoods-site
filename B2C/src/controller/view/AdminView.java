package controller.view;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.account.Account;
import model.cart.Cart;
import model.checkout.OrdersClerk;
import model.checkout.Receipt;
import controller.EndPointServlet;

/**
 * Servlet implementation class AdminView
 */
//@WebServlet("/AdminView")
public class AdminView extends EndPointServlet{

    @Override
    protected void doRequest(String method, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        super.doRequest(method, req, res);

        ServletContext sc           = getServletContext();
        HttpSession    sess         = req.getSession();
        String         target       = (String)req.getAttribute("target");

        double         cartAvg      = ((sc.getAttribute("avgCartTime") == null) ? 0 : (double)(sc.getAttribute("avgCartTime")));
        double         checkoutAvg  = ((sc.getAttribute("avgCheckoutTime") == null) ? 0 : (double)(sc.getAttribute("avgCheckoutTime")));
        
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
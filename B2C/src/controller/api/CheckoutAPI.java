package controller.api;

import java.io.*;
import controller.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.account.*;
import model.cart.*;
import model.checkout.*;
import model.common.*;

/**
 * Servlet implementation class CartAPI Cart API Endpoint.
 */
// @WebServlet("/api/checkout")
public class CheckoutAPI extends EndPointServlet {

    @Override
    protected void doRequest(String method, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        super.doRequest(method, req, res);

        if (res.isCommitted()) {return;}
        
        ServletContext sc       = getServletContext();
        HttpSession    sess     = req.getSession();
        String         target   = (String)req.getAttribute("target");
        Cart           cart     = (Cart)sess.getAttribute("cart");
        Account		   account  = (Account)sess.getAttribute("account");
        OrdersClerk    clerk    = (OrdersClerk)sc.getAttribute("clerk");
        String         host     = (String)req.getAttribute("host");
        StringWriter   sw       = new StringWriter();

        if (clerk == null) {
            sc.setAttribute("clerk", clerk = OrdersClerk.getClerk());}
        if (cart == null) {
            sess.setAttribute("cart", cart = new Cart());}

        try {
            Receipt receipt = clerk.checkout(host, account, cart, false);
            req.setAttribute("data", XMLUtil.generate(sw, receipt).toString());
            req.setAttribute("status", "Successfully checked out.");
            cart.clear();
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }

        req.getRequestDispatcher(target).forward(req, res);
    } // doRequest

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doRequest("GET", req, res);
    }

} // CartAPI

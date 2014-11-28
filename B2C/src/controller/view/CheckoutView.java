package controller.view;

import controller.*;	
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.account.*;
import model.cart.*;
import model.checkout.*;

/**
 * Servlet implementation class CartView
 */
//@WebServlet("/view/checkout")
public class CheckoutView extends EndPointServlet{

    @Override
    protected void doRequest(String method, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        super.doRequest(method, req, res);

        if (res.isCommitted()) {return;}
        
        ServletContext sc       = getServletContext();
        HttpSession    sess     = req.getSession();
        String         target   = (String)req.getAttribute("target");
        Account        account  = (Account)sess.getAttribute("account");
        Cart           cart     = (Cart)sess.getAttribute("cart");
        OrdersClerk    clerk    = (OrdersClerk)sc.getAttribute("clerk");
        String         host     = (String)req.getAttribute("host");

        if (clerk == null) {
            sc.setAttribute("clerk", clerk = OrdersClerk.getClerk());}
        if (cart == null) {
            sess.setAttribute("cart", cart = new Cart());}

        try {
            Receipt receipt = clerk.checkout(host, account, cart);
            req.setAttribute("receipt", receipt.getUrl());
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

} // CheckoutView

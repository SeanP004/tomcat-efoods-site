package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.account.*;
import model.cart.*;
import model.checkout.*;

/**
 * Servlet implementation class CartAPI Cart API Endpoint.
 */
// @WebServlet("/api/checkout")
public class CheckoutAPI extends HttpServlet {

    private static final String JSP_FILE = "/WEB-INF/xmlres/APIResponse.jspx";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String         jspFile  = JSP_FILE;
        ServletContext sc       = getServletContext();
        HttpSession    sess     = req.getSession();
        Cart           cart     = (Cart)sess.getAttribute("cart");
        Account		   account  = (Account)sess.getAttribute("account");
        OrdersClerk    clerk    = (OrdersClerk)sc.getAttribute("clerk");

        if (clerk == null) {
            sc.setAttribute("clerk", clerk = OrdersClerk.getClerk());}
        if (cart == null) {
            sess.setAttribute("cart", cart = new Cart());}
        if (account == null) {
        	sess.setAttribute("account", account = new Account());}

        try {
            req.setAttribute("data", clerk.checkout(account, cart));
            req.setAttribute("status", "Successfully checked out.");            
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }

        req.getRequestDispatcher(jspFile).forward(req, res);
    } // doGet

} // CartAPI

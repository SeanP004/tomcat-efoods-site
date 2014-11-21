package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.account.*;
import model.cart.*;
import model.catalog.*;
import model.checkout.*;
import model.common.*;

/**
 * Servlet implementation class CartAPI Cart API Endpoint.
 */
// @WebServlet("/api/checkout")
public class CheckoutAPI extends HttpServlet {

    private static final String
        JSP_FILE = "/WEB-INF/xmlres/APIResponse.jspx"
      , XSL_FILE = "/WEB-INF/xmlres/PO.xslt"
      , XSD_FILE = "/WEB-INF/xmlres/PO.xsd"
      ;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        ServletContext sc       = getServletContext();
        HttpSession    sess     = req.getSession();
        String         quantity = req.getParameter("quantity");
        Catalog        catalog  = (Catalog)sc.getAttribute("catalog");
        Cart           cart     = (Cart)sess.getAttribute("cart");
        File           xslt     = new File(sc.getRealPath(XSL_FILE));
        File		   xsd		= new File(sc.getRealPath(XSD_FILE));
        Account		   account  = (Account)sess.getAttribute("account");
        CheckoutClerk  clerk    = (CheckoutClerk)sc.getAttribute("clerk");

        if (clerk == null) {
            sc.setAttribute("clerk", clerk = CheckoutClerk.getClerk());}
        if (cart == null) {
            sess.setAttribute("cart", cart = new Cart());}
        if (account == null) {
        	sess.setAttribute("account", account = new Account());}

        try {
        	req.setAttribute("data", clerk.checkout(account, cart, xsd, xslt));
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }

        req.getRequestDispatcher(JSP_FILE).forward(req, res);
    } // doGet

} // CartAPI

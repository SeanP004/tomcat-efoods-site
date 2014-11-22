package controller;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.cart.Cart;
import model.cart.CartElement;
import model.catalog.Catalog;
import model.common.XMLUtil;
import model.pricing.Cost;

/**
 * Servlet implementation class CartView
 */
//@WebServlet("/api/cartview")
public class CartView extends HttpServlet {

    private static final String JSP_FILE = "/WEB-INF/pages/CartView.jspx";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String         jspFile  = JSP_FILE;
        ServletContext sc       = getServletContext();
        HttpSession    sess     = req.getSession();
        String         action   = req.getParameter("action");
        String         number   = req.getParameter("number");
        String         quantity = req.getParameter("quantity");
        Catalog        catalog  = (Catalog)sc.getAttribute("catalog");
        Cart           cart     = (Cart)sess.getAttribute("cart");
        StringWriter   sw       = new StringWriter();

        if (catalog == null) {
            sc.setAttribute("catalog", catalog = Catalog.getCatalog());}
        if (cart == null) {
            sess.setAttribute("cart", cart = new Cart());}

        try {

            // TODO: add variable for listener as analytics
            // Management wants to be able to determine the average time it
            // takes a client to add an item to the cart and the average time
            // between a fresh visit and checkout (in the same session).
            // Provide a mechanism by which these two averages can be viewed
            // in real time.

            //req.setAttribute("cart", cart.getElements());
            req.setAttribute("cartElement", cart.getElements());
            
                    
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }

        req.getRequestDispatcher(jspFile).forward(req, res);
    } // doGet

} // CartAPI

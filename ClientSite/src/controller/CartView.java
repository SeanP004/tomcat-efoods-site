package controller;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import model.catalog.Item;
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
        Catalog        catalog  = (Catalog)sc.getAttribute("catalog");
        Cart           cart     = (Cart)sess.getAttribute("cart");

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

            Map<String, List<Item>> itemsets = new HashMap<String, List<Item>>();

            // FIXME: Remove this code
            {
                List<Item> items = catalog.getItems(null, null, null, null, null, null, null);
                List<Item> popular = new ArrayList<Item>();
                for (int i = 0; i < 4; i += 1) {
                    popular.add(items.get((int)(Math.random() * items.size() - 1)));
                }
                itemsets.put("Popular", popular);
                req.setAttribute("items",  itemsets);
            }

        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }

        req.getRequestDispatcher(jspFile).forward(req, res);
    } // doGet

} // CartAPI

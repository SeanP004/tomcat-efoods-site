package controller.view;

import controller.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.cart.*;
import model.catalog.*;

/**
 * Servlet implementation class CartView
 */
//@WebServlet("/view/cart")
public class CartView extends EndPointServlet {

    @Override
    protected void doRequest(String method, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        super.doRequest(method, req, res);

        if (res.isCommitted()) {return;}
        
        ServletContext sc       = getServletContext();
        HttpSession    sess     = req.getSession();
        String         target   = (String)req.getAttribute("target");
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

        req.getRequestDispatcher(target).forward(req, res);
    } // doRequest

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doRequest("GET", req, res);
    }

} // CartAPI

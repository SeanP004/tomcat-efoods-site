package controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.account.*;
import model.cart.*;
import model.catalog.*;

/**
 * Servlet implementation class StoreFront page.
 */
// @WebServlet("/jsp")
public class StoreFront extends HttpServlet {

    private static final String
        JSP_FILE = "/WEB-INF/pages/StoreFront.jspx";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        ServletContext sc       = getServletContext();
        HttpSession    sess     = req.getSession();
        Catalog        catalog  = (Catalog)sc.getAttribute("catalog");
        Cart           cart     = (Cart)sess.getAttribute("cart");
        Account        account  = (Account)sess.getAttribute("account");

        if (catalog == null) {
            sc.setAttribute("catalog", catalog = Catalog.getCatalog());}
        if (cart == null) {
            sess.setAttribute("cart", cart = new Cart());}
        if (account == null) {
            sess.setAttribute("account", account = new Account());}

        try {
            List<Category> categories = catalog.getCategories(null, null, null, null);
            Map<String, List<Item>> itemsets = new HashMap<String, List<Item>>();
            itemsets.put("Popular", catalog.getItems(null, null, null, null, "4", null, null));
            for (Category category : categories) {
                itemsets.put(category.getName(), catalog.getItems(null, null, "" + category.getId(), null, "4", null, null));
            }
            req.setAttribute("orders", ItemFilter.sorts);
            req.setAttribute("items",  itemsets);
            req.setAttribute("categories", categories);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }

        req.getRequestDispatcher(JSP_FILE).forward(req, res);
    } // doGet

} // CartAPI

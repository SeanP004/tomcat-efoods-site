package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.account.Account;
import model.cart.*;
import model.catalog.*;

/**
 * Servlet implementation class CatalogView page.
 */
// @WebServlet("/jsp/browse")
public class CatalogView extends HttpServlet {

    private static final String
        JSP_FILE = "/WEB-INF/pages/CatalogView.jspx";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        ServletContext sc       = getServletContext();
        HttpSession    sess     = req.getSession();
        Catalog        catalog  = (Catalog)sc.getAttribute("catalog");
        Cart           cart     = (Cart)sess.getAttribute("cart");
        Account        account  = (Account)sess.getAttribute("account");

        String orderBy,
               searchTerm,
               category,
               minPrice,
               maxPrice;

        req.setAttribute("orderBy",    orderBy = req.getParameter("orderBy"));
        req.setAttribute("searchTerm", searchTerm = req.getParameter("searchTerm"));
        req.setAttribute("catid",      category = req.getParameter("catid"));
        req.setAttribute("minPrice",   minPrice = req.getParameter("minPrice"));
        req.setAttribute("maxPrice",   maxPrice = req.getParameter("maxPrice"));

        if (catalog == null) {
            sc.setAttribute("catalog", catalog = Catalog.getCatalog());}
        if (cart == null) {
            sess.setAttribute("cart", cart = new Cart());}
        if (account == null) {
            sess.setAttribute("account", account = new Account());}

        try {
            req.setAttribute("orders", ItemFilter.sorts);
            req.setAttribute("maxPriceRange", Math.ceil(catalog.getItemMaxPrice()));
            req.setAttribute("items", catalog.getItems("null".equals(orderBy) ? null : orderBy, searchTerm, category,
                    null, null, minPrice, maxPrice));
            req.setAttribute("categories", catalog.getCategories(null, null, null, null));
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }

        req.getRequestDispatcher(JSP_FILE).forward(req, res);
    } // doGet

} // CartAPI

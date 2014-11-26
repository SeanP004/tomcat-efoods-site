package controller.view;

import controller.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.account.Account;
import model.cart.*;
import model.catalog.*;

/**
 * Servlet implementation class CatalogView page.
 */
// @WebServlet("/view/browse")
public class CatalogView extends EndPointServlet {

    @Override
    protected void doRequest(String method, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        super.doRequest(method, req, res);

        ServletContext sc       = getServletContext();
        HttpSession    sess     = req.getSession();
        String         target   = (String)req.getAttribute("target");
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
        req.setAttribute("catId",      category = req.getParameter("catId"));
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
            req.setAttribute("categories", catalog.getCategories(null, null, null, null));
            req.setAttribute("maxPriceRange", Math.ceil(catalog.getItemMaxPrice()));
            req.setAttribute("items", catalog.getItems("null".equals(orderBy) ? null : orderBy, searchTerm, category,
                    null, null, minPrice, maxPrice));            
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

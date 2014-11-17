package controller;

import java.io.*;	
import javax.servlet.*;
import javax.servlet.http.*;
import model.cart.*;
import model.catalog.*;

/**
 * Servlet implementation class StoreFront page.
 */
// @WebServlet("/jsp")
public class StoreFront extends HttpServlet {

    private static final String JSP_FILE = "/WEB-INF/pages/StoreFront.jspx";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        ServletContext sc       = getServletContext();
        HttpSession    sess     = req.getSession();
        Catalog        catalog  = (Catalog)sc.getAttribute("catalog");
        Cart           cart     = (Cart)sess.getAttribute("cart");

        if (catalog == null) {
            sc.setAttribute("catalog", catalog = Catalog.getCatalog());}
        if (cart == null) {
            sess.setAttribute("cart", cart = new Cart());}

        try {
            req.setAttribute("items", catalog.getItems(null, null, null, null, null, null, null));
            req.setAttribute("categories", catalog.getCategories(null, null, null, null));            
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }

        req.getRequestDispatcher(JSP_FILE).forward(req, res);
    } // doGet

} // CartAPI

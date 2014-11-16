package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.cart.*;
import model.catalog.*;
import model.common.*;

/**
 * Servlet implementation class CartAPI Cart API Endpoint.
 */
// @WebServlet("/api/cart")
public class CartAPI extends HttpServlet {

    private static final String JSP_FILE = "/WEB-INF/xmlres/APIResponse.jspx";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        ServletContext sc       = getServletContext();
        HttpSession    sess     = req.getSession();
        String         action   = req.getParameter("action");
        String         number   = req.getParameter("number");
        String         quantity = req.getParameter("quantity");
        Catalog        catalog  = (Catalog)sc.getAttribute("catalog");
        Cart           cart     = (Cart)sess.getAttribute("cart");
        StringWriter   sw       = new StringWriter();
        DummyCart      dc;

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

            if (action != null) {
                switch (action) {
                    case "add":
                        cart.add(number);
                        dc = cart.getDummyCart();
                        req.setAttribute("status", "Successfully Added");
                        req.setAttribute("data", XMLUtil.<DummyCart>generate(sw, dc).toString());
                        break;
                    case "remove":
                        if (cart.hasElement(number)) {
                            cart.remove(number);
                            dc = cart.getDummyCart();
                            req.setAttribute("status", "Successfully Removed");
                            req.setAttribute( "data", XMLUtil.<DummyCart>generate(sw, dc).toString());
                        } else {
                            req.setAttribute("status", "Nothing to remove");
                        }
                        break;
                    case "bulk":
                        cart.bulkUpdate(number, quantity);
                        dc = cart.getDummyCart();
                        req.setAttribute("status", "Successfully Performed Bulk Update");
                        req.setAttribute( "data", XMLUtil.<DummyCart>generate(sw, dc).toString());
                        break;
                    case "list":
                        req.setAttribute("data", XMLUtil.<Cart>generate(sw, cart).toString());
                        break;
                    default:
                        throw new RuntimeException("Bad action");
                }
            } else {
                req.setAttribute("data", XMLUtil.<Cart>generate(sw, cart).toString());
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }

        req.getRequestDispatcher(JSP_FILE).forward(req, res);
    } // doGet

} // CartAPI

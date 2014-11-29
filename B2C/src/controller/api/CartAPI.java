package controller.api;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import controller.*;
import model.cart.*;
import model.catalog.*;
import model.common.*;

/**
 * Servlet implementation class CartAPI Cart API Endpoint.
 */
// @WebServlet("/api/cart")
public class CartAPI extends EndPointServlet {

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
        String         action   = req.getParameter("action");
        String         number   = req.getParameter("number");
        String         quantity = req.getParameter("quantity");
        StringWriter   sw       = new StringWriter();

        if (catalog == null) {
            sc.setAttribute("catalog", catalog = Catalog.getCatalog());}
        if (cart == null) {
            sess.setAttribute("cart", cart = new Cart());}

        try {
            if (action == null) {
                action = "list";
            }
            switch (action) {
                case "add":
                    cart.add(number);
                    req.setAttribute("status", "Successfully Added");
                    req.setAttribute("data", XMLUtil.generate(sw, cart).toString());
                    break;
                case "remove":
                    if (cart.hasElement(number)) {
                        cart.remove(number);
                        req.setAttribute("status", "Successfully Removed");
                        req.setAttribute( "data", XMLUtil.generate(sw, cart).toString());
                    } else {
                        req.setAttribute("status", "Nothing to remove");
                    }
                    break;
                case "bulk":
                    cart.multiBulkUpdate(number, quantity);
                    req.setAttribute("status", "Successfully Performed Bulk Update");
                    req.setAttribute( "data", XMLUtil.generate(sw, cart).toString());
                    break;
                case "list":
                    req.setAttribute("data", XMLUtil.generate(sw, cart).toString());
                    break;
                default:
                    throw new RuntimeException("Bad action");
            }
            req.setAttribute("cart", cart);
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doRequest("POST", req, res);
    }

} // CartAPI

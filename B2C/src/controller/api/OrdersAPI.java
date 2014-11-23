package controller.api;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import controller.*;
import model.checkout.*;
import model.common.*;

/**
 * Servlet implementation class CartAPI Cart API Endpoint.
 */
// @WebServlet(urlPatterns = {"/api/po/*", "/api/order"})
public class OrdersAPI extends EndPointServlet {

    @Override
    protected void doRequest(String method, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        super.doRequest(method, req, res);

        ServletContext sc       = getServletContext();
        String         target   = (String)req.getAttribute("target");
        String         pathInfo = (String)req.getAttribute("pathInfo");
        OrdersClerk    clerk    = (OrdersClerk)sc.getAttribute("clerk");
        String         host     = (String)req.getAttribute("host");
        StringWriter   sw       = new StringWriter();

        if (clerk == null) {
            sc.setAttribute("clerk", clerk = OrdersClerk.getClerk());}

        try {
            if (pathInfo == null) {
                pathInfo = "";
            } else if (!pathInfo.isEmpty() && pathInfo.charAt(0) == '/') {
                pathInfo = pathInfo.substring(1);
            }
            if (pathInfo.isEmpty()) {
                OrdersList orders = clerk.getPurchaseOrders(host);
                req.setAttribute("data", XMLUtil.generate(sw, orders).toString());
            } else {
                if (pathInfo.endsWith(".xml")) {
                    target = clerk.getPurchaseOrder(pathInfo).getPath();
                } else {
                    OrdersList orders = clerk.getPurchaseOrders(host, pathInfo);
                    req.setAttribute("data", XMLUtil.generate(sw, orders).toString());
                }
            }
            req.getRequestDispatcher(target).forward(req, res);
        } catch (Exception e) {
            e.printStackTrace();
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    } // doGet

} // CartAPI

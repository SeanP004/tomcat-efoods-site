package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.checkout.*;
import model.common.*;

/**
 * Servlet implementation class CartAPI Cart API Endpoint.
 */
// @WebServlet(urlPatterns = {"/api/po", "/po/*"})
public class OrdersAPI extends HttpServlet {

    private static final String JSP_FILE = "/WEB-INF/xmlres/APIResponse.jspx";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String         target   = JSP_FILE;
        ServletContext sc       = getServletContext();
        OrdersClerk    clerk    = (OrdersClerk)sc.getAttribute("clerk");
        String         pathInfo = req.getPathInfo();
        StringWriter   sw       = new StringWriter();

        if (clerk == null) {
            sc.setAttribute("clerk", clerk = OrdersClerk.getClerk());}        

        try {
            if (pathInfo == null) {
                pathInfo = "";
            } else if (pathInfo.charAt(0) == '/') {
                pathInfo = pathInfo.substring(1);
            }
            if (pathInfo.isEmpty()) {
                OrdersList orders = clerk.getPurchaseOrders();
                req.setAttribute("data", XMLUtil.generate(sw, orders).toString());
            } else {
                if (pathInfo.endsWith(".xml")) {
                    target = clerk.getPurchaseOrder(pathInfo).getPath();
                } else {
                    OrdersList orders = clerk.getPurchaseOrders(pathInfo);
                    req.setAttribute("data", XMLUtil.generate(sw, orders).toString());
                }
            }
            req.getRequestDispatcher(target).forward(req, res);
        } catch (Exception e) {
            e.printStackTrace();
            res.sendError(404);
        }
    } // doGet

} // CartAPI

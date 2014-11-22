package controller;

import java.io.*;	
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import model.pricing.*;
import model.account.*;
import model.cart.*;
import model.catalog.*;
import model.checkout.*;

/**
 * Servlet implementation class Main
 */
@WebServlet(urlPatterns = {"/jsp/*", "/api/*", "/po/*"})
public class Main extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext sc = getServletContext();
        sc.setAttribute("catalog", Catalog.getCatalog());
        sc.setAttribute("clerk", OrdersClerk.getClerk(
            new File(sc.getRealPath(sc.getInitParameter("userdata"))),
            new File(sc.getRealPath(sc.getInitParameter("ordersXsd"))),
            new File(sc.getRealPath(sc.getInitParameter("ordersXslt"))),
            sc.getContextPath() + sc.getInitParameter("ordersXsltView"),
            sc.getContextPath() + sc.getInitParameter("ordersPrefix"),
            sc.getInitParameter("userdata")
        ));
        sc.setAttribute("pm", PriceManager
                .getPriceManager(new PricingRules(sc
                        .getInitParameter("shippingCost"), sc
                        .getInitParameter("shippingWaverCost"), sc
                        .getInitParameter("taxRate"))));
    }

    private void doRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        ServletContext sc = getServletContext();
        String pathInfo   = req.getPathInfo();
        String context    = req.getContextPath();
        String relative   = req.getRequestURI().substring(context.length());
        String target     = "Error404";

        HttpSession    sess     = req.getSession();
        Cart           cart     = (Cart)sess.getAttribute("cart");
        Account        account  = (Account)sess.getAttribute("account");

        if (cart == null) {
            sess.setAttribute("cart", cart = new Cart());}        
        if (account == null) {
            sess.setAttribute("account", account = new Account());}

        if (relative.startsWith("/api")) {
            if (pathInfo != null) {
                switch (pathInfo) {
                    case "/catalog":  target = "CatalogAPI";       break;
                    case "/cart":     target = "CartAPI";          break;
                    case "/checkout": target = "CheckoutAPI";      break;
                }
            }
        } else if (relative.startsWith("/jsp")) {
            if (pathInfo != null) {
                
                
                switch (pathInfo) {
                    case "/":       target = "StoreFront"; break;
                    case "/browse": target = "CatalogView"; break;
                    case "/cart": target = "CartView"; break;
                }
               
                if(pathInfo.startsWith("/item")){
                    target = "ItemView"; 
                }
                
            } else {
                target = "StoreFront";
            }
        } else if (relative.startsWith("/po")) {
            target = "OrdersAPI";
        } else {
            if (pathInfo == null) {
                target = "StoreFront";
            }
        }

        if ("Error404".equals(target)) {
            res.sendError(404);
        } else {
            sc.getNamedDispatcher(target).forward(req, res);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doRequest(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doRequest(req, res);
    }

} // Main

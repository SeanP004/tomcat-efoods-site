package controller.view;

import controller.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import model.account.*;
import model.cart.*;
import model.checkout.*;

/**
 * Servlet implementation class CartView
 */
//@WebServlet("/view/checkout")
public class CheckoutView extends EndPointServlet implements Filter{

    @Override
    protected void doRequest(String method, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        super.doRequest(method, req, res);

        ServletContext sc       = getServletContext();
        HttpSession    sess     = req.getSession();
        String         target   = (String)req.getAttribute("target");
        Cart           cart     = (Cart)sess.getAttribute("cart");
        Account		   account  = (Account)sess.getAttribute("account");
        OrdersClerk    clerk    = (OrdersClerk)sc.getAttribute("clerk");
        String         host     = (String)req.getAttribute("host");

        if (clerk == null) {
            sc.setAttribute("clerk", clerk = OrdersClerk.getClerk());}
        if (cart == null) {
            sess.setAttribute("cart", cart = new Cart());}
        if (account == null) {
        	sess.setAttribute("account", account = new Account());}

        try {
            Receipt receipt = clerk.checkout(host, account, cart);
            req.setAttribute("receipt", receipt.getUrl());
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
    
    // get access time

    public void init(FilterConfig fConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, 
                FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = ((HttpServletRequest)request);
        HttpSession sess = req.getSession();
        String uri = req.getRequestURI().substring(req.getContextPath().length());
        ServletContext sc = request.getServletContext();
        long before, after, total;
        double avgTime;
        long CheckoutTime;
        int CheckoutCounter;
        if (sc.getAttribute("CheckoutTime") == null) {
            CheckoutTime = 0;
        } else {
            CheckoutTime = (long)sc.getAttribute("CheckoutTime");
        }
        if (sc.getAttribute("CheckoutCounter") == null) {
            CheckoutCounter = 0;
        } else {
            CheckoutCounter = (int)sc.getAttribute("CheckoutCounter");
        }
        
        chain.doFilter(request, response);
        after =  (long) System.currentTimeMillis();
        before = (long) sess.getAttribute("startCheckoutTime");
        CheckoutTime += (after - before);
        CheckoutCounter += 1;
        avgTime = (double) CheckoutTime / (double) CheckoutCounter;
        
        sc.setAttribute("CheckoutTime", CheckoutTime);
        sc.setAttribute("CheckoutCounter", CheckoutCounter);
        sc.setAttribute("avgCheckoutTime", avgTime);
        sess.setAttribute("startCheckoutTime", System.currentTimeMillis());
    }

} // CheckoutView

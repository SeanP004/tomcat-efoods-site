package filter;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet Filter implementation class CheckoutFilter
 */

public class CheckoutFilter implements Filter {

    /**
     * Default constructor.
     */
    public CheckoutFilter() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub
    }

    // get access time

    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

    /**
     * The filter is called when checkout view is called this will generate
     * the average time it take for a user to add a item to cart
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                FilterChain chain) throws IOException, ServletException {

        ServletContext     sc   = request.getServletContext();
        HttpServletRequest req  = ((HttpServletRequest)request);
        HttpSession        sess = req.getSession();

        long   before,
               after;
        double avgTime;
        long   checkoutTime    = 0;
        int    checkoutCounter = 0;

        if (sc.getAttribute("CheckoutTime") != null) {
            checkoutTime = (long)sc.getAttribute("CheckoutTime");}
        if (sc.getAttribute("CheckoutCounter") != null) {
            checkoutCounter = (int)sc.getAttribute("CheckoutCounter");}

        chain.doFilter(request, response);
        before = (long)sess.getAttribute("startCheckoutTime");
        after  = (long)System.currentTimeMillis();

        checkoutTime += (after - before);
        checkoutCounter += 1;
        avgTime = (double)checkoutTime / (double)checkoutCounter;

        //System.out.println("counter checkout" + checkoutCounter);

        sc.setAttribute("CheckoutTime", checkoutTime);
        sc.setAttribute("CheckoutCounter", checkoutCounter);
        sess.setAttribute("startCheckoutTime", System.currentTimeMillis());

        sc.setAttribute("avgCheckoutTime", avgTime);
    }

}

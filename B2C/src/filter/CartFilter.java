package filter;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet Filter implementation class CartFilter
 */
public class CartFilter implements Filter {

    /**
     * Default constructor.
     */
    public CartFilter() {
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
     * The filter is called when cart api is called this will generate
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
        long   cartTime    = 0;
        int    cartCounter = 0;

        if (sc.getAttribute("cartTime") != null) {
            cartTime = (long)sc.getAttribute("cartTime");}
        if (sc.getAttribute("cartCounter") != null) {
            cartCounter = (int)sc.getAttribute("cartCounter");}

        chain.doFilter(request, response);
        before = (long)sess.getAttribute("startCartTime");
        after  = (long)System.currentTimeMillis();

        cartTime += (after - before);
        cartCounter += 1;
        avgTime = (double)cartTime / (double)cartCounter;

        sc.setAttribute("cartTime", cartTime);
        sc.setAttribute("cartCounter", cartCounter);
        sess.setAttribute("startCartTime", System.currentTimeMillis());

        sc.setAttribute("avgCartTime", avgTime);
    }


}

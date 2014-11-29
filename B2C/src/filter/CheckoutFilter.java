package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    }

    /**
     * The filter is called when checkout view is called this will generate
     * the average time it take for a user to add a item to cart
     */
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
        before = (long) sess.getAttribute("startCheckoutTime");
        after =  (long) System.currentTimeMillis();
        
        CheckoutTime += (after - before);
        CheckoutCounter += 1;
        avgTime = (double) CheckoutTime / (double) CheckoutCounter;
        
        //System.out.println("counter checkout" + CheckoutCounter);
        
        sc.setAttribute("CheckoutTime", CheckoutTime);
        sc.setAttribute("CheckoutCounter", CheckoutCounter);
        sess.setAttribute("startCheckoutTime", System.currentTimeMillis());
        
        sc.setAttribute("avgCheckoutTime", avgTime);
    }

}

package filter;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import model.cart.*;
import model.pricing.*;

/**
 * Servlet Filter implementation class PriceFilter
 */
@WebFilter("/PriceFilter")
public class CrossSellingFilter implements Filter {
	
	private boolean isActive = false;
	
    /**
     * Default constructor. 
     */
    public CrossSellingFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (!isActive) {
			ServletContext sc = request.getServletContext();
	        PriceManager pm = (PriceManager) sc.getAttribute("pm");
	        
			PriceOverride po = new PriceOverride() {
				
				public boolean override(Cost cost, PriceManager pm) {
					PricingRules pricingRules = pm.getPricingRules();
					if (cost.getCart().hasElement("2002H712") && cost.getCart().hasElement("1409S413")){
						double itemCost = cost.getExtendedCost(cost.getCart().getElement("2002H712"))
									+ cost.getExtendedCost(cost.getCart().getElement("1409S413"));
						double discount = 0.5;
						
				        cost.setTotal(cost.getTotal() - itemCost + (itemCost * discount));
				        cost.setShipping(cost.getTotal() == 0 || 
				                cost.getTotal() > pricingRules.getShippingWaverCost() 
				                    ? 0 : pricingRules.getShippingCost());
				        cost.setTax((cost.getTotal() + cost.getShipping()) * pricingRules.getTaxRate());
				        cost.setGrandTotal(cost.getTotal() + cost.getTax() + cost.getShipping());
						return true;
					}
					return false;
				}
			};
			
			pm.addPriceFilter(po);
			isActive = true;
		}
	    
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}

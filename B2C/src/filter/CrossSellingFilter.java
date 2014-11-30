package filter;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import model.cart.*;
import model.catalog.*;
import model.pricing.*;

/**
 * Servlet Filter implementation class PriceFilter
 */
public class CrossSellingFilter implements Filter {

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
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     *
     * Ad hoc that will generated of a custom price override that can be changed
     * without recompiling the server.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {

        ServletContext sc = request.getServletContext();
        PriceManager   pm = (PriceManager)sc.getAttribute("pm");

        @SuppressWarnings("unchecked")
        Map<String, PriceFilter> filters = (Map<String, PriceFilter>)sc.getAttribute("priceFilters"); 
        if (filters == null) {
            sc.setAttribute("priceFilters", filters = new HashMap<String, PriceFilter>());}
        if (!filters.containsKey(this.getClass().getName())) {
            PriceFilter po = new PriceFilter() {
                @Override
                public boolean filter(Cost cost, CartElement ce, int qty, PriceManager pm) {
                    Cart         cart     = cost.getCart();
                    PricingRules rules    = pm.getPricingRules();
                    String       number   = ce.getItem().getNumber();
                    int          delta    = qty - ce.getQuantity();
                    double       discount = 0.5;
                    double       price1   = Catalog.getCatalog().getItem("2002H712").getPrice();
                    double       price2   = Catalog.getCatalog().getItem("1409S413").getPrice();
                    double       itemCost;

                    System.out.println(cost.getTotal());
                    
                    if ((!cart.hasElement("2002H712") && !number.equals("2002H712")) ||
                        (!cart.hasElement("1409S413") && !number.equals("1409S413"))) {
                        if (cost.hasFilter(this)) {
                            cost.removeFilter(this);
                        }
                        return false;
                    }

                    if (cost.hasFilter(this)) {
                        if (number.equals("2002H712")) {
                            itemCost = price1 * delta;
                        } else if (number.equals("1409S413")) {
                            itemCost = price2 * delta;
                        } else {
                            return false;
                        }
                    } else {
                        if (number.equals("2002H712")) {
                            itemCost = price1 * qty + cart.getElement("1409S413").extendedCost();
                        } else if (number.equals("1409S413")) {
                            itemCost = price2 * qty + cart.getElement("2002H712").extendedCost();
                        } else {
                            itemCost = cart.getElement("2002H712").extendedCost()
                                     + cart.getElement("1409S413").extendedCost();
                        }
                    }

                    cost.setTotal(cost.getTotal() - itemCost + (itemCost * discount));
                    cost.setShipping(cost.getTotal() == 0 ||
                            cost.getTotal() > rules.getShippingWaverCost()
                                ? 0 : rules.getShippingCost());
                    cost.setTax((cost.getTotal() + cost.getShipping()) * rules.getTaxRate());
                    cost.setGrandTotal(cost.getTotal() + cost.getTax() + cost.getShipping());

                    cost.addFilter(this);

                    System.out.println(cost.getTotal());
                    System.out.println("original: " + itemCost);
                    System.out.println("discounted: " + itemCost * discount);

                    return true;
                }
            }; // PriceOverride
            filters.put(this.getClass().getName(), po);
            if (pm != null) {
                pm.addPriceFilter(po);
            }
        } // set filter

        chain.doFilter(request, response);
    }

}

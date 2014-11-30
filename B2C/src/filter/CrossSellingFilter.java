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
                private boolean hasItem(String number, Cart cart, String item) {
                    return cart.hasElement(number) || item.equals(number);
                }
                private double getPrice(String number) {
                    return Catalog.getCatalog().getItem(number).getPrice();
                }
                private double getExtendedCost(String number, Cart cart) {
                    return cart.hasElement(number) ? cart.getElement(number).extendedCost() : 0;
                }
                @Override
                public int getFilterType() {
                    return TOTAL;
                }
                @Override
                public boolean filter(Cost cost, CartElement ce, int qty, PriceManager pm) {                   
                    
                    Cart   cart   = cost.getCart();
                    String number = ce.getItem().getNumber();

                    if (!hasItem("2002H712", cart, number) || !hasItem("1409S413", cart, number)) {
                        if (cost.hasFilter(this)) {
                            cost.removeFilter(this);}
                        return false;
                    }

                    double itemCost;
                    double discount    = 0.5;
                    int    delta       = qty - ce.getQuantity();
                    double price1      = getPrice("2002H712");
                    double price2      = getPrice("1409S413");
                    double extendCost1 = getExtendedCost("2002H712", cart);
                    double extendCost2 = getExtendedCost("1409S413", cart);

                    if (number.equals("2002H712")) {
                        if (qty == 0) {
                            itemCost = price1 * delta - extendCost2;
                        } else if (ce.getQuantity() == 0) {
                            itemCost = price1 * qty + extendCost2;
                        } else {
                            itemCost = price1 * delta;
                        }
                    } else if (number.equals("1409S413")) {
                        if (qty == 0) {
                            itemCost = price2 * delta - extendCost1;
                        } else if (ce.getQuantity() == 0) {
                            itemCost = price2 * qty + extendCost1;
                        } else {
                            itemCost = price2 * delta;
                        }
                    } else {
                        return false;
                    }

                    cost.setDiscount(cost.getDiscount() + itemCost * discount);
                    //System.out.println("discount: " + cost.getDiscount());

                    if (qty <= 0) {
                        cost.removeFilter(this);
                        return false;
                    } else if (!cost.hasFilter(this)) {
                        cost.addFilter(this);
                    }

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

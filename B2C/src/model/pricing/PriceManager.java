package model.pricing;

import java.util.*;
import model.cart.*;
import model.catalog.*;

/**
 * The price manager class is a singleton in
 * the system that determines and computes how
 * all the items are priced and taxes, shipping,
 * totals are calculated, given a set pricing rules,
 * the sticker price of the items from the database, and
 * the quantity of items being purchased.
 */
public class PriceManager{

    private static PriceManager singleton = null;

    private PricingRules pricingRules;
    private List<PriceFilter> priceFilterTotal    = new ArrayList<PriceFilter>();
    private List<PriceFilter> priceFilterShipping = new ArrayList<PriceFilter>();
    private List<PriceFilter> priceFilterTax      = new ArrayList<PriceFilter>();

    private PriceManager(PricingRules rules, List<PriceFilter> filters) {
        this.pricingRules = rules;
        for (PriceFilter filter : filters) {
            addPriceFilter(filter);
        }
    }

    public PricingRules getPricingRules (){
    	return pricingRules;
    }

    public Cost getCostInstance(Cart cart) {
        return new Cost(this, cart);
    }

    public double getExtendedCost(Item item, int quantity) {
        return item.getPrice() * quantity;
    }

    public double getExtendedCost(CartElement ce) {
        return getExtendedCost(ce.getItem(), ce.getQuantity());
    }

    public void resetCost(Cost cost){
    	cost.reset();
    }

    // Costing
    
    public void updateCost(Cost cost, CartElement ce, int qty) {
        cost.setTotal(computeTotal(cost, ce, qty));
        //System.out.println("total: " + cost.getTotal());
        doFilter(priceFilterTotal, cost, ce, qty);
        cost.setShipping(computeShipping(cost, ce, qty));
        //System.out.println("Shipping: " + cost.getShipping());
        doFilter(priceFilterShipping, cost, ce, qty);
        cost.setTax(computeTax(cost, ce, qty));
        //System.out.println("tax: " + cost.getTax());
        doFilter(priceFilterTax, cost, ce, qty);
        cost.setGrandTotal(computeGrandTotal(cost, ce, qty));
        //System.out.println("grand: " + cost.getGrandTotal());
    }

    private double computeTotal(Cost cost, CartElement ce, int quantity) {
        return cost.getTotal() + getExtendedCost(ce.getItem(), quantity)
                - getExtendedCost(ce);
    }

    private double computeShipping(Cost cost, CartElement ce, int quantity) {
        double total = cost.getTotal() - cost.getDiscount();
        return total == 0 || total > pricingRules.getShippingWaverCost() 
                ? 0 : pricingRules.getShippingCost();
    }

    private double computeTax(Cost cost, CartElement ce, int quantity) {
        double total = cost.getTotal() - cost.getDiscount();
        return (total + cost.getShipping()) * pricingRules.getTaxRate();
    }

    private double computeGrandTotal(Cost cost, CartElement ce, int quantity) {
        double total = cost.getTotal() - cost.getDiscount();
        return total + cost.getTax() + cost.getShipping();
    }

    // Filtering
    
    public void addPriceFilter(PriceFilter filter) {
        switch (filter.getFilterType()) {
            case PriceFilter.TOTAL:
                priceFilterTotal.add(filter); break;
            case PriceFilter.SHIPPING:
                priceFilterShipping.add(filter); break;
            case PriceFilter.TAX:
                priceFilterTax.add(filter); break;
        }
    }
    
    public void removePriceFilter(PriceFilter filter) {
        if (priceFilterTotal.remove(filter)) {return;}
        if (priceFilterShipping.remove(filter)) {return;}
        if (priceFilterTax.remove(filter)) {return;}
    }
    
    private void doFilter(List<PriceFilter> filters, Cost cost, CartElement ce, int quantity) {
        for (PriceFilter po : filters) {
            if (po.filter(cost, ce, quantity, this)) {break;}
        }
    }
    
    // Static

    public static PriceManager getPriceManager(
                PricingRules rules, List<PriceFilter> filters) {
        if (rules != null && singleton == null) {
            singleton = new PriceManager(rules, filters);
        }
        return singleton;
    }

    public static PriceManager getPriceManager() {
        return getPriceManager(null, null);
    }

} // PriceManager
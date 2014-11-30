package model.pricing;

import java.util.*;
import model.cart.*;
import model.catalog.*;

/**
 * The price manager class is a singleton in
 * the system that determines and computes how
 * all the items are priced and taxes, shipping,
 * totals are calculated, by on a set pricing rules,
 * the sticker of the items from the database, and
 * the quantity of items being purchased.
 */
public class PriceManager{

    private static PriceManager singleton = null;

    private PricingRules pricingRules;
    private List<PriceFilter> priceFilter;

    private PriceManager(PricingRules rules, List<PriceFilter> filters) {
        this.pricingRules = rules;
        this.priceFilter = filters == null ? new ArrayList<PriceFilter>() : filters;
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

    public void updateCost(Cost cost, CartElement ce, int quantity) {
        cost.setTotal(cost.getTotal() + getExtendedCost(ce.getItem(), quantity)
                - getExtendedCost(ce));
        cost.setShipping(cost.getTotal() == 0 || 
                cost.getTotal() > pricingRules.getShippingWaverCost() 
                    ? 0 : pricingRules.getShippingCost());
        cost.setTax((cost.getTotal() + cost.getShipping()) * pricingRules.getTaxRate());
        cost.setGrandTotal(cost.getTotal() + cost.getTax() + cost.getShipping());
    	for (PriceFilter po : priceFilter) {
    		if (po.filter(cost, ce, quantity, this)) {return;}
    	}
    }
    
    public void addPriceFilter(PriceFilter po) {
    	priceFilter.add(po);
    }
    
    public void removePriceFilter(PriceFilter po) {
    	priceFilter.remove(po);
    }

    public void resetCost(Cost cost){
    	cost.reset();
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
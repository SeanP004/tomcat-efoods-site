package model.pricing;

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
public class PriceManager {

    private static PriceManager singleton = null;

    private PricingRules pricingRules;

    private PriceManager(PricingRules pricingRules) {
        this.pricingRules = pricingRules;
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
        cost.setTotal(cost.getTotal() + getExtendedCost(ce.getItem(), quantity) - getExtendedCost(ce));
        cost.setShipping(cost.getTotal() > pricingRules.getShippingWaverCost() ? 0 : pricingRules.getShippingCost());
        cost.setTax((cost.getTotal() + cost.getShipping()) * pricingRules.getTaxRate());
        cost.setGrandTotal(cost.getTotal() + cost.getTax() + cost.getShipping());
    }
    
    public void resetCost(Cost cost){
    	cost.reset();
    }

    // Static

    public static PriceManager getPriceManager(PricingRules pricingRules) {
        if (pricingRules != null && singleton == null) {
            singleton = new PriceManager(pricingRules);
        }
        return singleton;
    }

    public static PriceManager getPriceManager() {
        return getPriceManager(null);
    }

} // PriceManager
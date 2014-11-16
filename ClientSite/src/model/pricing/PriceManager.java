package model.pricing;

import model.cart.*;
import model.catalog.*;

public class PriceManager {
    
    private static PriceManager singleton = null;
    
    private PricingRules pricingRules;
    
    private PriceManager(PricingRules pricingRules) {
        this.pricingRules = pricingRules;
    }
    
    public Cost getCostInstance() {
        return new Cost(this);
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
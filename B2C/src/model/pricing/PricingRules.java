package model.pricing;

import model.exception.*;

/**
 * The pricing rules are stored
 * in this object and loaded into
 * the system at startup from the
 * web.xml configuration file.
 */
public class PricingRules {

    private double shippingCost;
    private double shippingWaverCost;
    private double taxRate;
    
    public PricingRules() { }

    public PricingRules(double shippingCost, double shippingWaverCost, double taxRate) {
        this.shippingCost = shippingCost;
        this.shippingWaverCost = shippingWaverCost;
        this.taxRate = taxRate;
    }

    public PricingRules(String shippingCost, String shippingWaverCost, String taxRate) {
        this(parseDouble(shippingCost), parseDouble(shippingWaverCost), parseTaxRate(taxRate));
    }
    
    // Getters
    
    public double getShippingCost() {
        return shippingCost;
    }

    public double getShippingWaverCost() {
        return shippingWaverCost;
    }

    public double getTaxRate() {
        return taxRate;
    }

    // Setters

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public void setShippingWaverCost(double shippingWaverCost) {
        this.shippingWaverCost = shippingWaverCost;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    // Static Helpers

    private static double parseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            throw new PricingRuleValueException("Invalid number.", e);
        }
    }

    private static double parseTaxRate(String s) {
        double rate = parseDouble(s);
        if (rate < 0 || rate > 1) {
            throw new PricingRuleValueException("Invalid tax rate.");
        }
        return rate;
    }
    
} // PricingRules

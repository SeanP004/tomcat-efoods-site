package model.pricing;

public class PricingRules {

    private double shippingCost;
    private double shippingWaverCost;
    private double taxRate;
    
    public PricingRules () { }
    
    public PricingRules (double shippingCost, double shippingWaverCost, double taxRate) {
        this.shippingCost = shippingCost;
        this.shippingWaverCost = shippingWaverCost;
        this.taxRate = taxRate;
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

} // PricingRules

package model.pricing;

import javax.xml.bind.annotation.*;
import model.cart.*;

@XmlRootElement(name="cost")
public class Cost {

    private PriceManager pm;
    private double total;
    private double shipping;
    private double tax;
    private double grandTotal;
    
    public Cost(PriceManager priceManager) {
        this.pm = priceManager;
    }

    // Getters
    
    public double getTotal() {
        return total;
    }

    public double getShipping() {
        return shipping;
    }

    public double getTax() {
        return tax;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public double getExtendedCost(CartElement ce) {
        return pm.getExtendedCost(ce);
    }    

    // Setters
    
    void setTotal(double total) {
        this.total = total;
    }

    void setShipping(double shipping) {
        this.shipping = shipping;
    }

    void setTax(double tax) {
        this.tax = tax;
    }

    void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    // Public Setters
    
    public void updateCost(CartElement ce, int quantity) {
        pm.updateCost(this, ce, quantity);
    }

} // Cost

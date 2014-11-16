package model.pricing;

import javax.xml.bind.annotation.*;	
import model.cart.*;

import static model.common.CommonUtil.*;

@XmlRootElement(name="cost")
public class Cost {

    private PriceManager pm;
    private double total;
    private double shipping;
    private double tax;
    private double grandTotal;

    public Cost() { } // To make JAXB happy

    public Cost(PriceManager priceManager) {
        this.pm = priceManager;
    }

    // Getters

    @XmlElement
    public double getTotal() {
        return roundOff(total);
    }

    @XmlElement
    public double getShipping() {
        return roundOff(shipping);
    }

    @XmlElement
    public double getTax() {
        return tax;
    }

    @XmlElement
    public double getGrandTotal() {
        return roundOff(grandTotal);
    }

    public double getExtendedCost(CartElement ce) {
        return pm.getExtendedCost(ce);
    }    

    // Package-Access Getters
    
    double getRawTotal() {
        return total;
    }

    double getRawGrandTotal() {
        return grandTotal;
    }

    // Package-Access Setters

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

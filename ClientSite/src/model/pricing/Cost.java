package model.pricing;

import javax.xml.bind.annotation.*;
import model.cart.*;

import static model.common.CommonUtil.*;

/**
 * The Cost object stores the values of
 * a cart's total, shopping and tax costs.
 * Has a reference to the PriceManager, and
 * notifies the PriceManager when an update
 * to the pricing is needed.
 */
@XmlRootElement(name="cost")
public class Cost {

    private PriceManager pm;
    private Cart cart;
    private double total;
    private double shipping;
    private double tax;
    private double grandTotal;

    public Cost() { } // To make JAXB happy

    /**
     * Construct a new Cost object
     * Must be created by the PriceManager.
     * Package level access only
     * 
     * @param priceManager  the price manager
     * @param cart          the associate cart
     */
    Cost(PriceManager priceManager, Cart cart) {
        this.cart = cart;
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

    Cart getCart() {
        return cart;
    }

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

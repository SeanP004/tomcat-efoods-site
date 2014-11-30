package model.pricing;

import java.util.*;
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
    private List<PriceFilter> filters;
    private Cart cart;
    private double total;
    private double discount;
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
        this.filters = new ArrayList<PriceFilter>();
    }

    // Getters

    @XmlElement
    public double getTotal() {
        return roundOff(total);
    }

    @XmlElement
    public double getDiscountedTotal() {
        return roundOff(total - discount);
    }

    @XmlElement
    public double getDiscount() {
        return roundOff(discount);
    }

    @XmlElement
    public double getShipping() {
        return roundOff(shipping);
    }

    @XmlElement
    public double getTax() {
        return roundOff(tax);
    }

    @XmlElement
    public double getGrandTotal() {
        return roundOff(grandTotal);
    }

    public double getExtendedCost(CartElement ce) {
        return pm.getExtendedCost(ce);
    }

    // Package-Access Getters

    public Cart getCart() {
        return cart;
    }

    double getRawTotal() {
        return total;
    }

    double getRawDiscountedTotal() {
        return total - discount;
    }

    double getRawDiscount() {
        return discount;
    }

    double getRawTax() {
        return tax;
    }
    
    double getRawGrandTotal() {
        return grandTotal;
    }

    // Setters
    
    public void setTotal(double total) {
        this.total = total;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    void reset() {
    	setTotal(0);
    	setDiscount(0);
    	setShipping(0);
    	setTax(0);
    	setGrandTotal(0);
    }

    // Filters
    
    public void addFilter(PriceFilter pf) {
        filters.add(pf);
    }
    
    public boolean hasFilter(PriceFilter pf) {
        return filters.contains(pf);
    }
    
    public void removeFilter(PriceFilter pf) {
        filters.remove(pf);
    }

    // Public Setters

    public void updateCost(CartElement ce, int quantity) {
        pm.updateCost(this, ce, quantity);
    }

} // Cost

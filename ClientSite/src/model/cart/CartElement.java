package model.cart;

import javax.xml.bind.annotation.*;
import model.catalog.*;
import model.pricing.*;

/**
 * CartElement stores the item collected as an
 * object shopping cart stores the item object and
 * the quanity
 */
public class CartElement {

    private Item item;
    private Cost cost;
    private int  quantity;
    
    /**
     * CartElement constructor
     */
    public CartElement() {
        quantity = 1;
    }
    
    /**
     * CartElement constructor
     * 
     * @param item      catalog item
     * @param cost      costing object
     */
    public CartElement(Item item, Cost cost) {
       this();
       this.cost = cost;
       setItem(item);
    }
   
    /**
     * Increase the quantity counter by 1 increment.
     */
    public void incrementQuantity() {
        setQuantity(quantity + 1);
    }

    /**
     * Decrease the the quantity counter by 1 decrement.
     */
    public void decrementQuantity() {
        setQuantity(quantity - 1);
    }

    // Getters

    /**
     * Returns the item stored in cart.
     * 
     * @return the catalog item
     */
    @XmlElement
    public Item getItem() {
        return item;
    }

    /**
     * Returns the quantity store in cart.
     * 
     * @return the quantity of the item in the cart
     */
    @XmlAttribute
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns the extended cost of the item.
     * 
     * @return the extended cost of the item in the cart
     */
    @XmlAttribute
    public double getExtendedCost() {
        return cost.getExtendedCost(this);
    }

    // Setters
   
    /**
     * Set the value of item.
     * 
     * @param item      catalog item
     */
    public void setItem(Item item) {
        this.item = item;
    }
    
    /**
     * Set the value of quantity.
     * 
     * @param quantity
     */
    public void setQuantity(int quantity) {
        if (quantity > 0) {
            cost.updateCost(this, quantity);
            this.quantity = quantity;
        }
    }

} // CartElement

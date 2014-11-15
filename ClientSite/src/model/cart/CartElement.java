package model.cart;

import javax.xml.bind.annotation.*;
import model.catalog.*;

/**
 * CartElement stores the item collected as an
 * object shopping cart stores the item object and
 * the quanity
 */
public class CartElement {
    private Item item;
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
     */
    public CartElement(Item item) {
       this();
       setItem(item);
    }
   
    /**
     * Increase the quantity counter by 1 increment.
     */
    public void incrementQuantity() {
        quantity += 1;
    }

    /**
     * Decrease the the quantity counter by 1 decrement.
     */
    public void decrementQuantity() {
        quantity -= 1;
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

    // Setters
   
    /**
     * Set the value of item.
     * 
     * @param item      catalog item
     */
    public void setItem(Item item) {
        this.item = item;
    }
    
} // CartElement

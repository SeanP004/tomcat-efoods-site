package model.cart;

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
     * 
     * @param item      catalog item
     */
    public CartElement(Item item) {
        this.item = item;
        quantity = 1;
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
    public Item getItem() {
        return item;
    }

    /**
     * Returns the quantity store in cart.
     * 
     * @return the quantity of the item in the cart
     */
    public int getQuantity() {
        return quantity;
    }

} // CartElement

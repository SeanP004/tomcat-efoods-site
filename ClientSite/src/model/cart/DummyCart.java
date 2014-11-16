package model.cart;

import javax.xml.bind.annotation.*;
import model.pricing.*;

/**
 * A "dummy" cart represents a shopping cart
 * in the system. It lacks a list of items,
 * Is only intended for outputting XML in the
 * CartAPI servlet.
 */
@XmlRootElement(name="cart")
public class DummyCart {

    private int numberOfItems;
    private Cost cost;

    public DummyCart() { } // To make JAXB happy

    public DummyCart(int numberOfItems, Cost cost) {
        setNumberOfItems(numberOfItems);
        setCost(cost);
    }

    // Getter

    /**
     * Returns the number of items stored in the cart
     *
     * @return the number of items in the cart
     */
    @XmlAttribute(name="size")
    public int getNumberOfItems() {
        return numberOfItems;
    }

    /**
     * Returns the cost object stored in the cart
     *
     * @return the cost object in the cart
     */
    @XmlElement(name="cost")
    public Cost getCost() {
        return cost;
    }
    
    // Setter

    /**
     * Sets the number of items in the cart
     *
     * @param numberOfItems     in the cart
     */
    private void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    /**
     * Sets the cost of the cart
     *
     * @param cost  costing object
     */
    private void setCost(Cost cost) {
        this.cost = cost;
    }

} // ThinCart

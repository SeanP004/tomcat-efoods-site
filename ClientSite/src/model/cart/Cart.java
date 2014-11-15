package model.cart;

import java.util.*;
import javax.xml.bind.annotation.*;
import model.catalog.*;

/**
 * Cart stores the item collected of CartElement as an object shopping cart
 * stores the item object and the number of item in cart
 */
@XmlRootElement(name="cart")
public class Cart {

    private Map<String, CartElement> elements;
    private int                      numberOfItems;
    private Catalog                  catalog;

    /**
     * Cart constructor
     */
    public Cart() {
        this.elements = new LinkedHashMap<String, CartElement>();
        this.numberOfItems = 0;
    }

    /**
     * Cart constructor
     * @param catalog   catalog object
     */
    public Cart(Catalog catalog) {
        this();
        setCatalog(catalog);
    }
    
    /**
     * Set the cart's catalog reference
     * 
     * @param catalog   catalog object
     */
    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    /**
     * Add an CartElement to the cart
     * If the current item exist in the cart then increment it quanty by 1
     * else create and add an new cart element to cart
     *
     * @param number    the item number string
     */
    public synchronized void add(String number) {
        if (elements.containsKey(number)) {
            elements.get(number).incrementQuantity();
        } else {
            Item item = catalog.getItem(number);
            CartElement newItem = new CartElement(item);
            elements.put(number, newItem);
        }
        numberOfItems += 1;
    }

    /**
     * Remove an cart element from the cart
     * Only if the request number exist in cart quanty decrease by 1
     * If the quanty is 0 the entire cart element is removed from cart
     *
     * @param number    the item number string
     */
    public synchronized void remove(String number) {
        if (elements.containsKey(number)) {
            CartElement scitem = elements.get(number);
            scitem.decrementQuantity();
            if (scitem.getQuantity() <= 0) {
                elements.remove(number);
            }
            numberOfItems -= 1;
        }
    }

    /**
     * Convert the current cart to an list of items
     *
     * @return a copy of a list of cart elements
     */
    @XmlElement(name="element")
    public synchronized List<CartElement> getElements() {
        return new ArrayList<CartElement>(elements.values());
    }

    /**
     * Returns the number of items stored in the cart
     *
     * @return the number of items in the cart
     */
    @XmlAttribute(name="size")
    public synchronized int getNumberOfItems() {
        return numberOfItems;
    }

    /**
     * Clear the cart back to intial state
     */
    public synchronized void clear() {
        elements.clear();
        numberOfItems = 0;
    }

} // Cart

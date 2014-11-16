package model.cart;

import java.util.*;
import javax.xml.bind.annotation.*;
import model.catalog.*;
import model.exception.*;

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
        this.catalog = Catalog.getCatalog();
        this.numberOfItems = 0;
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
        if (hasElement(number)) {
            getElement(number).incrementQuantity();
        } else {
            Item item = catalog.getItem(number);
            CartElement ce = new CartElement(item);
            elements.put(number, ce);
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
        if (hasElement(number)) {
            CartElement ce = getElement(number);
            ce.decrementQuantity();
            if (ce.getQuantity() <= 0) {
                elements.remove(number);
            }
            numberOfItems -= 1;
        }
    }

    /**
     * Update the specified CartElement with the given quantity.
     * If the item specified is not in the cart, add to cart
     * If the quantity is negative throw an Exception
     * If the quantity is zero remove from cart
     * Otherwise set quantity.
     *
     * @param number    the item number string
     * @param quantity  the number of items
     * @throws          InvalidCartQuantityException
     */
    public synchronized void bulkUpdate(String number, int quantity) {
        if (quantity < 0) { throw new InvalidCartQuantityException(); }
        if (!hasElement(number)) {
            Item item = catalog.getItem(number);
            CartElement ce = new CartElement(item);
            ce.setQuantity(quantity);
            elements.put(number, ce);
            numberOfItems += quantity;
        } else {
            CartElement ce = getElement(number);
            if (quantity == 0) {
                elements.remove(number);
                numberOfItems -= ce.getQuantity();
            } else {
                numberOfItems += quantity - ce.getQuantity();
                ce.setQuantity(quantity);
            }
        }
    }

    /**
     * Update the specified CartElement with the given quantity.
     * If the item specified is not in the cart, add to cart
     * If the quantity is negative throw an Exception
     * If the quantity is zero remove from cart
     * Otherwise set quantity.
     *
     * @param number    the item number string
     * @param quantity  the number of items
     * @throws          InvalidCartQuantityException
     */
    public synchronized void bulkUpdate(String number, String quantity) {
        try {
            bulkUpdate(number, Integer.parseInt(quantity));
        } catch (NumberFormatException e) {
            throw new InvalidCartQuantityException(e);
        }
    }

    /**
     * Returns true if the cart element for given
     * the specified item number exists in the carts,
     * otherwise false.
     *
     * @param number    the item number string
     * @return          true if cart element exists, otherwise false.
     */
    public synchronized boolean hasElement(String number) {
        return elements.containsKey(number);
    }

    /**
     * Returns the cart element from the cart
     * given the specified item number.
     *
     * @param number    the item number string
     * @return          the cart element
     */
    public synchronized CartElement getElement(String number) {
        return elements.get(number);
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

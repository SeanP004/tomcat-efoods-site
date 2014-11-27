package model.cart;

import java.util.*;
import javax.xml.bind.annotation.*;
import model.catalog.*;
import model.exception.*;
import model.pricing.*;

/**
 * Cart stores the item collected of CartElement as an object shopping cart
 * stores the item object and the number of item in cart
 */
@XmlRootElement(name="cart")
public class Cart {

    private Map<String, CartElement> elements;
    private Catalog catalog;
    private int numberOfItems;
    private Cost cost;

    /**
     * Cart constructor
     */
    public Cart() {
        this.elements = new LinkedHashMap<String, CartElement>();
        this.catalog = Catalog.getCatalog();
        this.cost = PriceManager.getPriceManager().getCostInstance(this);
        setNumberOfItems(0);
    }

    // Operations

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
            CartElement ce = new CartElement(item, getCost());
            elements.put(number, ce);
        }
        shiftNumberOfItems(1);
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
            shiftNumberOfItems(-1);
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
            CartElement ce = new CartElement(item, getCost());
            ce.setQuantity(quantity);
            elements.put(number, ce);
            shiftNumberOfItems(quantity);
        } else {
            CartElement ce = getElement(number);
            if (quantity == 0) {
                elements.remove(number);
            }
            shiftNumberOfItems(quantity - ce.getQuantity());
            ce.setQuantity(quantity);
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
     * Multiple update the specified CartElement with the given quantity.
     * If the item specified is not in the cart, add to cart
     * If the quantity is negative throw an Exception
     * If the quantity is zero remove from cart
     * Otherwise set quantity.
     *
     * @param number    the item number string
     * @param quantity  the number of items
     * @throws          InvalidCartQuantityException
     */    
    public synchronized void multiBulkUpdate(String number, String quantity) {
        String[] numberList = number.split(";");
        String[] quantityList = quantity.split(";");
        if (numberList.length != quantityList.length) {
            throw new InvalidCartQuantityException("Invalid value for bulk order.");
        } else {
            for (int i = 0; i < numberList.length; i++) {
                bulkUpdate(numberList[i], quantityList[i]);
            }
        }
    }

    /**
     * Clear the cart back to intial state
     */
    public synchronized void clear() {
        elements.clear();
        setNumberOfItems(0);
        PriceManager.getPriceManager().resetCost(this.cost);
    }

    // Getters

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
    @XmlElementWrapper(name="cart-elements")
    @XmlElement(name="cart-element")
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
     * Returns the cost object stored in the cart
     *
     * @return the cost object in the cart
     */
    @XmlElement(name="cost")
    public synchronized Cost getCost() {
        return cost;
    }

    // Queries

    /**
     * Returns true if the cart element for given
     * the specified item number exists in the carts,
     * otherwise false.
     *
     * @param number    the item number string
     * @return          true if cart element exists, otherwise false.
     */
    public synchronized boolean hasElement(String number) {
        System.out.println("element checked");
        return elements.containsKey(number);
    }

    // Private

    /**
     * Sets the number of items in the cart
     *
     * @param numberOfItems     in the cart
     */
    private void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    /**
     * Shift the number of items in the cart by
     * the specified amount.
     *
     * @param shift     the number of items to increase or decrease by
     */
    private void shiftNumberOfItems(int shift) {
        setNumberOfItems(numberOfItems + shift);
    }

} // Cart

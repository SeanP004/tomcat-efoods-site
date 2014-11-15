package model.cart;

import java.util.*;
import model.catalog.*;

public class Cart {

    private Map<String, CartElement> elements;
    private int                      numberOfItems;
    private Catalog                  catalog;

    public Cart(Catalog catalog) {
        this.catalog = catalog;
        this.elements = new LinkedHashMap<String, CartElement>();
        this.numberOfItems = 0;
    }

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

    public synchronized List<CartElement> getElements() {
        return new ArrayList<CartElement>(elements.values());
    }

    public synchronized int getNumberOfItems() {
        return numberOfItems;
    }

    public synchronized void clear() {
        elements.clear();
        numberOfItems = 0;
    }

} // Cart

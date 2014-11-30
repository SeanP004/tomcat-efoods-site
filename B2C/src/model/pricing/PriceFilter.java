package model.pricing;

import model.cart.CartElement;

/**
 * Interface that allows the ad-hoc
 * manipulation of the pricing of products
 * in the store, given the state of a
 * shopping cart presented and the price
 * manager.
 */
public interface PriceFilter {

    /**
     * Perform filter of the cost, given its
     * corresponding price manager. Returns a
     * boolean flag to stop cascade of filters,
     * If true, stops cascade, otherwise cascades.
     * 
     * @param cost  the cost object to manipulate
     * @param ce    the newly added item
     * @param qty   the number of items
     * @param pm    the price manager
     * @return      boolean flag to stop cascade of filters,
     */
	boolean filter(Cost cost, CartElement ce, int qty, PriceManager pm);

}

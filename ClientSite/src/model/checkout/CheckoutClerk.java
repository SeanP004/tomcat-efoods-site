package model.checkout;

import model.cart.*;
import model.account.*;

public class CheckoutClerk {

    private static CheckoutClerk singleton = null;

    private int orders = 0;

    private CheckoutClerk() { }

    // Public

    public Receipt checkout(Account customer, Cart cart) {
    	Receipt receipt;
        orders += 1;
        return new Receipt(orders, customer, cart, null);
    }

    // Static

    public static CheckoutClerk getClerk() {
        if (singleton == null) {
            singleton = new CheckoutClerk();
        }
        return singleton;
    }

} // CheckoutClerk

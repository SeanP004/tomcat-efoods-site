package model.checkout;

import java.util.*;
import java.text.*;	
import javax.xml.bind.annotation.*;
import model.cart.*;

@XmlRootElement(name="order")
public class Receipt {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-mm-dd");

    private int  orderId;
    private Date submitDate;
    private User customer;
    private Cart cart;

    public Receipt() { } // To make JAXB happy

    /**
     * Create a receipt object for a new
     * Purchase order.
     *
     * @param  orderId  the order unique identifier
     * @param  customer the user info object
     * @param  cart     the user's shopping cart
     * @return          this new object
     */
    public Receipt(int orderId, User customer, Cart cart) {
        this.orderId    = orderId;
        this.submitDate = Calendar.getInstance().getTime(); // Date now
        this.customer   = customer;
        this.cart       = cart;
    }

    // Getters

    @XmlAttribute
    public int getOrderId() {
        return orderId;
    }

    @XmlAttribute
    public String getSubmitDate() {
        return SDF.format(submitDate);
    }

    public User getCustomer() {
        return customer;
    }

    public Cart getCart() {
        return cart;
    }

} // Receipt

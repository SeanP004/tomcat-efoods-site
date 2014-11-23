package model.order;

import java.util.*;
import javax.xml.bind.annotation.*;


/**
 * Cart stores the item collected of CartElement as an object shopping cart
 * stores the item object and the number of item in cart
 */
@XmlRootElement(name="Order")
public class Order {

    private Map<String, Integer> elements;
    private int numberOfElements ;

    /**
     * Order constructor
     */
    public Order() {
        
        this.elements = new HashMap<String, Integer>(); 
        setNumberOfElements(0);
    }
    
    public Order(Map<String, Integer> elements) {
        this.elements = elements;
        this.numberOfElements = elements.size();
    }

 
    /**
     * Add elements to the Order
     */
    
    public void add (String element, int qty)
    {
        if (getNumberOfItems() == 0){
            this.elements.put(element, qty);
            shiftNumberOfElements(1);
        }
        else {
            if(this.elements.containsKey(element)){
                this.elements.put(element, this.elements.get(element)+qty);
            }
            else {
                this.elements.put(element, qty);
                shiftNumberOfElements(1);
            }
        }
    }
    
 

    /**
     * Returns the number of elements in the Order
     *
     */
    @XmlAttribute(name="size")
    public synchronized int getNumberOfItems() {
        return numberOfElements;
    }

    /**
     * Sets the number of elements in the Order
     * 
     */
    private void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    /**
     * Shift the number of items in the Order by
     * the specified amount.
     */
    private void shiftNumberOfElements(int shift) {
        setNumberOfElements(numberOfElements + shift);
    }

} // Cart

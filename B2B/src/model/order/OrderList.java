package model.order;

import java.util.*;
import javax.xml.bind.annotation.*;


/**
 * Cart stores the item collected of CartElement as an object shopping cart
 * stores the item object and the number of item in cart
 */
@XmlRootElement(name="Order")
public class OrderList {

    private  Map<String, Integer> orderlist ;
    private int numberOfOrders;

    /**
     * Order constructor
     */
    
    public OrderList() {
        this.orderlist = new  HashMap<String, Integer> () ;
        this.numberOfOrders = 0;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }
    
    private void increaseNoOfOrders() {
        this.numberOfOrders= this.numberOfOrders++;
        
    }

    /**
     * Add elements to the Order
     */
    
    public void addOrder (Order o)
    {
        if (this.numberOfOrders == 0){
            this.orderlist.put(o.getItemNo(),o.getQty());
        }
        else {
            if(this.orderlist.containsKey(o.getItemNo())){
                
                int temp = this.orderlist.get(o.getItemNo());
                this.orderlist.put(o.getItemNo(), o.getQty()+temp);
            }
            else {
               
                this.orderlist.put(o.getItemNo(), o.getQty());
                
            }
             
        }
        
        increaseNoOfOrders();
    }
       
} // Order

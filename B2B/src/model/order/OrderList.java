package model.order;

import java.util.*;
import javax.xml.bind.annotation.*;


/**
 * Cart stores the item collected of CartElement as an object shopping cart
 * stores the item object and the number of item in cart
 */
@XmlRootElement(name="Order")
public class OrderList {

    private  Map<String, Integer> list ;
    private int numberOfOrders;

    /**
     * Order constructor
     */
    
    public OrderList() {
        this.list = new  HashMap<String, Integer> () ;
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
    public Map<String, Integer> getList() {
        return list;
    }

    /**
     * Add elements to the Order
     */
    
    public void addOrder (Order o)
    {
        if (this.numberOfOrders == 0){
            this.list.put(o.getItemNo(),o.getQty());
        }
        else {
            if(this.list.containsKey(o.getItemNo())){
                
                int temp = this.list.get(o.getItemNo());
                this.list.put(o.getItemNo(), o.getQty()+temp);
            }
            else {
               
                this.list.put(o.getItemNo(), o.getQty());
                
            }
             
        }
        
        increaseNoOfOrders();
    }
       
} // Order

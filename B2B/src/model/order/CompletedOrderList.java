package model.order;

import java.text.*;
import java.util.*;
import javax.xml.bind.annotation.*;

/**
 * A object that store the value of completed list of orders.
 *
 */
@XmlRootElement(name = "CompletedOrderList")
public class CompletedOrderList {

    private String               date;
    private List<CompletedOrder> oList = new ArrayList<CompletedOrder>();

    /**
     * Completed order list constructor that intalize the list of completed 
     * order.
     * 
     * @param date  the date of the order.
     * @param oList the list of the order to process.
     */
    public CompletedOrderList(String date, List<CompletedOrder> oList) {

        this.date = date;
        this.oList = oList;
    }

    /**
     * Completed order list constructor that intalize the list of completed 
     * order.
     */
    public CompletedOrderList() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        this.date = dateFormat.format(d);
    }

    /**
     * Get the date of the processed order list.
     * 
     * @return A string date value.
     */
    @XmlAttribute(name = "date")
    public String getDate() {
        return date;
    }
    
    /**
     * Get the list of completed orders.
     * 
     * @return The list of completed orders.
     */
    @XmlElement(name = "orders")
    public List<CompletedOrder> getoList() {
        return oList;
    }
    
    /**
     * Set the date of processed order list.
     * 
     * @param date The string value of data.
     */
    public void setDate(String date) {
        this.date = date;
    }
    
    /**
     * Set the list of completed orders.
     * 
     * @param oList The list of completed order.
     */
    public void setoList(List<CompletedOrder> oList) {
        this.oList = oList;
    }

}

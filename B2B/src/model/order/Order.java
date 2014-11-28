package model.order;

/**
 *  Store object varaible that were parsed from B2C Purchase Order
 *
 */

public class Order {

    private String itemNo;
    private int    Qty;

    /**
     * The purchase order constructor  
     * 
     * @param itemNo    store the item number
     * @param qty       store the quantity of the item
     */
    public Order(String itemNo, int qty) {
        this.itemNo = itemNo;
        Qty = qty;
    }

    //Getter
    
    /**
     * get the item number 
     * 
     * @return   the string value of the item number
     */
    public String getItemNo() {
        return itemNo;
    }
    
    /**
     * get the quantity 
     * 
     * @return  the integer value of the quantity
     */
    public int getQty() {
        return Qty;
    }

    //Setter
    
    /**
     * set the string value for the item
     * 
     * @param itemNo    an string value of the item
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }


    /**
     * set the integer value of the quantity
     * 
     * @param qty   a integer value of the quantity
     */
    public void setQty(int qty) {
        Qty = qty;
    }

}

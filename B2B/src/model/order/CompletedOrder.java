package model.order;

public class CompletedOrder {

    private String itemNo;
    private double price;
    private String confirm;
    private String wholesaler;

    /**
     * CompletedOrder constructor to set the procurement order object
     * 
     * @param itemNo        the item number string value
     * @param price         the lowest price for the item
     * @param confirm       the confirmation number 
     * @param wholesaler    the wholesaler that sell the project
     */
    public CompletedOrder(String itemNo, double price, String confirm,
            String wholesaler) {
        this.price = price;
        this.itemNo = itemNo;
        this.confirm = confirm;
        this.wholesaler = wholesaler;
    }

    //Getter 
    
    /**
     * Get the item number 
     * 
     * @return  The string value of the item number
     */
    public String getItemNo() {
        return itemNo;
    }
    
    /**
     * Get the price of the item 
     * 
     * @return The double value of the item price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Get the confirmation number of the item from wholesale
     * 
     * @return  The string value of the wholesaler conformation
     */
    public String getConfirm() {
        return confirm;
    }

    /**
     * Get the the wholesaler for the item 
     * 
     * @return  the string name of the wholesaler
     */
    public String getWholesaler() {
        return wholesaler;
    }
    
    //Setter
    
    /**
     * Set the price of the item
     * 
     * @param price  The double value of the item
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * Set the item number value
     * 
     * @param itemNo    The string value of the item number
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * Set the confirmation from the wholesaler
     * 
     * @param confirm   The conformation string value of wholesaler
     */
    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    /**
     * Set the wholesaler name
     * 
     * @param wholesaler The string name of the wholesaler
     */
    public void setWholesaler(String wholesaler) {
        this.wholesaler = wholesaler;
    }

}

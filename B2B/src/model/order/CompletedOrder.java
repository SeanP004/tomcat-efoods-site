package model.order;

public class CompletedOrder {

   
    private String itemNo;
    private double price;
    private String confirm ; 
    private String wholesaler ;
    

    public CompletedOrder(String itemNo, double price,String confirm,
            String wholesaler) {
        super();
        this.price = price;
        this.itemNo = itemNo;
        this.confirm = confirm;
        this.wholesaler = wholesaler;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getConferm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getWholesaler() {
        return wholesaler;
    }

    public void setWholesaler(String wholesaler) {
        this.wholesaler = wholesaler;
    }
   
    
}

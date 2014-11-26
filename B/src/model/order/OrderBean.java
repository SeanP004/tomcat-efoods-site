package model.order;

public class OrderBean {

    private String itemNo;
    private int    Qty;

    public OrderBean(String itemNo, int qty) {
        this.itemNo = itemNo;
        Qty = qty;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

}

package model.checkout;

import java.io.*;

public class PurchaseOrder {
    
    private File userHistoryDir;
    
    public PurchaseOrder(File userHistoryDir) {
        this.userHistoryDir = userHistoryDir;
        CheckoutClerk.getClerk().maintainDirectory();
    }

    public int totalPurchaseOrder() {
        return this.userHistoryDir.listFiles().length;
    }

    public File[] getPurchaseOrders() {
        return this.userHistoryDir.listFiles();
    }
    
    public File[] getPurchaseOrders(String regex) {
        return this.userHistoryDir.listFiles(new PurchaseOrderFilter(regex));
    }
}

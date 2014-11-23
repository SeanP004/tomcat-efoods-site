package model.dao;

import java.io.*;
import model.account.*;

public class OrdersDAO {

    private File userData;

    public OrdersDAO(File userData) {
        this.userData = userData;
        maintainDirectory();
    }

    private String getOrderFileNamePrefix(String accId) {
        return "po" + accId + "_";
    }   

    public synchronized String getNextOrderFileName(String accId, int id) {
        return String.format(getOrderFileNamePrefix(accId) + "%02d.xml", id);
    }

    // Public

    public synchronized String getNextOrderFileName(Account acc) {
        return getNextOrderFileName(acc.getId(), getPurchaseOrders(acc.getId()).length + 1);
    }

    public synchronized FileWriter getWriter(String fileName) throws IOException {
        File file = new File(userData, fileName);
        maintainDirectory();
        if (file.exists()) {
            throw new IOException("File already exists");
        }
        file.createNewFile();
        return new FileWriter(file);
    }

    public synchronized void maintainDirectory() {
        if (!userData.exists()) {
            userData.mkdir();
        }
    }
    
    public synchronized File[] getPurchaseOrders() {
        return userData.listFiles();
    }

    public synchronized int getPurchaseOrderTotal() {
        return getPurchaseOrders().length;
    }   

    public synchronized File[] getPurchaseOrders(final String accId) {
        return userData.listFiles(new FilenameFilter() {
            @Override public boolean accept(File dir, String name) {
                return name.startsWith(getOrderFileNamePrefix(accId));
            }
        });
    }

    public synchronized File getPurchaseOrder(String fileName) {
        return new File(userData, fileName);
    }

} // OrdersDAO

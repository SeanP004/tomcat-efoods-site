package model.dao;

import java.io.*;

import model.account.*;

/**
 * 
 * File System data access object for management and creation of files
 * 
 * Generate a new file. 
 * Checks if the parent directory exist else create.
 * File will be create if it does not exist.
 *
 */
public class OrdersDAO {

    private File userData;

    /**
     * OrderDAO constructor for initalization.
     * 
     * @param userData   is the file path loction of parent directory
     */
    public OrdersDAO(File userData) {
        this.userData = userData;
        maintainDirectory();
    }

    /** 
     * Automoatic generation of the file name according the date and time the
     * method was called as a html file
     * @return
     */
    private String getOrderFileNamePrefix(String accId) {
        if (accId.matches("^[a-zA-Z]+[0-9]+$")) {
            accId = accId.replaceFirst("^[a-zA-Z]+", ""); 
        }
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

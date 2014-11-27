package model.xml;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrdersDAO {

    private File reportDir;

    public OrdersDAO(){
    }
    
    public OrdersDAO(File userData) {
        this.reportDir = userData;
        maintainDirectory();
    }

    public String getOrderFileNamePrefix() {
        DateFormat dateFormat = new SimpleDateFormat("yyyymmdd_HHmmss");
        Date d = new Date();
        String today = dateFormat.format(d);
        
        return "report"+today+".xml" ; 
    }   

    // Public


    public synchronized FileWriter getWriter(String fileName) throws IOException {
        File file = new File(reportDir, fileName);
        maintainDirectory();
        if (file.exists()) {
            throw new IOException("File already exists");
        }
        file.createNewFile();
        return new FileWriter(file);
    }

    public synchronized void maintainDirectory() {
        if (!reportDir.exists()) {
            reportDir.mkdir();
        }
    }
    
    public synchronized File[] getPurchaseOrders() {
        return reportDir.listFiles();
    }

    public synchronized int getPurchaseOrderTotal() {
        return getPurchaseOrders().length;
    }   

    public synchronized File[] getPurchaseOrders(final String accId) {
        return reportDir.listFiles(new FilenameFilter() {
            @Override public boolean accept(File dir, String name) {
                return name.startsWith(getOrderFileNamePrefix());
            }
        });
    }

    public synchronized File getPurchaseOrder(String fileName) {
        return new File(reportDir, fileName);
    }

} // OrdersDAO

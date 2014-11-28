package model.xml;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * File System data access object for management and creation of files
 *
 */
public class OrdersDAO {

    private File reportDir;

    public OrdersDAO() {

    }

    /**
     * OrderDAO constructor for initalization.
     * 
     * @param userData   is the file path loction of parent directory
     */
    public OrdersDAO(File userData) {
        this.reportDir = userData;
        maintainDirectory();
    }

    /** 
     * Automoatic generation of the file name according the date and time the
     * method was called as a html file
     * @return
     */
    public String getOrderFileNamePrefix() {
        DateFormat dateFormat = new SimpleDateFormat("yyyymmdd_HHmmss");
        Date d = new Date();
        String today = dateFormat.format(d);

        return "report" + today + ".html";
    }

    // Public

    /**
     * Generate a new file. 
     * Checks if the parent directory exist else create.
     * File will be create if it does not exist.
     * 
     * @param fileName  the name of file to be generated;
     * @return the method that used to append text to requeted file name
     * 
     * @throws IOException  when the requested file already exist
     */
    public synchronized FileWriter getWriter(String fileName)
            throws IOException {
        File file = new File(reportDir, fileName);
        maintainDirectory();
        if (file.exists()) { throw new IOException("File already exists"); }
        file.createNewFile();
        return new FileWriter(file);
    }

    /**
     * Check if the current parent directory exist else create.
     */
    public synchronized void maintainDirectory() {
        if (!reportDir.exists()) {
            reportDir.mkdir();
        }
    }

    /**
     * Generate a list of file in the parent directory
     * 
     * @return  List of files.
     */
    public synchronized File[] getPurchaseOrders() {
        return reportDir.listFiles();
    }

    /**
     * Get a integer value of the current number of files in parent directory
     * 
     * @return  number of file in parent directory
     */
    public synchronized int getPurchaseOrderTotal() {
        return getPurchaseOrders().length;
    }

    /**
     * Get file that are filtered and equals the name of the string search.
     * 
     * @param fname the filtering element to match the file in the diectory
     * 
     * @return  list of files that match the filter.
     */
    public synchronized File[] getPurchaseOrders(final String fname) {
        return reportDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(getOrderFileNamePrefix());
            }
        });
    }

    public synchronized File getPurchaseOrder(String fileName) {
        return new File(reportDir, fileName);
    }

} // OrdersDAO

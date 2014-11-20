package model.checkout;

import java.io.*;
import java.util.Scanner;
import model.cart.*;
import model.common.XMLUtil;
import model.account.*;

public class CheckoutClerk {
    
    private static CheckoutClerk singleton = null;
    
    private int orders;
    private File userHistoryDir;
    
    private CheckoutClerk() { }
    
    private CheckoutClerk(File userHistoryDir) {
    	this.userHistoryDir = userHistoryDir;
    	maintainDirectory(userHistoryDir);
    	this.orders = userHistoryDir.listFiles().length;
    }
    
    //return true when a file was created
    private synchronized void maintainDirectory(File dir) {
            if (!dir.exists()) {
                dir.mkdir();
            }
    }

    // Public
    public synchronized String checkout(Account customer, Cart cart, File xsd, File xslt){
        //make sure directory folder exist to write to
        maintainDirectory(userHistoryDir);
        orders += 1;
        String orderFile = "po" + customer.getId() + "_" + String.format("%02d", orders) + ".xml";
        Receipt receipt = new Receipt(orders, customer, cart, orderFile);
        String data = XMLUtil.generate(new StringWriter(), receipt, xsd, xslt).toString();
        try{

            File file =new File(userHistoryDir, orderFile);

            //if file doesnt exists, then create it
            if(!file.exists()){
                file.createNewFile();
            }

            //true = append file
            FileWriter fileWritter = new FileWriter(file);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(data);
            bufferWritter.close();

        }catch(IOException e){
            e.printStackTrace();
        }
        
        return data;
    }
    
    // Static
    
    public static CheckoutClerk getClerk(File userHistoryDir) {
        if (singleton == null) {
            singleton = new CheckoutClerk(userHistoryDir);
        }
        return singleton;
    }

} // CheckoutClerk

package model.checkout;

import java.io.*;
import model.cart.*;
import model.common.*;
import model.exception.*;
import model.account.*;

public class CheckoutClerk {

    private static CheckoutClerk singleton = null;

    private int orders;
    private File userData;

    private CheckoutClerk(File userData) {
    	this.userData = userData;
    	maintainDirectory();
    	this.orders = userData.listFiles().length;
    }

    //return true when a file was created
    synchronized void maintainDirectory() {
        if (!userData.exists()) {
            userData.mkdir();
        }
    }

    // Public
    public synchronized String checkout(Account customer, Cart cart, File xsd, File xslt) throws ItemNotFoundException{
        if (cart.getNumberOfItems() > 0) {
            //make sure directory folder exist to write to
            maintainDirectory();
            orders += 1;
            String orderFile = "po" + customer.getId() + "_" + String.format("%02d", orders) + ".xml";
            Receipt receipt = new Receipt(orders, customer, cart, orderFile);
            String data = XMLUtil.generate(new StringWriter(), receipt, xsd, xslt).toString();
            try {
                File file = new File(userData, orderFile);
                // if file doesnt exists, then create it
                if (!file.exists()) {
                    file.createNewFile();
                }
                // true = append file
                FileWriter fileWritter = new FileWriter(file);
                BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                bufferWritter.write(data);
                bufferWritter.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
            // once saved to file the cart should be cleared
            cart.clear();
            return data;
        } else {
            throw new ItemNotFoundException("Requires items in the cart to checkout");
        }
    }

    // Static

    public static CheckoutClerk getClerk(File userData) {
        if (userData != null && singleton == null) {
            singleton = new CheckoutClerk(userData);
        }
        return singleton;
    }

    public static CheckoutClerk getClerk() {
        return getClerk(null);
    }

} // CheckoutClerk

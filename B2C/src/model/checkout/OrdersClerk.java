package model.checkout;

import java.io.*;
import model.cart.*;
import model.common.*;
import model.dao.*;
import model.exception.*;
import model.account.*;

public class OrdersClerk {

    private static OrdersClerk singleton = null;

    private int orders;
    private File xsd;
    private File xslt;
    private String xsltView;    
    private String prefix;
    private String udPrefix;
    private OrdersDAO dao;

    private OrdersClerk(File userData, File xsd, File xslt, 
                        String xsltView, String prefix, String udPrefix) {
        this.dao = new OrdersDAO(userData);
        this.xsd = xsd;
    	this.xslt = xslt;
    	this.xsltView = xsltView;
    	this.prefix = prefix;
    	this.udPrefix = udPrefix;
    	dao.maintainDirectory();
    	this.orders = dao.getPurchaseOrderTotal();
    }

    // Public

    public synchronized String checkout(String host, Account customer, Cart cart) 
                throws OrderCheckoutException {
        try {
            if (cart.getNumberOfItems() > 0) {
                this.orders += 1;
                StringWriter sw = new StringWriter();
                Receipt receipt = new Receipt(orders, customer, cart);
                String fileName = dao.getNextOrderFileName(customer);
                receipt.setUrl(host + prefix + fileName);
                Writer writer = dao.getWriter(fileName);
                writer.write("<?xml version='1.0' encoding='UTF-8'?>\n");
                writer.write("<?xml-stylesheet type='text/xsl' href='" + xsltView + "'?>\n");
                XMLUtil.generate(writer, receipt, xsd, xslt);
                XMLUtil.generate(sw, receipt);
                cart.clear();
                return sw.toString();
            } else {
                throw new OrderCheckoutException("Requires at least one item in the cart");
            }
        } catch (IOException e) {
            throw new OrderCheckoutException(e);
        }
    }

    public synchronized OrdersList getPurchaseOrders(String host) 
                throws OrderNotFoundException {
        File[] files = dao.getPurchaseOrders();
        if (files.length == 0) {
            throw new OrderNotFoundException();
        }
        return new OrdersList(host + prefix, files);
    }

    public synchronized OrdersList getPurchaseOrders(String host, String accId) 
                throws OrderNotFoundException {
        File[] files = dao.getPurchaseOrders(accId);
        if (files.length == 0) {
            throw new OrderNotFoundException();
        }
        return new OrdersList(host + prefix, files);
    }

    public synchronized File getPurchaseOrder(String fileName) 
                throws OrderNotFoundException {
        File file = dao.getPurchaseOrder(fileName);
        if (!file.exists()) {
            throw new OrderNotFoundException();
        }
        return new File(udPrefix, file.getName());
    }

    // Static

    public static OrdersClerk getClerk(File userData, File xsd, File xslt,
                String xsltView, String prefix, String udPrefix) {
        if (userData != null && singleton == null) {
            singleton = new OrdersClerk(userData, xsd, xslt, xsltView, prefix, udPrefix);
        }
        return singleton;
    }

    public static OrdersClerk getClerk() {
        return getClerk(null, null, null, null, null, null);
    }

} // CheckoutClerk

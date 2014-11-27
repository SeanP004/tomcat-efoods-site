package model.order;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

public class OrderHandler {

    private static String WTORONTO;
    private static String WVANCOUVER;
    private static String WHALIFAX;
    private HashMap <String, String> config;

    public OrderHandler(HashMap <String, String> config) throws Exception {
    	this.WTORONTO = config.get("WTORONTO");
    	this.WVANCOUVER = config.get("WVANCOUVER");
    	this.WHALIFAX = config.get("WHALIFAX");
    	this.config = config;
    }

    public String doOrder(int index,Order orderDetails) throws Exception {
        
        
        SOAPMessage  msg = MessageFactory.newInstance().createMessage();
        javax.xml.soap.MimeHeaders header = msg.getMimeHeaders();
        header.addHeader("SOAPAction", "");

        SOAPPart soap = msg.getSOAPPart();
        SOAPEnvelope envelope = soap.getEnvelope();
        SOAPBody    body  = envelope.getBody();
        

        QName childName = new QName("Order");
        SOAPElement order = body.addChildElement(childName);

        childName = new QName("itemNumber");
        SOAPElement product = order.addChildElement(childName);
        product.addTextNode(orderDetails.getItemNo());

        childName = new QName("quantity");
        SOAPElement price = order.addChildElement(childName);
        price.addTextNode(String.valueOf(orderDetails.getQty()));

        childName = new QName("key");
        SOAPElement key = order.addChildElement(childName);

        SOAPConnection sc = SOAPConnectionFactory.newInstance()
                .createConnection();

        SOAPMessage orderWHcon = null;

        
        orderWHcon = sc.call(msg, new URL(getWholesaler(index)));
        sc.close();

        // msg.writeTo(System.out);
        // orderWHcon.writeTo(System.out);

        org.w3c.dom.Node node4 = orderWHcon.getSOAPPart().getEnvelope()
                .getBody().getElementsByTagName("OrderReturn").item(0);

        return (node4.getTextContent().toString());
    }
    
    public String getWholesaler(int index){
     
        if (index == 0) {
           return WTORONTO;
        } else if (index == 1) {
           return WVANCOUVER ;
        } else if (index == 2) {
            return WHALIFAX;
        }
        else 
            return "ERROR" ;
        
    }
    
    public String getWholesalerName(int index){
        
        if (index == 0) {
           return "Toronto Wholesaler";
        } else if (index == 1) {
           return "Vancouver Wholesaler";
        } else if (index == 2) {
            return "Halifax Wholesaler"; 
        }
        else 
            return "ERROR" ;
        
    }

}

package model.order;

import java.net.*;
import java.util.*;
import javax.xml.namespace.*;
import javax.xml.soap.*;

/**
 * 
 * Use SOAP to access and generate the require orders to be handled
 *
 */
public class OrderHandler {

    private WholeSaler ws;
    private HashMap<String, String> config;

    /**
     * Order Handler constructor to initialize the the method.
     * 
     * @param config    file that store the access path information.
     * @throws Exception Exception when an error occur.
     */
    public OrderHandler(HashMap<String, String> config) throws Exception {
        this.config = config;
        this.ws = new WholeSaler(config);
    }

    /**
     * Process the order to get the requeted SOAP string value
     * 
     * @param index         The integer value of the which wholesaler
     * @param orderDetails  The object of the order to compare with SOAP
     * 
     * @return              The string value of the SOAP parsed value
     * 
     * @throws Exception    Error of parsing occur
     */
    public String doOrder(int index, Order orderDetails) throws Exception {

        SOAPMessage msg = MessageFactory.newInstance().createMessage();
        javax.xml.soap.MimeHeaders header = msg.getMimeHeaders();
        header.addHeader("SOAPAction", "");

        SOAPPart soap = msg.getSOAPPart();
        SOAPEnvelope envelope = soap.getEnvelope();
        SOAPBody body = envelope.getBody();

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
        key.addTextNode(config.get("key"));

        SOAPConnection sc = SOAPConnectionFactory.newInstance()
                .createConnection();

        SOAPMessage orderWHcon = null;

        orderWHcon = sc.call(msg, new URL(getWholesaler(index)));
        sc.close();

        org.w3c.dom.Node node4 = orderWHcon.getSOAPPart().getEnvelope()
                .getBody().getElementsByTagName("OrderReturn").item(0);

        return (node4.getTextContent().toString());
    }

    /**
     * Index the wholesaler according to index
     * 
     * @param index An requested wholesaler url according to index
     * 
     * @return return the wholesaler url link
     */
    public String getWholesaler(int index) {
        switch(index) {
            case 0: return ws.WTORONTO; 
            case 1: return ws.WVANCOUVER; 
            case 2: return ws.WHALIFAX; 
            default: return "ERROR"; 
        }
    }

    /**
     * Index the the wholesaler accourdingly by thier name
     * 
     * @param index An requested wholesaler url according to index
     * 
     * @return the name of the wholesaler
     */
    public String getWholesalerName(int index) {
        switch(index) {
            case 0: return "Toronto Wholesaler"; 
            case 1: return "Vancouver Wholesaler"; 
            case 2: return "Halifax Wholesaler"; 
            default: return "ERROR"; 
        }
    }

}

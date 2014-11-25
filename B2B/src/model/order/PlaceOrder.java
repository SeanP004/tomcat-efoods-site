package control;

import java.io.IOException;
import java.net.URL;
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
import model.order.Order;
import model.order.OrderList;

public class PlaceOrder {

    final private static String WTORONTO   = "http://roumani.eecs.yorku.ca:4413/axis/YYZ.jws";
    final private static String WVANCOUVER = "http://roumani.eecs.yorku.ca:4413/axis/YVR.jws";
    final private static String WHALIFAX   = "http://roumani.eecs.yorku.ca:4413/axis/YHZ.jws";

    private SOAPMessage         msg;
    private SOAPBody            body;
    private Order               orderDetails;

    public PlaceOrder(Order o) throws Exception {

        msg = MessageFactory.newInstance().createMessage();

        javax.xml.soap.MimeHeaders header = msg.getMimeHeaders();
        header.addHeader("SOAPAction", "");

        SOAPPart soap = msg.getSOAPPart();
        SOAPEnvelope envelope = soap.getEnvelope();
        body = envelope.getBody();

        this.orderDetails = o;

    }

    public String doOrder(int i) throws Exception {

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
        key.addTextNode("4413secret");

        SOAPConnection sc = SOAPConnectionFactory.newInstance()
                .createConnection();

        int index = 1;
        SOAPMessage orderWHcon = null;

        // need to add error for -1
        if (index == 0) {
            orderWHcon = sc.call(msg, new URL(WTORONTO));
        } else if (index == 1) {
            orderWHcon = sc.call(msg, new URL(WVANCOUVER));
        } else if (index == 2) {
            orderWHcon = sc.call(msg, new URL(WHALIFAX));
        }
        sc.close();

        // msg.writeTo(System.out);
        // orderWHcon.writeTo(System.out);

        org.w3c.dom.Node node4 = orderWHcon.getSOAPPart().getEnvelope()
                .getBody().getElementsByTagName("OrderReturn").item(0);

        return (node4.getTextContent().toString());
    }

}

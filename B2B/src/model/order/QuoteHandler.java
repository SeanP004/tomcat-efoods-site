package model.order;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.xml.soap.*;

/**
 * 
 * Get a quotation of SOAP with QuoteHandler
 *
 */
public class QuoteHandler {

    private WholeSaler ws;

    /**
     * QuoteHandler constructor intalize 
     *  
     * @param config        The configuration of the system path.
     * 
     * @throws Exception    Excetpion of a error.
     */
    public QuoteHandler(HashMap<String, String> config) throws Exception {
        this.ws = new WholeSaler(config);
    }

    /**
     * Parse the SOAP to get a double array of all prices
     * 
     * @param o     An object of the order
     * @return      A double array of all prices in SOAP
     * 
     * @throws Exception    Exceptption when error has occur.
     */
    public double[] getQuoteArray(Order o) throws Exception {

        SOAPMessage msg = MessageFactory.newInstance().createMessage();
        javax.xml.soap.MimeHeaders header = msg.getMimeHeaders();
        header.addHeader("SOAPAction", "");

        SOAPPart soap = msg.getSOAPPart();
        SOAPEnvelope envelope = soap.getEnvelope();
        SOAPBody body = envelope.getBody();

        body.addChildElement("quote").addChildElement("Itemnumber")
                .addTextNode(o.getItemNo());

        SOAPConnection sc = SOAPConnectionFactory.newInstance()
                .createConnection();

        // create 3 connections --------------------------
        SOAPMessage yyz = sc.call(msg, new URL(ws.WTORONTO));
        SOAPMessage yvr = sc.call(msg, new URL(ws.WVANCOUVER));
        SOAPMessage yhz = sc.call(msg, new URL(ws.WHALIFAX));
        sc.close();

        // get the quote --------------------------
        org.w3c.dom.Node node1 = yyz.getSOAPPart().getEnvelope().getBody()
                .getElementsByTagName("quoteReturn").item(0);
        org.w3c.dom.Node node2 = yvr.getSOAPPart().getEnvelope().getBody()
                .getElementsByTagName("quoteReturn").item(0);
        org.w3c.dom.Node node3 = yhz.getSOAPPart().getEnvelope().getBody()
                .getElementsByTagName("quoteReturn").item(0);

        // -- place in array
        double quoteArray[] = new double[3];
        quoteArray[0] = Double.parseDouble(node1.getTextContent().toString());
        quoteArray[1] = Double.parseDouble(node2.getTextContent().toString());
        quoteArray[2] = Double.parseDouble(node3.getTextContent().toString());

        // System.out.println("Q prices : "+quoteArray[0]+" "+quoteArray[1]+" "+quoteArray[2]);

        return quoteArray;
    }

    /**
     * Get the minimum price from an array of double;
     * 
     * @param quoteArray    a array of double.
     * 
     * @return  the smallest item in a array.
     */
    public int getMinPrice(double[] quoteArray) {

        int minIndex = -1;
        double smallest = Double.MAX_VALUE;
        
        for (int i = 0; i < quoteArray.length; i++) {
            if (quoteArray[i] > 0 && smallest > quoteArray[i]) {
                smallest = quoteArray[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

}

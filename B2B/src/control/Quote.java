package control;

import java.net.URL;
import java.io.IOException;
import java.net.URL;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import model.order.OrderList;




public class Quote {

    final private static String WTORONTO    = "http://roumani.eecs.yorku.ca:4413/axis/YYZ.jws"  ;
    final private static String WVANCOUVER  = "http://roumani.eecs.yorku.ca:4413/axis/YVR.jws" ;
    final private static String WHALIFAX    = "http://roumani.eecs.yorku.ca:4413/axis/YHZ.jws"  ;
    
    private SOAPMessage msg;
    private SOAPBody body ;
    
    private OrderList order; 
    
    public Quote() throws Exception {
            
        msg = MessageFactory.newInstance().createMessage();
        
        javax.xml.soap.MimeHeaders header = msg.getMimeHeaders();
        header.addHeader("SOAPAction", "");
        
        SOAPPart soap = msg.getSOAPPart();
        SOAPEnvelope envelope = soap.getEnvelope();
        body = envelope.getBody();

    }
    
   
    public double[] getQuoteArray() throws Exception{
        
        body.addChildElement("quote").addChildElement("itemNumber").addTextNode("0905A771");
        
        SOAPConnection sc = SOAPConnectionFactory.newInstance().createConnection();
        
        // create 3 connections --------------------------
        SOAPMessage yyz = sc.call(msg, new URL(WTORONTO));
        SOAPMessage yvr = sc.call(msg, new URL(WVANCOUVER));
        SOAPMessage yhz = sc.call(msg, new URL(WHALIFAX));
        sc.close();
       
        // get the quote --------------------------
        org.w3c.dom.Node node1 = yyz.getSOAPPart().getEnvelope().getBody().getElementsByTagName("quoteReturn").item(0);
        org.w3c.dom.Node node2 = yvr.getSOAPPart().getEnvelope().getBody().getElementsByTagName("quoteReturn").item(0);
        org.w3c.dom.Node node3 = yhz.getSOAPPart().getEnvelope().getBody().getElementsByTagName("quoteReturn").item(0);

        //-- place in array
        double quoteArray[] = new double[3];
        quoteArray[0]= Double.parseDouble(node1.getTextContent().toString()); 
        quoteArray[1]= Double.parseDouble(node2.getTextContent().toString()); 
        quoteArray[2]= Double.parseDouble(node3.getTextContent().toString()); 
        
        return quoteArray;
    }
    
    // return the array index of the cheapest price 
    public int getCheapPrice(double[] quoteArray) {
        
        int minIndex = -1; 
        
        double smallest = Double.MAX_VALUE; 

        for(int i=0;i<quoteArray.length;i++) {
            if(quoteArray[i] > 0 && smallest > quoteArray[i])
            {
                smallest =  quoteArray[i]; 
                minIndex= i ;      
            }  
        } 
        return minIndex; 
    }
    
}

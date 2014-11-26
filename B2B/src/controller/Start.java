package controller;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.XMLReader;
import model.order.Order;
import model.order.OrderList;
import model.order.OrderHandler;
import model.order.QuoteHandler;
import model.xml.XMLHandler;

public class Start {

    public static void main(String[] args) throws Exception {

        //-----Get Details from XML
        
        XMLHandler xmlh = new XMLHandler();

        List<String> files = xmlh.getFileDetails();
        OrderList orderlist = xmlh.getOrderDetails(files);

       
        // ---- PerformOrderOperation

        Map<String, Integer> list = orderlist.getList();

        Iterator it = list.entrySet().iterator();

        while (it.hasNext()) {

            Map.Entry pairs = (Map.Entry) it.next();

            Order o = new Order(pairs.getKey().toString(),
                    Integer.parseInt(pairs.getValue().toString()));

            QuoteHandler q = new QuoteHandler();
            double quotedPrices[] = q.getQuoteArray(o);

            int minPriceIndex = q.getCheapPrice(quotedPrices);
           // System.out.println("min ind = "+minPriceIndex);

            OrderHandler po = new OrderHandler();

           // String conf = po.doOrder(minPriceIndex,o);

            //System.out.println(conf);
           // System.out.println(" ------ done -------- \n \n ");
            it.remove(); // avoids a ConcurrentModificationException
            
        }
        
      
    }

}

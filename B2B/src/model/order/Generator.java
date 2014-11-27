package model.order;

import java.io.*;
import java.text.*;
import java.util.*;
import model.common.XMLUtil;
import model.order.*;
import model.xml.*;

public class Generator {
    
    
    public void generate() throws Exception{
        
        
        //-----Get Details from XML
        
        XMLHandler xmlh = new XMLHandler();

        List<String> files = xmlh.getFileDetails();
        OrderList orderlist = xmlh.getOrderDetails(files);
        
        //------------------------ Generate report 
        CompletedOrderList col = new CompletedOrderList();
        CompletedOrder co = null ;
       
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

            String conf = po.doOrder(minPriceIndex,o);

            System.out.println(conf+pairs.getKey().toString()+" "+ 
                    quotedPrices[minPriceIndex] + " " + po.getWholesaler(minPriceIndex));
            
            co = new CompletedOrder(pairs.getKey().toString(), 
                    quotedPrices[minPriceIndex], conf, po.getWholesaler(minPriceIndex));
           // System.out.println(" ------ done -------- \n \n ");
            it.remove(); // avoids a ConcurrentModificationException
            
            col.getoList().add(co);
            
        }
        
        xmlh.createReport(col);
          
    }
    
    


}

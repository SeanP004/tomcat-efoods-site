package control;

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
import model.order.OrderBean;
import model.order.OrderList;
import model.order.OrderHandeler;
import model.order.QuoteHandeler;
import model.xml.XMLHandeler;

public class Start {

    public static void main(String[] args) throws Exception {

        //-----Get Details from XML
        
        XMLHandeler xmlh = new XMLHandeler();

        List<String> files = xmlh.getFileDetails();
        OrderList orderlist = xmlh.getOrderDetails(files);

       
        // ---- PerformOrderOperation

        Map<String, Integer> list = orderlist.getList();

        Iterator it = list.entrySet().iterator();

        while (it.hasNext()) {

            Map.Entry pairs = (Map.Entry) it.next();

            OrderBean o = new OrderBean(pairs.getKey().toString(),
                    Integer.parseInt(pairs.getValue().toString()));

            QuoteHandeler q = new QuoteHandeler();
            double quotedPrices[] = q.getQuoteArray(o);

            int minPriceIndex = q.getCheapPrice(quotedPrices);
            System.out.println("min ind = "+minPriceIndex);

            OrderHandeler po = new OrderHandeler();

            String conf = po.doOrder(minPriceIndex,o);

            System.out.println(conf);
            System.out.println(" ------ done -------- \n \n ");
            it.remove(); // avoids a ConcurrentModificationException
        }

    }

}

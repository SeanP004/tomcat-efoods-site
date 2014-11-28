package model.order;

import java.util.*;	
import model.xml.*;

/**
 * 
 * Implement the Methods to access the SOAP to generate the requested data
 * to form an html file to be stored physically to disk
 *
 */
public class Generator {

    public void generate(HashMap<String, String> config) throws Exception {

        XMLHandler xmlh = new XMLHandler(config);
        OrderList orderlist = null;
        
        try {
            List<String> files = xmlh.getFileDetails();
            orderlist = xmlh.getOrderDetails(files);
        } catch (Exception e) {
            System.out.println("No Orders!");
        }

        if (!(orderlist == null)) {
            CompletedOrderList col = new CompletedOrderList();
            CompletedOrder co = null;
            Map<String, Integer> list = orderlist.getList();

            for (Map.Entry<String, Integer> entry : list.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                Order o = new Order(key, Integer.parseInt(value.toString()));
                QuoteHandler q = new QuoteHandler(config);
                double quotedPrices[]   = q.getQuoteArray(o);
                int minPriceIndex       = q.getMinPrice(quotedPrices);
                OrderHandler po         = new OrderHandler(config);
                String conf             = po.doOrder(minPriceIndex, o);
                co = new CompletedOrder(key,
                        model.common.CommonUtil.roundOff(quotedPrices[minPriceIndex])
                        , conf, po.getWholesalerName(minPriceIndex));
                col.getoList().add(co);
            }
            
            xmlh.createReport(col);
        }
    }
}

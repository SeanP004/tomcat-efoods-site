package model.order;

import java.util.*;

import model.xml.*;
import model.common.CommonUtil;

public class Generator {

    public void generate(HashMap <String, String> config) throws Exception {

        // -----Get Details from XML

        XMLHandler xmlh = new XMLHandler(config.get("dataURL"));

        OrderList orderlist = null;
        try {
            List<String> files = xmlh.getFileDetails();
            orderlist = xmlh.getOrderDetails(files);
        } catch (Exception e) {
            System.out.println("No Orders!");
        }

        if (! (orderlist == null)) {
            // ------------------------ Generate report
            CompletedOrderList col = new CompletedOrderList();
            CompletedOrder co = null;

            // ---- PerformOrderOperation

            Map<String, Integer> list = orderlist.getList();

            Iterator it = list.entrySet().iterator();

            while (it.hasNext()) {

                Map.Entry pairs = (Map.Entry) it.next();

                Order o = new Order(pairs.getKey().toString(),
                        Integer.parseInt(pairs.getValue().toString()));

                QuoteHandler q = new QuoteHandler(config);
                double quotedPrices[] = q.getQuoteArray(o);

                int minPriceIndex = q.getCheapPrice(quotedPrices);
                // System.out.println("min ind = "+minPriceIndex);

                OrderHandler po = new OrderHandler(config);

                String conf = po.doOrder(minPriceIndex, o);

                System.out.println(conf + pairs.getKey().toString() + " "
                        +  model.common.CommonUtil.roundOff(quotedPrices[minPriceIndex]) + " "
                        + po.getWholesalerName(minPriceIndex));

                co = new CompletedOrder(pairs.getKey().toString(),
                        model.common.CommonUtil.roundOff(quotedPrices[minPriceIndex]), conf,
                        po.getWholesalerName(minPriceIndex));

                // System.out.println(" ------ done -------- \n \n ");
                it.remove(); // avoids a ConcurrentModificationException

                col.getoList().add(co);
            }
            xmlh.createReport(col);
        }
    }
}

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
import model.order.OrderBean;
import model.order.OrderList;
import model.order.OrderHandeler;
import model.order.QuoteHandeler;

public class Start {

    public static void main(String[] args) throws Exception {

        // --- Get PO names

        final String urlPO = "http://localhost:4413/eFoods/api/order/";

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        List<String> files = new ArrayList<String>();
        OrderList orderlist = new OrderList();

        try {

            db = dbf.newDocumentBuilder();
            Document doc = db.parse(new URL(urlPO).openStream());

            NodeList nl = doc.getElementsByTagName("file");

            for (int i = 0; i < nl.getLength(); i++) {
                files.add(i, nl.item(i).getTextContent().toString());
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        // ---------------------------------------------------

        String url = "http://localhost:4413/eFoods/api/order/";

        for (int i = 0; i < files.size(); i++) {
            url = files.get(i);
            System.out.println(url);

            try {

                db = dbf.newDocumentBuilder();
                Document doc = db.parse(new URL(url).openStream());

                NodeList itemList = doc.getElementsByTagName("item");
                NodeList qList = doc.getElementsByTagName("quantity");
                // System.out.println(itemList.item(0).getAttributes().getNamedItem("number").getNodeValue());

                for (int x = 0; x < itemList.getLength() ; x++) {

                    OrderBean o = new OrderBean(itemList.item(x)
                            .getAttributes().getNamedItem("number")
                            .getNodeValue(), Integer.parseInt(qList.item(x)
                            .getTextContent().toString()));
                    
                    System.out.print(o.getItemNo()+ " "+ o.getQty());

                    orderlist.addOrder(o);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            // ------------------------------------------------------

            Map<String, Integer> list = orderlist.getList();

            Iterator it = list.entrySet().iterator();

            while (it.hasNext()) {

                Map.Entry pairs = (Map.Entry) it.next();

                OrderBean o = new OrderBean(pairs.getKey().toString(),
                        Integer.parseInt(pairs.getValue().toString()));

                QuoteHandeler q = new QuoteHandeler(o);
                double quotedPrices[] = q.getQuoteArray();

                int minPriceIndex = q.getCheapPrice(quotedPrices);

                OrderHandeler po = new OrderHandeler(o);

                String conf = po.doOrder(minPriceIndex);

                System.out.print(conf);

                // System.out.println(pairs.getKey() + " = " +
                // pairs.getValue());
                it.remove(); // avoids a ConcurrentModificationException

            }

        }

    }

}

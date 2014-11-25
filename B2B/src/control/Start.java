package control;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import model.order.OrderBean;
import model.order.OrderList;
import model.order.PlaceOrder;
import model.order.Quote;

public class Start {

    public static void main(String[] args) throws Exception {

        // --- Get PO names

        String urlPO = "http://localhost:4413/eFoods/api/order/";

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
                // System.out.println(files.get(i));
            }

            // ------ TO PRINT THE FULL XML FILE
            // TransformerFactory factory = TransformerFactory.newInstance();
            // Transformer xform = factory.newTransformer();
            // xform.transform(new DOMSource(doc), new
            // StreamResult(System.out));

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

                for (int x = 0; x < itemList.getLength(); x++) {

                    OrderBean o = new OrderBean(itemList.item(x)
                            .getAttributes().getNamedItem("number")
                            .getNodeValue(), Integer.parseInt(qList.item(x)
                            .getTextContent().toString()));

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

                Quote q = new Quote(o);
                double quotedPrices[] = q.getQuoteArray();

                int minPriceIndex = q.getCheapPrice(quotedPrices);

                PlaceOrder po = new PlaceOrder(o);

                 String conf = po.doOrder(minPriceIndex);

                System.out.print(conf);

                // System.out.println(pairs.getKey() + " = " +
                // pairs.getValue());
                it.remove(); // avoids a ConcurrentModificationException

            }

        }

    }

}

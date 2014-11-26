package model.xml;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import model.order.OrderBean;
import model.order.OrderList;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XMLHandeler {

    public List<String> getFileDetails() throws Exception {

        final String urlPO = "http://localhost:4413/eFoods/api/order/";

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        List<String> files = new ArrayList<String>();

        db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL(urlPO).openStream());

        NodeList nl = doc.getElementsByTagName("file");

        for (int i = 0; i < nl.getLength(); i++) {
            files.add(i, nl.item(i).getTextContent().toString());
        }

        return files;
    }

    public OrderList getOrderDetails(List<String> files) throws Exception {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        OrderList orderlist = new OrderList();

        String url = "http://localhost:4413/eFoods/api/order/";

        for (int i = 0; i < files.size(); i++) {
            url = files.get(i);
            System.out.println("url is : "+ url);

            db = dbf.newDocumentBuilder();
            Document doc = db.parse(new URL(url).openStream());

            NodeList itemList = doc.getElementsByTagName("item");
            NodeList qList = doc.getElementsByTagName("quantity");
          //  System.out.println(itemList.item(0).getAttributes().getNamedItem("number").getNodeValue()+" "+qList.item(0).getTextContent());

            for (int x = 0; x < itemList.getLength(); x++) {

                OrderBean o = new OrderBean(itemList.item(x).getAttributes()
                        .getNamedItem("number").getNodeValue(),
                        Integer.parseInt(qList.item(x).getTextContent()
                                .toString()));

                System.out.println(o.getItemNo() + " " + o.getQty());

                orderlist.addOrder(o);
            }

        }
        return orderlist;
    }

}

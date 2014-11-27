package model.xml;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import model.common.XMLUtil;
import model.order.CompletedOrderList;
import model.order.Order;
import model.order.OrderList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sun.jdbc.odbc.OdbcDef;

public class XMLHandler {
	private String dataURL;
	
	public XMLHandler (String sourceURL) {
		this.dataURL = sourceURL;
	}

    public List<String> getFileDetails() throws Exception {

        final String urlPO = dataURL;

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

        String url = dataURL;

        for (int i = 0; i < files.size(); i++) {
            url = files.get(i);
            // System.out.println("url is : "+ url);

            db = dbf.newDocumentBuilder();
            Document doc = db.parse(new URL(url).openStream());

            NodeList d = doc.getElementsByTagName("order");
            String date = d.item(0).getAttributes().getNamedItem("submitted")
                    .getNodeValue();

            if (checkDate(date)) {
                
                NodeList itemList = doc.getElementsByTagName("item");
                NodeList qList = doc.getElementsByTagName("quantity");
                // System.out.println(itemList.item(0).getAttributes().getNamedItem("number").getNodeValue()+" "+qList.item(0).getTextContent());

                for (int x = 0; x < itemList.getLength(); x++) {

                    Order o = new Order(itemList.item(x)
                            .getAttributes().getNamedItem("number")
                            .getNodeValue(), Integer.parseInt(qList.item(x)
                            .getTextContent().toString()));

                    // System.out.println(o.getItemNo() + " " + o.getQty());

                    orderlist.addOrder(o);
                }
            } 
            
        }
       
        return orderlist;
    }

    private boolean checkDate(String podate) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        String today = dateFormat.format(d);
        //System.out.println("t "+today+" p "+podate);

        if (today.equals(podate))
            return true;
        else 
            return false;

    }

    public void createReport(CompletedOrderList col) throws IOException {
        
        File r = new File("reports");
        
        OrdersDAO dao = new OrdersDAO(r);
       
        String xsltView = "res/order.xslt";
        Writer sw = dao.getWriter(dao.getOrderFileNamePrefix());
       
        sw.write("<?xml version='1.0' encoding='UTF-8'?>\n");
        sw.write("<?xml-stylesheet type='text/xsl' href='" + xsltView + "'?>\n");
        XMLUtil.generate(sw, col);
        
        System.out.println(" ------ done --------  ");
        
        
    }

}

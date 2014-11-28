package model.xml;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import javax.xml.parsers.*;
import model.common.*;
import model.order.*;
import org.w3c.dom.*;

/**
 * 
 * Process and transform xml files into the desired respective form
 * the xslt is the key in transform the xml files
 *
 */
public class XMLHandler {
    private HashMap<String, String> config;

    /**
     * Intalize the constructor for XMLHandler
     *  
     * @param config    the file of the of access path for setup
     */
    public XMLHandler(HashMap<String, String> config) {
        this.config = config;
    }

    /**
     * Access the file according to the config give directory 
     * and create a list of file from there.
     *   
     * @return  a list of files
     * 
     * @throws Exception    when a error has occur
     */
    public List<String> getFileDetails() throws Exception {

        final String urlPO = config.get("dataURL");

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

    /**
     * process a list of file and generate a list of object orders
     * 
     * @param files     An list of files
     * 
     * @return          an list of orders
     * 
     * @throws Exception when an error has occur
     */
    public OrderList getOrderDetails(List<String> files) throws Exception {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        OrderList orderlist = new OrderList();

        String url = config.get("dataURL");

        for (int i = 0; i < files.size(); i++) {
            url = files.get(i);

            db = dbf.newDocumentBuilder();
            Document doc = db.parse(new URL(url).openStream());

            NodeList d = doc.getElementsByTagName("order");
            String date = d.item(0).getAttributes().getNamedItem("submitted")
                    .getNodeValue();

            if (checkDate(date)) {

                NodeList itemList = doc.getElementsByTagName("item");
                NodeList qList = doc.getElementsByTagName("quantity");

                for (int x = 0; x < itemList.getLength(); x++) {

                    Order o = new Order(itemList.item(x).getAttributes()
                            .getNamedItem("number").getNodeValue(),
                            Integer.parseInt(qList.item(x).getTextContent()
                                    .toString()));

                    orderlist.addOrder(o);
                }
            }
        }

        return orderlist;
    }

    /**
     * Check the and compare the date and return boolean.
     * 
     * @param podate    A string of date.
     * 
     * @return  If the data is equal to the current date true, else false.
     */
    private boolean checkDate(String podate) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        String today = dateFormat.format(d);

        if (today.equals(podate)) return true;
        else return false;

    }

    /**
     * generate and transform xml file according to the xslt.
     * 
     * @param col   object to be passed to be transformed
     * 
     * @throws IOException  when a error has occur.
     */
    public void createReport(CompletedOrderList col) throws IOException {

        File r = new File(config.get("storeDir"));

        OrdersDAO dao = new OrdersDAO(r);
        File xsltView = new File(config.get("xslt"));
        Writer sw = dao.getWriter(dao.getOrderFileNamePrefix());

        XMLUtil.generate(sw, col, null, xsltView);

        System.out.println(" ------ HTML file has be Generated --------  ");

    }

}

package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.account.*;
import model.cart.*;
import model.checkout.*;
import model.common.*;

/**
 * Servlet implementation class CartAPI Cart API Endpoint.
 */
// @WebServlet("/api/checkout")
public class CheckoutAPI extends HttpServlet {

    private static final String JSP_FILE = "/WEB-INF/xmlres/APIResponse.jspx";
    private static final String XSL_FILE = "/WEB-INF/xmlres/PO.xslt";
    private static final String XSD_FILE = "/WEB-INF/xmlres/PO.xsd";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        ServletContext sc       = getServletContext();
        HttpSession    sess     = req.getSession();
        Cart           cart     = (Cart)sess.getAttribute("cart");  
        StringWriter   sw       = new StringWriter();
        File           xslt     = new File(sc.getRealPath(XSL_FILE));
        String 		   xsdRealPath = sc.getRealPath(XSD_FILE);
        Receipt		   receipt;
        Account		   account = (Account)sess.getAttribute("account");
        
        if (cart == null) {
            sess.setAttribute("cart", cart = new Cart());}
        if (account == null) {
        	sess.setAttribute("account", account = new Account());}

        try {
            receipt = CheckoutClerk.getClerk().checkout(account, cart);
            String receiptData = XMLUtil.generate(sw, receipt, null, xslt).toString();
            req.setAttribute( "data", receiptData);
            StringReader receiptXML = new StringReader(receiptData);
            if (XMLUtil.validateXMLSchema(xsdRealPath, receiptXML)) {
            	System.out.println("write to file"); // TODO
            	cart.clear();
            }
            else {
            	System.out.println("template needs to be fixed"); // TODO
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }

        req.getRequestDispatcher(JSP_FILE).forward(req, res);
    } // doGet

} // CartAPI

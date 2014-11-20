package controller;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import model.cart.*;
import model.catalog.*;
import model.checkout.*;
import model.common.*;

/**
 * Servlet implementation class CartAPI Cart API Endpoint.
 */
// @WebServlet("/api/po")
public class PurchaseOrderAPI extends HttpServlet {

    private static final String JSP_FILE = "/WEB-INF/xmlres/APIResponse.jspx";
    private static final String XSL_FILE = "/WEB-INF/xmlres/PO.xslt";
    private static final String XSD_FILE = "/WEB-INF/xmlres/PO.xsd";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        ServletContext sc       = getServletContext();
        HttpSession    sess     = req.getSession();
        String         action   = req.getParameter("action");
        String         filter	= req.getParameter("filter");
        StringWriter   sw       = new StringWriter();
        File           xslt     = new File(sc.getRealPath(XSL_FILE));
        
        File userHistory = new File(sc.getRealPath(sc.getAttribute("userHistory").toString()));
        PurchaseOrder  po		= new PurchaseOrder(userHistory);

        try {

            if (action != null) {
                switch (action) {
                    case "list":
                    	File[] fileList;
                    	String out = "";
                    	if (filter != null) {
                    		fileList = po.getPurchaseOrders(filter);
                    	} else {
                        	fileList = po.getPurchaseOrders();
                    	}
                    	for (File file : fileList) {
                    		out += file.getName() + "\n";
                    	}
                    	req.setAttribute("data", out);
                        break;
                    default:
                        throw new RuntimeException("Bad action");
                }
            } else {
            	
            	File[] fileList;
            	String out = "";
            	System.out.println("trying to get");
            	fileList = po.getPurchaseOrders();
            	System.out.println("Files name");
            	for (File file : fileList) {
            		out += file.getName() + "\n";
            	}
            	req.setAttribute("data", out);
                //req.setAttribute("data", "Total files: " + po.getPurchaseOrderTotal());
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }

        req.getRequestDispatcher(JSP_FILE).forward(req, res);
    } // doGet

} // CartAPI

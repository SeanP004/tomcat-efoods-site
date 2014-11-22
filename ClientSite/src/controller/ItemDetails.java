package controller;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.account.Account;
import model.cart.Cart;
import model.catalog.Catalog;
import model.catalog.Item;
import model.catalog.ItemFilter;
import model.checkout.OrdersClerk;
import model.checkout.OrdersList;
import model.common.XMLUtil;

/**
 * Servlet implementation class ItemDetails
 */
//@WebServlet("/jsp/item/*")
public class ItemDetails extends HttpServlet {
	
    
    private static final String JSP_FILE = "/WEB-INF/pages/ItemDetails.jspx";
    
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
       
	    
	    
        String         target   = JSP_FILE;
        String         pathInfo = req.getPathInfo();
        ServletContext sc       = getServletContext();
        Catalog        catalog  = (Catalog)sc.getAttribute("catalog");

        
        try {
            if (pathInfo == null ) {
                pathInfo = "";
                
            } else if (pathInfo.charAt(0) == '/' && pathInfo.length() == 14 ) {
                pathInfo = pathInfo.substring(6, 14);
                
                Item item = catalog.getItem(pathInfo); 
                req.setAttribute("item", item);
            }
            else {
                req.setAttribute("item", "Invalid");
            }
                
         
            req.getRequestDispatcher(target).forward(req, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(404);
        }
        
	}
	



}

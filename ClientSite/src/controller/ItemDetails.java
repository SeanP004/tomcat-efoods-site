package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.catalog.*;
import model.exception.*;

/**
 * Servlet implementation class ItemDetails
 */
//@WebServlet("/jsp/item/*")
public class ItemDetails extends HttpServlet {

    private static final String JSP_FILE = "/WEB-INF/pages/ItemDetails.jspx";

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String         target   = JSP_FILE;
        String         pathInfo = req.getPathInfo();
        ServletContext sc       = getServletContext();
        Catalog        catalog  = (Catalog)sc.getAttribute("catalog");

        try {
            if (pathInfo == null) {
                throw new ItemNotFoundException();
            } else if (pathInfo.charAt(0) == '/' && pathInfo.length() == 14) {
                req.setAttribute("item", catalog.getItem(pathInfo.substring(6, 14)));
            } else {
                throw new ItemNotFoundException();
            }
            req.getRequestDispatcher(target).forward(req, res);
        } catch (Exception e) {
            res.sendError(404);
        }
	}

} // ItemDetails

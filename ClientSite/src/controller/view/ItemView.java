package controller.view;

import controller.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.catalog.*;
import model.exception.*;

/**
 * Servlet implementation class ItemView
 */
//@WebServlet("/view/item/*")
public class ItemView extends EndPointServlet {

    @Override
    protected void doRequest(String method, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        super.doRequest(method, req, res);

        ServletContext sc       = getServletContext();
        String         target   = (String)req.getAttribute("target");
        String         pathInfo = (String)req.getAttribute("pathInfo");
        Catalog        catalog  = (Catalog)sc.getAttribute("catalog");

        try {
            if (pathInfo == null) {
                throw new ItemNotFoundException();
            } else if (pathInfo.charAt(0) == '/') {
                req.setAttribute("item", catalog.getItem(pathInfo.substring(1)));
            } else {
                throw new ItemNotFoundException();
            }
            req.getRequestDispatcher(target).forward(req, res);
        } catch (Exception e) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    } // doRequest

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doRequest("GET", req, res);
    }

} // ItemDetails

package controller.api;

import java.io.*;
import controller.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Login API Endpoint.
 * Enables user to login and logout.
 */
//@WebServlet("/api/login")
public class LoginAPI extends EndPointServlet {

    @Override
    protected void doRequest(String method, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        super.doRequest(method, req, res);

        if (res.isCommitted()) {return;}

        ServletContext sc = getServletContext();
        HttpSession sess  = req.getSession();
        String action     = req.getParameter("action");
        String referrer   = req.getParameter("ref");

        if (action == null) {
            action = "login";
        }
        switch (action) {
            case "login": break;
            case "logout": sess.setAttribute("account", null); break;
        }

        res.sendRedirect(referrer == null ? sc.getContextPath() : referrer);
    } // doRequest

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doRequest("GET", req, res);
    }

} // LoginAPI

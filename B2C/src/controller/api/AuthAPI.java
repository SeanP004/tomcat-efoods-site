package controller.api;

import java.io.*;
import controller.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.account.*;
import model.common.*;
import model.exception.*;

/**
 * Callback servlet for authenication.
 * Auth API Endpoint.
 */
//@WebServlet("/api/auth")
public class AuthAPI extends EndPointServlet {

    @Override
    protected void doRequest(String method, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        super.doRequest(method, req, res);

        if (res.isCommitted()) {return;}
        
        ServletContext sc = getServletContext();
        HttpSession sess  = req.getSession();
        String signer     = req.getParameter("signer");
        String referrer   = req.getParameter("ref");
        String account    = req.getParameter("account");
        String name       = req.getParameter("name");
        String secret     = (String)sc.getAttribute("secret");
        String signature  = account + ";" + name + ";" + referrer + ";" + secret;

        try {
            if (!CommonUtil.md5sum(signature).equals(signer)) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                sess.setAttribute("account", new Account(account, name, null));
                res.sendRedirect(referrer);
            }
        } catch (Exception e) {
            // should never be reached
            throw new AppException(e);
        }
    } // doRequest

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doRequest("GET", req, res);
    }

} // AuthAPI

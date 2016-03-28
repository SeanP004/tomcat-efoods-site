package controller;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.account.*;

/**
 * Servlet implementation class EndPointServlet
 * Represents an endpoint servlet, which will make a
 * targetted request to a file or JSP.
 */
public abstract class EndPointServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = getServletContext();
        context.setAttribute("target://" + getServletName(), config.getInitParameter("target"));
        if (config.getInitParameter("restrictedUsers") != null) {
            List<String> restrictedUsers = new ArrayList<String>();
            for (String user : config.getInitParameter("restrictedUsers").split("[ \n]+")) {
                restrictedUsers.add(user);
            }
            context.setAttribute("restrictedUsers", restrictedUsers);
        }
    }

    protected void doRequest(String method, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        ServletContext sc = getServletContext();
        HttpSession sess  = req.getSession();
        String host       = (String)req.getAttribute("host");
        String ref        = host + req.getRequestURI() + (req.getQueryString() == null  ? "" : '?' + req.getQueryString());

        req.setAttribute("target", (String)sc.getAttribute("target://" + getServletName()));
        req.setAttribute("request", URLEncoder.encode(ref, "UTF-8"));

        // Restricted Users check

        if (sess.getAttribute("account") != null) {
            @SuppressWarnings("unchecked")
            List<String> restrictedUsers = (List<String>)sc.getAttribute("restrictedUsers");
            Account account = (Account)sess.getAttribute("account");
            if (restrictedUsers != null && !restrictedUsers.contains(account.getId())) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        }

        //System.out.println(this.getClass().getName()
        //        + " :: " + req.getRequestURI()
        //        + " : "  + req.getAttribute("pathInfo")
        //        + " => " + req.getAttribute("target"));

    } // doRequest

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doRequest("GET", req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doRequest("POST", req, res);
    }

} // EndPointServlet

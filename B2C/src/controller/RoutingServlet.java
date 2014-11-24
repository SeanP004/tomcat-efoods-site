package controller;

import java.io.IOException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet implementation class RoutingServlet
 * Represents an intermediate routing servlet.
 *
 * At initialization the servlet retrieves a list
 * of url patterns and servlet name mappings from
 * the web.xml initialization parameters that it
 * will use to route incoming requests.
 *
 * It then stores the routing table (rules) in an
 * attribute in the context scope, namespaced by the
 * prefix 'routes://' where each servlet has its own
 * routing rules.
 */
public abstract class RoutingServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = getServletContext();
        Map<String, String> routes = new LinkedHashMap<String, String>();
        for (String path : config.getInitParameter("routes").split("[ \n]+")) {
            String[] rule = path.split("="); // 0: servletName, 1: urlPatterns
            for (String uri : rule[1].split(",")) {
                routes.put(uri, rule[0]);
                //System.out.println("Rule: " + uri + " => " + rule[0]);
            }
        }
        context.setAttribute("routes://" + getServletName(), routes);
    }

    protected void doRequest(String method, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // get attributes
        ServletContext context = getServletContext();
        String pathInfo = (String)req.getAttribute("pathInfo");

        // reset attributes
        req.setAttribute("pathInfo", null);
        req.setAttribute("target", null);

        // update attributes
        @SuppressWarnings("unchecked")
        Map<String, String> routes = ((Map<String, String>)
                context.getAttribute("routes://" + getServletName()));
        if (pathInfo == null || pathInfo.isEmpty()) {
            pathInfo = "/";
        }
        if (pathInfo.charAt(0) != '/') {
            pathInfo = "/" + pathInfo;
        }
        for (String uri : routes.keySet()) {
            //System.out.println(uri + " ;" + pathInfo);
            if (pathInfo.matches("^" + uri.replace("*", ".*") + "$")) {
                if (uri.endsWith("/*")) {
                    req.setAttribute("pathInfo", pathInfo.substring(uri.replace("/*", "").length()));
                } else {
                    req.setAttribute("pathInfo", pathInfo.substring(uri.length()));
                }
                req.setAttribute("target", routes.get(uri));
                break;
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

} // RoutingServlet

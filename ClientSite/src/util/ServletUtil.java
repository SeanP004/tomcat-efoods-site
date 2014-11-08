package util;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Common Servlet Utilities
 *
 * This class is a collection of methods shared in common between all servlet in
 * the application.
 *
 * @version 0.1
 */

public class ServletUtil {

    private ServletUtil() {
    } // no constructor

    /**
     * Returns the value for the specified parameter, given the servlet context,
     * the session object, request scope. Sets the request attribute and session
     * attribute to the value.
     *
     * @param param     the specified name of the parameter
     * @param ctx       the servlet context
     * @param sess      the session object (scope)
     * @param req       the request scope
     * @return          the value of the parameter
     */
    public static String getParameter(String param, ServletContext ctx,
            HttpSession sess, ServletRequest req) {
        String value = req.getParameter(param);
        if (value == null) {
            value = (String) sess.getAttribute(param);
        }
        if (value == null) {
            value = ctx.getInitParameter(param);
        }
        req.setAttribute(param, value);
        sess.setAttribute(param, value);
        return value;
    }

} // ServletUtil

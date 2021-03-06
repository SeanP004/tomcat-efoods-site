package controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.pricing.*;
import model.cart.*;
import model.catalog.*;
import model.checkout.*;

/**
 * Servlet implementation class Main
 */
public class Main extends RoutingServlet implements Filter {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = getServletContext();
        context.setAttribute("catalog", Catalog.getCatalog());
        context.setAttribute("clerk", OrdersClerk.getClerk(
            new File(context.getRealPath(context.getInitParameter("userData"))),
            new File(context.getRealPath(context.getInitParameter("ordersXsd"))),
            new File(context.getRealPath(context.getInitParameter("ordersXslt"))),
            context.getContextPath() + context.getInitParameter("ordersXsltView"),
            context.getContextPath() + context.getInitParameter("ordersPrefix"),
            context.getInitParameter("userData")
        ));
        context.setAttribute("pm", PriceManager
                .getPriceManager(new PricingRules(
                        context.getInitParameter("shippingCost"),
                        context.getInitParameter("shippingWaiverCost"),
                        context.getInitParameter("taxRate")),
                        configPriceFilters(context)));
        context.setAttribute("secret",       context.getInitParameter("secret"));
        context.setAttribute("authUri",      context.getInitParameter("authUri"));
        context.setAttribute("authCallback", context.getInitParameter("authCallback"));
    }

    @SuppressWarnings("unchecked")
    private List<PriceFilter> configPriceFilters(ServletContext sc) {
        List<PriceFilter> filters = new ArrayList<PriceFilter>();
        Map<String, PriceFilter> lookup  = (Map<String, PriceFilter>)sc.getAttribute("priceFilters");
        if (lookup == null) {
            sc.setAttribute("priceFilters", lookup = new HashMap<String, PriceFilter>());}
        for (String key : lookup.keySet()) {
            filters.add(lookup.get(key));
        }
        return filters;
    }

    @Override
    protected void doRequest(String method, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        super.doRequest(method, req, res);

        ServletContext sc    = getServletContext();
        HttpSession sess     = req.getSession();
        Cart        cart     = (Cart)sess.getAttribute("cart");
        String      target   = (String)req.getAttribute("target");
        String      host     = (req.isSecure() ? "https://" : "http://") + req.getServerName() + ":" + req.getServerPort();

        req.setAttribute("host", host);

        if (cart == null) {
            sess.setAttribute("cart", cart = new Cart());}
        if (res.isCommitted()) {
            return;
        } else if (target == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            sc.getNamedDispatcher(target).forward(req, res);
        }
    }

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

    // Filter Methods: Main filter
    // Prevent forwarded calls, assets,
    // and WEB-INF urls from being
    // routed via the Servlets.

    public void init(FilterConfig fConfig) throws ServletException {
        String[] ignores = fConfig.getInitParameter("ignores").split("[ \n]+");
        fConfig.getServletContext().setAttribute("ignores", ignores);
        //for (String ignore : ignores) {
        //    System.out.println("ignore: " + ignore);
        //}
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = ((HttpServletRequest)request);
        String uri = req.getRequestURI().substring(req.getContextPath().length());
        ServletContext sc = request.getServletContext();
        //System.out.println("======================================");
        for (String ignore : (String[])sc.getAttribute("ignores")) {
            if (uri.startsWith(ignore)) {
                //System.out.println("Filtering: " + uri);
                chain.doFilter(request, response);
                return;
            }
        }
        //System.out.println("Dispatch: " + uri);
        req.setAttribute("pathInfo", uri);
        sc.getNamedDispatcher("Main").forward(request, response);
    }

} // Main

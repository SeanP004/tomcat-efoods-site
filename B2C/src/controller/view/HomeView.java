package controller.view;

import controller.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.account.*;
import model.cart.*;
import model.catalog.*;

/**
 * Servlet implementation class HomeView page.
 */
// @WebServlet("/view")
public class HomeView extends EndPointServlet {

    @Override
    protected void doRequest(String method, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        super.doRequest(method, req, res);

        ServletContext sc       = getServletContext();
        HttpSession    sess     = req.getSession();
        String         target   = (String)req.getAttribute("target");
        Catalog        catalog  = (Catalog)sc.getAttribute("catalog");
        Cart           cart     = (Cart)sess.getAttribute("cart");
        Account        account  = (Account)sess.getAttribute("account");

        if (catalog == null) {
            sc.setAttribute("catalog", catalog = Catalog.getCatalog());}
        if (cart == null) {
            sess.setAttribute("cart", cart = new Cart());}
        if (account == null) {
            sess.setAttribute("account", account = new Account());}

        try {
            List<Category> categories = catalog.getCategories(null, null, null, null);
            Map<String, List<Item>> itemsets = new HashMap<String, List<Item>>();

            // FIXME: Remove this code
            {
                List<Item> items = catalog.getItems(null, null, null, null, null, null, null);
                List<Item> popular = new ArrayList<Item>();
                for (int i = 0; i < 4; i += 1) {
                    popular.add(items.get((int)(Math.random() * items.size() - 1)));
                }
                itemsets.put("Popular", popular);
            }

            for (Category category : categories) {
                itemsets.put(category.getName(), catalog.getItems(null, null, "" + category.getId(), null, "4", null, null));
            }
            req.setAttribute("orders", ItemFilter.sorts);
            req.setAttribute("items",  itemsets);
            req.setAttribute("categories", categories);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }

        req.getRequestDispatcher(target).forward(req, res);
    } // doRequest

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doRequest("GET", req, res);
    }

} // CartAPI

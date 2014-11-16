package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import model.catalog.*;
import model.common.*;

/**
 * Servlet implementation class CartAPI
 * Cart API Endpoint.
 */
@WebServlet("/api/catalog")
public class CatalogAPI extends HttpServlet {

    private static final String
        JSP_FILE = "/WEB-INF/xmlres/APIResponse.jspx";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        ServletContext sc      = getServletContext();
        String         type    = req.getParameter("type");
        Catalog        catalog = (Catalog)sc.getAttribute("catalog");

        if (catalog == null) {
            sc.setAttribute("catalog", catalog = Catalog.getCatalog()); }

        try {
            if (type != null) {
                switch (type) {
                    case "itemlist":
                        ItemList items = catalog.getItemList(
                                req.getParameter("orderBy"),
                                req.getParameter("searchTerm"),
                                req.getParameter("category"),
                                req.getParameter("offset"),
                                req.getParameter("fetch"),
                                req.getParameter("minPrice"),
                                req.getParameter("maxPrice"));
                        req.setAttribute("data", XMLUtil.<ItemList>generate(new StringWriter(), items).toString());
                        break;
                    case "item":
                        Item item = catalog.getItem(req.getParameter("number"));
                        req.setAttribute("data", XMLUtil.<Item>generate(new StringWriter(), item).toString());
                        break;
                    case "catlist":
                        CategoryList categories = catalog.getCategoryList(
                                req.getParameter("orderBy"),
                                req.getParameter("searchTerm"),
                                req.getParameter("offset"),
                                req.getParameter("fetch"));
                        req.setAttribute("data", XMLUtil.<CategoryList>generate(new StringWriter(), categories).toString());
                        break;
                    case "category":
                        Category cat = catalog.getCategory(
                                req.getParameter("id"));
                        req.setAttribute("data", XMLUtil.<Category>generate(new StringWriter(), cat).toString());
                        break;
                    default:
                        throw new RuntimeException("Bad action");
                }
            } else {
                ItemList items = catalog.getItemList(
                        req.getParameter("orderBy"),
                        req.getParameter("searchTerm"),
                        req.getParameter("category"),
                        req.getParameter("offset"),
                        req.getParameter("fetch"),
                        req.getParameter("minPrice"),
                        req.getParameter("maxPrice"));
                req.setAttribute("data", XMLUtil.<ItemList>generate(new StringWriter(), items).toString());
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }

        req.getRequestDispatcher(JSP_FILE).forward(req, res);
    } // doGet

} // CartAPI

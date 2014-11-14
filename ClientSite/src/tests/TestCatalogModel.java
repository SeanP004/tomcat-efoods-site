package tests;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import model.catalog.*;

@WebServlet("/tests/catalog/model")
public class TestCatalogModel extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext sc = getServletContext();
        sc.setAttribute("model", new Catalog());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
        ServletContext sc = getServletContext();
        Catalog catalog = (Catalog)(sc.getAttribute("model"));
        String type = req.getParameter("type");

        switch (type) {
            case "itemlist":
            	catalog.getItems(
        			req.getParameter("orderBy"),
        			req.getParameter("searchTerm"),
        			req.getParameter("category"),
        			req.getParameter("offset"),
        			req.getParameter("fetch"),
        			req.getParameter("minPrice"),
        			req.getParameter("maxPrice"));
                break;
            case "item":
                catalog.getItem(
                    req.getParameter("number"));
                break;
            case "catlist":
                catalog.getItemCategories(
                	req.getParameter("orderBy"),
            		req.getParameter("searchTerm"),
            		req.getParameter("offset"),
            		req.getParameter("fetch"));
                break;
            case "category":
                catalog.getItemCategory(
                    req.getParameter("id"));
                break;
        }



    }

}

package tests;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import model.catalog.*;

@WebServlet("/tests/catalog")
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
        PrintWriter out = res.getWriter();

        res.setContentType("text/plain");

        if (type == null) {
        	return;
        }
        switch (type) {
            case "itemlist":
                List<Item> items = catalog.getItems(
                    req.getParameter("orderBy"),
                    req.getParameter("searchTerm"),
                    req.getParameter("category"),
                    req.getParameter("offset"),
                    req.getParameter("fetch"),
                    req.getParameter("minPrice"),
                    req.getParameter("maxPrice"));
                for (Item item : items) {
                    out.printf("%s|%s|%f|%d|%d|%d|%d|%d|%f|%s\n",
                        item.getNumber(),
                        item.getName(),
                        item.getPrice(),
                        item.getQty(),
                        item.getOnOrder(),
                        item.getReOrder(),
                        item.getCatId(),
                        item.getSupId(),
                        item.getCostPrice(),
                        item.getUnit());
                }
                break;
            case "item":
                Item item = catalog.getItem(
                    req.getParameter("number"));
                out.printf("%s|%s|%f|%d|%d|%d|%d|%d|%f|%s\n",
                    item.getNumber(),
                    item.getName(),
                    item.getPrice(),
                    item.getQty(),
                    item.getOnOrder(),
                    item.getReOrder(),
                    item.getCatId(),
                    item.getSupId(),
                    item.getCostPrice(),
                    item.getUnit());
                break;
            case "catlist":
                List<ItemCategory> categories = catalog.getItemCategories(
                    req.getParameter("orderBy"),
                    req.getParameter("searchTerm"),
                    req.getParameter("offset"),
                    req.getParameter("fetch"));
                for (ItemCategory cat : categories) {
                    out.printf("%d|%s|%s\n",
                        cat.getId(),
                        cat.getName(),
                        cat.getDescription());
                    //cat.getPicture()
                }
                break;
            case "category":
                ItemCategory cat = catalog.getItemCategory(
                    req.getParameter("id"));
                out.printf("%d|%s|%s\n",
                    cat.getId(),
                    cat.getName(),
                    cat.getDescription());
                //cat.getPicture()
                break;
        } // switch

    } // doGet

} // TestCatalogModel

package tests;

import java.io.*;
import java.util.*;
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
            case "item":
            case "catlist":
            case "category":
        }



    }

}

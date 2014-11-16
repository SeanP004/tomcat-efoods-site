package controller;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.pricing.*;
import model.catalog.*;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Front/*")
public class Main extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext sc = getServletContext();
        sc.setAttribute("Catalog", Catalog.getCatalog());
        sc.setAttribute("PriceManager", PriceManager
                .getPriceManager(new PricingRules(sc
                        .getInitParameter("shippingCost"), sc
                        .getInitParameter("shippingWaverCost"), sc
                        .getInitParameter("taxRate"))));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        ServletContext sc = getServletContext();
        String pathInfo = req.getPathInfo();
        if (pathInfo != null) {
            switch (pathInfo) {
                case "/api/catalog":
                    sc.getNamedDispatcher("CatalogAPI").forward(req, res);
                    break;
                case "/api/cart":
                    sc.getNamedDispatcher("CartAPI").forward(req, res);
                    break;
            }
        } else {
            sc.getRequestDispatcher("/WEB-INF/pages/Catalog.jspx").forward(req, res);
        }
    }

} // Main

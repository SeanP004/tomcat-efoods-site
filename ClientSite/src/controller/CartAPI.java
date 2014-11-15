package controller;

import java.io.*;
import javax.management.RuntimeErrorException;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import model.cart.*;
import model.catalog.*;

/**
 * Servlet implementation class TestCart
 */
@WebServlet("/api/cart")
public class CartAPI extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext sc = getServletContext();
        sc.setAttribute("xmlgen", new CartXML());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        ServletContext sc      = getServletContext();
        HttpSession    sess    = req.getSession();
        PrintWriter    out     = res.getWriter();        
        String         action  = req.getParameter("action");
        String         number  = req.getParameter("number");
        CartXML        xml     = (CartXML)sc.getAttribute("xmlgen");
        Catalog        catalog = (Catalog)sc.getAttribute("catalog");
        Cart           cart    = (Cart)sess.getAttribute("cart");

        if (catalog == null) {
            sc.setAttribute("catalog", catalog = new Catalog());
        }
        if (cart == null) {
            sess.setAttribute("cart", cart = new Cart(catalog));
        }

        out.println("<?xml version='1.0' encoding='UTF-8'?>");
        out.println("<response>");

        if (req.getQueryString() != null) {
            out.println("<request>");
            for (String param : req.getParameterMap().keySet()) {
                out.printf("<param name='%s' value='%s' />", param, req.getParameter(param));
            }
            out.println("</request>");
        }

        out.println("<data>");
        
        if (action != null) {
            try {
                switch (action) {
                    case "add":
                        cart.add(number);
                        out.println("<status>Successfully Added</status>");
                        break;
                    case "remove":
                        cart.remove(number);
                        out.println("<status>Successfully Removed</status>");
                        break;
                    case "list":
                        xml.generate(out, cart);                    
                        break;
                    default:
                        throw new RuntimeException("bad action");
                }
            } catch (Exception e) {
                out.printf("<error>%s</error>", e.getMessage());
            }
        } else {
            out.printf("<error>%s</error>", "no action");
        }

        out.println("</data>");
        out.println("</response>");       
    } // doGet

} // CartAPI

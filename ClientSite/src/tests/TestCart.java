package tests;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import model.cart.*;
import model.catalog.*;

/**
 * Servlet implementation class TestCart
 */
@WebServlet("/tests/cart")
public class TestCart extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext sc = getServletContext();
        sc.setAttribute("cart", new Cart(new Catalog()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
        ServletContext sc = getServletContext();
        Cart cart = (Cart)(sc.getAttribute("cart"));
        String action = req.getParameter("action");
        String number = req.getParameter("number");
        PrintWriter out = res.getWriter();
        
        res.setContentType("text/plain");
        
        if (action != null) {
            switch (action) {
                case "add": cart.add(number); break;
                case "remove": cart.remove(number); break;
                case "list": break;
            }
        }

        for (CartElement element : cart.getElements()) {
            Item item = element.getItem();
            out.printf("%s|%s|%f|%d|%d|%d|%d|%d|%f|%s Quantity: %d\n",
                    item.getNumber(),
                    item.getName(),
                    item.getPrice(),
                    item.getQty(),
                    item.getOnOrder(),
                    item.getReOrder(),
                    item.getCatId(),
                    item.getSupId(),
                    item.getCostPrice(),
                    item.getUnit(),
                    element.getQuantity());
        }
        out.printf("Total Items: %d\n", cart.getNumberOfItems());
    } // doGet

} // TestCart

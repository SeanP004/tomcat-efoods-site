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
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        ServletContext sc      = getServletContext();
        PrintWriter    out     = res.getWriter();
        HttpSession    sess    = req.getSession();
        String         action  = req.getParameter("action");
        String         number  = req.getParameter("number");
        Catalog        catalog = (Catalog)sc.getAttribute("catalog");
        Cart           cart    = (Cart)sess.getAttribute("cart");

        if (catalog == null) {
            sc.setAttribute("catalog", catalog = Catalog.getCatalog()); }
        if (cart == null) {
            sess.setAttribute("cart", cart = new Cart()); }

        res.setContentType("text/plain");

        if (action != null) {
            switch (action) {
                case "add":
                    cart.add(number);
                    break;
                case "remove":
                    cart.remove(number);
                    break;
                case "list":
                    break;
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

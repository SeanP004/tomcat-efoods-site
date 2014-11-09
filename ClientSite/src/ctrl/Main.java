package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

/**
 * Servlet implementation class Main
 */
//@WebServlet("/*")
public class Main extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Servlet#init(ServletConfig)
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doRequest("GET", req, res);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doRequest("POST", req, res);
    }

    // Private
    
    /**
     * Handles HTTP request from various methods, both: GET and POST.
     * And responses as appropriate.
     * 
     * @param method    HTTP method: GET or POST
     * @param req       the HTTP request object
     * @param res       the HTTP response object
     * 
     * @throws ServletException
     * @throws IOException
     */
    private void doRequest(String method, HttpServletRequest req,
            HttpServletResponse res) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

} // Main Servlet

package listener;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

/**
 * Application Lifecycle Listener implementation class SessionCountListener
 *
 */
@WebListener
public class SessionCountListener implements HttpSessionListener {

    private static int totalActiveSessions;
    
    public static int getTotalActiveSession(){
      return totalActiveSessions;
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent event)  { 
        totalActiveSessions++;
    	HttpSession sess = event.getSession();
    	//ServletContext sc = sess.getServletContext();
    	long time = (long)System.currentTimeMillis();
    	sess.setAttribute("startCartTime", time);
    	sess.setAttribute("startCheckoutTime", time);
        System.out.println("sessionCreated - add one session into counter");
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent event)  { 
         // TODO Auto-generated method stub
    }
	
}

package listener;

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
     * 
     * called when a new session event is triggered and the current time
     * is recorded to session context.
     */
    public void sessionCreated(HttpSessionEvent event)  { 
        totalActiveSessions++;
    	HttpSession sess = event.getSession();
    	long time = (long)System.currentTimeMillis();
    	sess.setAttribute("startCartTime", time);
    	sess.setAttribute("startCheckoutTime", time);
        //System.out.println("sessionCreated");
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent event)  { 
         // TODO Auto-generated method stub
    }
	
}

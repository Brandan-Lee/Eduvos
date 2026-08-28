
package websockets;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

//Helper class to help the websocket manage user sesssions
public class NotificationSessionManager {
    
    private static final Logger logger = Logger.getLogger(NotificationSessionManager.class.getName());
    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();
    
    //Method to add a session to the notification manager
    public static void addSession(Session session) {
        sessions.add(session);
        logger.log(Level.INFO, "New session created for client: {0}", session.getId());
    }
    
    //Method to remove a session from the notification manager
    public static void removeSession(Session session) {
        sessions.remove(session);
        logger.log(Level.INFO, "Client has disconnected at {0}", session.getId());
    }
    
    //Method to broadcast notifications to all sessions that are open by the notification manager
    public static void broadCastMessage(String message) {
        for (Session s : sessions) {
            sendMessage(s, message);
        }
    }
    
    //Helper method that ensures that sessions exist and are open before trying to send the notification
    private static void sendMessage(Session session, String message) {
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Failed to send message to client at {0}", session.getId());
            }
        }
    }
}


package websockets;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("ChurchNotificationSystemServer")
public class ChurchNotificationSystemWebSocket {
    
    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();
    
    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("New session created for client " + session.getId());
    } 
    
    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("Client has disconnected at " + session.getId());
    }
    
    @OnMessage
    public void onMessage(String message, Session session) {
        broadcastMessage(message);
    }
    
    public static void broadcastMessage(String message) {
        for (Session session : sessions) {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException ex) {
                    System.err.println("There was an error sending a message to the session client with " + session.getId());
                }
            }
        }
    }
}

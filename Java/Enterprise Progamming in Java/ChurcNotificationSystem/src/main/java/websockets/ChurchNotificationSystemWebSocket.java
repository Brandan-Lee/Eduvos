package websockets;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/ChurchNotificationSystemServer")
public class ChurchNotificationSystemWebSocket {
    
    @OnOpen
    public void onOpen(Session session) {
        NotificationSessionManager.addSession(session);
    }

    @OnClose
    public void onClose(Session session) {
        NotificationSessionManager.removeSession(session);
    }

    @OnMessage
    public static void onMessage(String message, Session session) {
        NotificationSessionManager.broadCast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        NotificationSessionManager.removeSession(session);
    }
}

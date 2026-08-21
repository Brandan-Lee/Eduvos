
package com.mycompany.churchnotificationsystem.JMS;

import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSConnectionFactoryDefinition;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import websockets.ChurchNotificationSystemWebSocket;

@JMSConnectionFactoryDefinition (
        name = "java:global/jms/ChurchNotificationSystemConnectionFactory",
        interfaceName = "javax.jms.ConnectionFactory"
)
@JMSDestinationDefinition (
        name = "java:global/jms/NotificationQueue",
        interfaceName = "javax.jms.Queue",
        destinationName = "NotificationQueue"
)
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:global/jms/NotificationQueue"),
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
public class NotificationProcessorMDB implements MessageListener {
    
    private static final Logger logger = Logger.getLogger(NotificationProcessorMDB.class.getName());

    @Override
    public void onMessage(Message message) {
        //Validate the type of the message
        if (!(message instanceof TextMessage)) {
            logger.warning("The message received is not of type text message");
            return;
        }
        
        //Process the incoming message and display on the client
        try {
            TextMessage txt = (TextMessage) message;
            String notificationText = txt.getText();
            logger.info("Incoming notification is being processed: " + notificationText);
            dispatchNotification(notificationText);
        } catch (JMSException ex) {
            logger.severe("Failed to read incoming JMS notification: " + ex.getMessage());
        }
    }
    
    //Helper method that ensures that the notification can be broadcasted via the websocket
    private void dispatchNotification(String notification) {
        try {
            ChurchNotificationSystemWebSocket.broadcastMessage(notification);
        } catch (Exception ex) {
            logger.severe("There was a problem broadcasting the notification from the websocket");
        }
    }
    
}


package com.mycompany.churchnotificationsystem.JMS;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactoryDefinition;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import websockets.NotificationSessionManager;

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
    
    @Inject
    private NotificationSessionManager manager;

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
            logger.log(Level.INFO, "Incoming notification is being processed: {0}", notificationText);
            dispatchNotification(notificationText);
        } catch (JMSException ex) {
            logger.log(Level.SEVERE, "Failed to read incoming JMS notification: {0}", ex.getMessage());
        }
    }
    
    //Helper method that ensures that the notification can be broadcasted via the websocket
    private void dispatchNotification(String notification) {
        try {
            //Ensure that an instance of the manager exists
            if (manager != null) {
                manager.broadCast(notification);
                logger.log(Level.INFO, "Notification has been sent successfully: {0}", notification);
            } else {
                logger.severe("Notification session manager has not been found");
            }
        } catch (Exception ex) {
            logger.severe("There was a problem broadcasting the notification from the websocket");
        }
    }
    
}


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
        name = "java:global/jms/CurchNotificationSystemConnectionFactory",
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
        try {
            if (message instanceof TextMessage) {
                String notificationText = ((TextMessage) message).getText();
                logger.info("NotificationQueue received order asynchronously: " + notificationText);
                ChurchNotificationSystemWebSocket.broadcastMessage(notificationText);
            }
        } catch (JMSException ex) {
            logger.severe("Error processing JMS notification: " + ex.getMessage());
        }
    }
    
    
}

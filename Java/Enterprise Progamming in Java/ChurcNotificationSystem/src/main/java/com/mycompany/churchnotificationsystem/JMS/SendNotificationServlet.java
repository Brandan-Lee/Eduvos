package com.mycompany.churchnotificationsystem.JMS;

import com.mycompany.churchnotificationsystem.validation.ValidationResult;
import com.mycompany.churchnotificationsystem.validation.ValidationUtil;
import java.io.IOException;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SendNotificationServlet")
public class SendNotificationServlet extends HttpServlet {

    @Resource(lookup = "java:global/jms/NotificationQueue")
    private Queue notificationQueue;

    @Inject
    private JMSContext jms;
    
    @Inject
    private ValidationUtil validator;
    
    private static final String VIEW_PAGE = "notifications.jsp";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(VIEW_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Retrieve the notification from the client and sanitize it.
        String notification = validator.sanitizeUserInput(request.getParameter("notification"));
        
        //Validation check for notification user input using the ValidationResult helper class and validation util.
        ValidationResult result = validator.validateNotification(notification);
        
        if (!result.isValid()) {
            request.setAttribute("errorField", result.getErrorField());
            validator.forwardWithFeedback(request, response, VIEW_PAGE, "errorMessage", result.getErrorMessage());
            return;
        }
        
        //Send the notification to the notificationqueue so that the websocket can display the notification.
        jms.createProducer().send(notificationQueue, notification.trim());
            validator.forwardWithFeedback(request, response, VIEW_PAGE, "success", "Notification has been successfully sent to the system. Send another notification or return back to your home dashboard");
    }
    
}

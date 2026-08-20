package com.mycompany.churchnotificationsystem.JMS;

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

    private final ValidationUtil validator = new ValidationUtil();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("notification.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String notification = request.getParameter("notification");

        if (validator.isEmpty(notification)) {
            request.setAttribute("errorField", "notification");
            validator.forwardWithFeedback(request, response, "notification.jsp", "errorMessage", "The notification field is empty");
            return;
        }

        jms.createProducer().send(notificationQueue, notification.trim());
        validator.forwardWithFeedback(request, response, "notification.jsp", "success", "Notification has been successfully sent to the system. Send another notification or return back to your home dashboard");
    }
}

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>City Church Notification System</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" type="text/css" href="style.css" />
    </head>
    <body>
        <!-- Top bar that will be used across the pages -->
        <div class="top-bar"></div>

        <section class="container">
            <h1 class="title">
                City Church Notification System
            </h1>
            
            <%
                com.mycompany.churchnotificationsystem.model.User user = (com.mycompany.churchnotificationsystem.model.User)session.getAttribute("currentUser");
                String role = (String) session.getAttribute("userRole");
            %>
            
            <h2 class="subtitle">
                Welcome back Church <%= role + " " + user.getUserName() %>. This is your home dashboard
            </h2>
            
            <!--Home buttons-->
            <div class="btn-group">
                <!-- Navigation button to registration page -->
                <a href="view-notification.jsp" class="btn" style="text-align: center; line-height: normal;">VIEW NOTIFICATIONS</a>
                    
                <% if (user.getRole().equals("leader")) { %>
                    <!-- Navigation button to navigation page -->
                    <a href="notification.jsp" class="btn" style="text-align: center; line-height: normal;">SEND A NEW NOTIFICATION</a>
                <% } %>
            </div>

        </section>
    </body>
</html>

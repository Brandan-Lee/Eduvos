<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.mycompany.churchnotificationsystem.model.User" %>
<%
    User user = (User) session.getAttribute("currentUser");
    String role = (String) session.getAttribute("userRole");
    
    //Ensures that  a user has logged in, else redirected to login page
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
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

        <section class="container flex-center-column">
            <h1 class="title">
                City Church Notification System
            </h1>

            <h2 class="subtitle">
                Welcome back church <%= role + " " + user.getUserName()%>. This is your home dashboard
            </h2>

            <!--Home buttons-->
            <div class="btn-group flex-center-column">
                <!-- Navigation button to registration page -->
                <a href="view-notification.jsp"
                   class="btn btn-nav"
                   >
                    VIEW NOTIFICATIONS
                </a>

                <!--Hide the Send a New Notification Button if the users role is member-->
                <% if ("leader".equalsIgnoreCase(user.getRole())) { %>
                <!-- Navigation button to navigation page -->
                <a href="notification.jsp"
                   class="btn btn-nav" 
                   >
                    SEND A NEW NOTIFICATION
                </a>
                <% }%>
            </div>
        </section>
    </body>
</html>

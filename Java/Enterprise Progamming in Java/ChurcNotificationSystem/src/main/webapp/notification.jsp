<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.mycompany.churchnotificationsystem.model.User" %>
<%
    User user = (User) session.getAttribute("currentUser");

    //Ensure that the user is logged in and that they have the correct role for this view
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    } else if (!"leader".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("home.jsp");
        return;
    }

    String error = (String) request.getAttribute("errorMessage");
    String success = (String) request.getAttribute("success");
    String errorField = (String) request.getAttribute("errorField");

    String prevNotification = request.getParameter("notification") != null ? request.getParameter("notification") : "";
    boolean isNotificationError = "notification".equals(errorField);
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
                Server-side: Send a notification to all church members.
            </h2>

            <% if (error != null) {%>
            <div class="msg-banner msg-error">
                <%= error%>
            </div>
            <% } else if (success != null) {%>
            <div class="msg-banner msg-success">
                <%= success%>
            </div>
            <% }%>

            <form class="frm-styles"
                  action="SendNotificationServlet"
                  method="POST"
                  >
                <!--Notification group-->
                <div class="frm-group flex-center-column">
                    <label for="notification"
                           class="lbl"
                           >
                        Write a notification to send to all church members.
                    </label>
                    <textarea id="notification"
                              class="input-field <%= isNotificationError ? "input-error" : ""%>"
                              name="notification"
                              rows="5"
                              placeholder="Please enter a notification to send..."
                              ><%= prevNotification%></textarea>
                </div>

                <!--Bottom buttons-->
                <div class="btn-group flex-center-column">
                    <input type="submit"
                           value="SEND NOTIFICATION"
                           class="btn"
                           />

                    <!-- Navigation button to home page -->
                    <a href="home.jsp"
                       class="btn btn-nav"
                       >
                        BACK TO HOME DASHBOARD
                    </a>
                </div>
            </form>
        </section>

        <!--On successful submission, clear the notification textarea input-->
        <% if (success != null) { %>
        <script>
            const notificationField = document.getElementById("notification");

            if (notificationField) {
                notificationField.value = "";
            }
        </script>
        <% }%>
    </body>
</html>
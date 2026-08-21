<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.mycompany.churchnotificationsystem.model.User" %>
<%
    User user = (User) session.getAttribute("currentUser");

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
                Incoming notifications from Church Leaders
            </h2>

            <div id="notification-feed" class="msg-banner msg-error">
                There are no new notifications at this time. Please try again later.
            </div>

            <div class="btn-group flex-center-column">
                <a href="<%= request.getContextPath() %>/home.jsp"
                   class="btn btn-nav"
                   >
                    BACK TO HOME DASHBOARD
                </a>
            </div>
        </section>

        <script>
            const protocol = window.location.protocol === "https:" ? "wss://" : "ws://";
            const host = window.location.host;
            const contextPath = "<%= request.getContextPath()%>";
            const webSocketURL = protocol + host + contextPath + "/ChurchNotificationSystemServer";
            let webSocket;
            let hasNotifications = false;

            function connect() {

                webSocket = new WebSocket(webSocketURL);

                webSocket.onopen = function () {
                    console.log("Connected to websocket at: " + webSocketURL);
                };

                webSocket.onmessage = function (event) {
                    const notification = event.data;
                    console.log("New notification received from the server: " + notification);

                    const notificationFeed = document.getElementById("notification-feed");

                    if (notificationFeed) {

                        if (!hasNotifications) {
                            //Clear the default banner when a message is received
                            notificationFeed.innerHTML = "";
                            notificationFeed.className = "feed-container flex-center-column";
                            hasNotifications = true;
                        }
                        
                        //Create the notification card, and add each notification to the notification feed.
                        const notificationCard = document.createElement("div");
                        notificationCard.className = "notification-card";
                        notificationCard.textContent = notification;
                        notificationFeed.prepend(notificationCard);
                    }
                };

                webSocket.onclose = function () {
                    console.log("The connection to the server has been closed");
                };
                
                websocket.onerror = function (error) {
                    console.error("An error occurred within the websocket: ", error);
                };
            }

            connect();
        </script>
    </body>
</html>

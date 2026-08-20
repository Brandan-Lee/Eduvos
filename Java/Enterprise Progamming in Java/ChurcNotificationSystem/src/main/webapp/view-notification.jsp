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
            <h2 class="subtitle">
                Incoming notifications from Church Leaders
            </h2>

            <div id="notification-feed" class="msg-banner msg-error">
                There are no new notifications at this time. Please try again later.
            </div>

            <a href="home.jsp" class="btn" style="text-align: center; line-height: normal;">BACK TO HOME DASHBOARD</a>

        </section>

        <script>
            const protocol = window.location.protocol === "https" ? "wss://" : "ws://";
            const host = window.location.host;
            const contextPath = "<%= request.getContextPath() %>";
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
                            notificationFeed.innerHTML = "";
                            notificationFeed.className = "";
                            hasNotifications = true;
                        }

                        const notificationAlert = document.createElement("div");
                        notificationAlert.className = "notification-card";
                        notificationAlert.textContent = notification;
                        notificationFeed.prepend(notificationAlert);
                    }
                };

                webSocket.onclose = function () {
                    console.log("The connection to the server has been closed");
                };
            }

            connect();
        </script>
    </body>
</html>

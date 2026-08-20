
const protocol = window.location.protocol === "https" ? "wss://" : "ws://";
const host = window.location.host;
const websocketURL = protocol + host + "/ChurchNotificationSystem/ChurchNotificationSystemServer";
let webSocket;
let hasNotifications = false;

function connect() {
    
    webSocket = new WebSocket(webSocketURL);
    
    webSocket.onopen = function() {
        console.log("Connected to websocket at: " + websocketURL);
    };
    
    webSocket.onmessage = function(event) {
        const notification = event.data;
        console.log("New notification received from the server: " + notification);
        
        const notificationFeed = document.getElementById("notification-feed");
        
        if (notificationFeed) {
            
            if (!hasNotifications) {
                notificationFeed.innerHTML = "";
                notificationFeed.className = "msg-banner";
                hasNotifications = true;
            }
            
            const notificationAlert = document.createElement("div");
            notificationAlert.className = "msg-banner";
            notificationAlert.style.marginBottom = "1em";
            notificationAlert.style.textAlign = "left";
            notificationAlert.textContent = notification;
            notificationFeed.prepend(notificationAlert);
        }
    };
    
    webSocket.onclose = function() {
        console.log("The connection to the server has been closed");
    };
};

connect();



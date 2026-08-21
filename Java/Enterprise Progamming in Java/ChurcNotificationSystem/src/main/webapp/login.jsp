<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    //Get request attributes from the server
    String error = (String) request.getAttribute("errorMessage");
    String success = (String) request.getAttribute("success");
    String errorField = (String) request.getAttribute("errorField");

    //Preserve previously entered usernames
    String usernameParameter = request.getParameter("username");
    String previousUsername = (usernameParameter != null) ? usernameParameter : "";

    //Status checks for input styling
    boolean isUsernameError = "login".equals(errorField) || "username".equals(errorField);
    boolean isPasswordError = "login".equals(errorField) || "password".equals(errorField);
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
                Login to the Notification System
            </h2>

            <!--Status Banners-->
            <% if (error != null) {%>
            <div class="msg-banner msg-error">
                <%= error%>
            </div>
            <% } else if (success != null) {%>
            <div class="msg-banner msg-success">
                <%= success%> Redirecting to home page in 2 seconds...
            </div>
            <% }%>

            <form class="frm-styles"
                  action="LoginServlet"
                  method="POST"
                  >
                <!--Username group-->
                <div class="frm-group flex-center-column">
                    <label for="username"
                           class="lbl"
                           >
                        Please enter your username
                    </label>
                    <input type="text"
                           id="username"
                           class="input-field <%= isUsernameError ? "input-error" : ""%>" 
                           name="username" 
                           placeholder="Username"
                           value="<%= previousUsername%>"
                           />
                </div>

                <!--Password group-->
                <div class="frm-group flex-center-column">
                    <label for="password"
                           class="lbl"
                           >
                        Please enter your password
                    </label>
                    <input type="password"
                           id="password"
                           class="input-field <%= isPasswordError ? "input-error" : ""%>" 
                           name="password" 
                           placeholder="Password"
                           />
                </div>

                <!--Bottom buttons-->
                <div class="btn-group flex-center-column">
                    <input type="submit" 
                           value="LOGIN" 
                           class="btn" 
                           />

                    <!-- Navigation button to registration page -->
                    <a href="register.jsp"
                       class="btn btn-nav"
                       >
                        REGISTER
                    </a>
                </div>
            </form>
        </section>
        <!--On successful login, the user will be redirected to the home page automatically-->
        <% if (success != null) { %>
        <script>
            setTimeout(function () {
                window.location.href = "home.jsp";
            }, 2000);
        </script>
        <% }%>
    </body>
</html>

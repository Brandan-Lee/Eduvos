<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    //Get request attributes from the server
    String error = (String) request.getAttribute("errorMessage");
    String success = (String) request.getAttribute("success");
    String errorField = (String) request.getAttribute("errorField");

    //Preserve the username and role
    String usernameParameter = request.getParameter("username");
    String previousUsername = (usernameParameter != null) ? usernameParameter : "";
    String selectedRole = request.getParameter("role");

    //Status checks for input styling
    boolean isUsernameError = "username".equals(errorField);
    boolean isPasswordError = "password".equals(errorField);
    boolean isRoleError = "role".equals(errorField);
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
                Register to receive your latest church notifications
            </h2>

            <!--Retrieve from the server if there was a problem with the registration process and highlight that field or if it was successful-->
            <% if (error != null) {%>
            <div class="msg-banner msg-error">
                <%= error%>
            </div>
            <% } else if (success != null) {%>
            <div class="msg-banner msg-success">
                <%= success%> Redirecting to login page in 2 seconds...
            </div>
            <% }%>

            <form class="frm-styles"
                  action="RegistrationServlet"
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

                <!--Role select as a database will not be included within this project-->
                <div class="frm-group flex-center-column">
                    <label for="user-role"
                           class="lbl"
                           >
                        Please select your role
                    </label>
                    <select id="user-role"
                            name="role"
                            class="input-field <%= isRoleError ? "input-error" : ""%>"
                            >
                        <option value=""
                                disabled
                                selected
                                <%= (selectedRole == null || selectedRole.isEmpty()) ? "selected" : ""%>
                                >
                            Select your role
                        </option>
                        <option value="leader"
                                <%= "leader".equals(selectedRole) ? "selected" : ""%>
                                >
                            Church Leader
                        </option>
                        <option value="member"
                                <%= "member".equals(selectedRole) ? "selected" : ""%>
                                >
                            Church Member
                        </option>
                    </select>
                </div>

                <!--Bottom buttons-->
                <div class="btn-group flex-center-column">
                    <input type="submit" 
                           value="REGISTER" 
                           class="btn"
                           />

                    <!-- Navigation button to login page -->
                    <a href="login.jsp"
                       class="btn btn-nav"
                       >
                        BACK TO LOGIN
                    </a>
                </div>
            </form>
        </section>
        <!--On successful registration, the user will be redirected to the login page automatically-->
        <% if (success != null) { %>
        <script>
            setTimeout(function () {
                window.location.href = "login.jsp";
            }, 2000);
        </script>
        <% }%>
    </body>
</html>

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
                Register to receive your latest church notifications
            </h2>

            <!--Retrieve from the server if there was a problem with the registration process and highlight that field or if it was successful-->
            <%
                String error = (String) request.getAttribute("errorMessage");
                String success = (String) request.getAttribute("success");
                String errorField = (String) request.getAttribute("errorField");
                String selectedRole = (String) request.getParameter("role");

                if (error != null) {
            %>

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
                <div class="frm-group">
                    <label for="username" class="lbl">
                        Please enter your username
                    </label>
                    <input type="text" 
                           class="input-field <%= "username".equals(errorField) ? "input-error" : ""%>" 
                           name="username" 
                           placeholder="Username"
                           value="<%= request.getParameter("username") != null ? request.getParameter("username") : ""%>"
                    />
                </div>

                <!--Password group-->
                <div class="frm-group">
                    <label for="password" class="lbl">
                        Please enter your password
                    </label>
                    <input type="password" 
                           class="input-field <%= "password".equals(errorField) ? "input-error" : ""%>" 
                           name="password" 
                           placeholder="Password"
                           value="<%= request.getParameter("password") != null ? request.getParameter("password") : ""%>"
                    />
                </div>

                <!--Role select as a database will not be included within this project-->
                <div class="frm-group">
                    <label for="user-role" class="lbl">
                        Please select your role
                    </label>
                    <select id="user-role"
                            name="role"
                            class="input-field <%= "role".equals(errorField) ? "input-error" : ""%>"
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
                <div class="btn-group">
                    <input type="submit" 
                           value="REGISTER" 
                           class="btn" />

                    <!-- Navigation button to login page -->
                    <a href="login.jsp" class="btn" style="text-align: center; line-height: normal;">BACK TO LOGIN</a>
                </div>
            </form>
        </section>
        <!--On successful registration, the user will be redirected to the login page automatically-->
        <% if (request.getAttribute("success") != null) { %>
            <script>
                setTimeout(function () {
                    window.location.href = "login.jsp";
                }, 2000);
            </script>
        <% }%>
    </body>
</html>

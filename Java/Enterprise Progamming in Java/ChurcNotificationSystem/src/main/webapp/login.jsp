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
                Login to the Notification System
            </h2>
            
            <!--Retrieve from the server if there was a problem with the login process and highlight that field or if it was successful-->
            <%
                String error = (String) request.getAttribute("errorMessage");
                String success = (String) request.getAttribute("success");
                String errorField = (String) request.getAttribute("errorField");

                if (error != null) {
            %>

            <div class="msg-banner msg-error">
                <%= error%>
            </div>
            <% } else if (success != null) {%>
            <div class="msg-banner msg-success">
                <%= success%> Redirecting to home page in 2 seconds...
            </div>
            <% }%>
            
            <%-- TEMPORARY DEBUG LINE --%>
<p style="color: red; text-align: center;">Debug errorField: '[<%= errorField %>]'</p>
            <form class="frm-styles"
                  action="LoginServlet"
                  method="POST"
            >
                <!--Username group-->
                <div class="frm-group">
                    <label for="username" class="lbl">
                        Please enter your username
                    </label>
                    <input type="text" 
                           class="input-field <%= ("login".equals(errorField) || "username".equals(errorField)) ? "input-error" : ""%>" 
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
                           class="input-field <%= ("login".equals(errorField) || "password".equals(errorField)) ? "input-error" : ""%>" 
                           name="password" 
                           placeholder="Password"
                           value="<%= request.getParameter("password") != null ? request.getParameter("password") : ""%>"
                    />
                </div>
                
                <!--Bottom buttons-->
                <div class="btn-group">
                    <input type="submit" 
                           value="LOGIN" 
                           class="btn" />
                    
                    <!-- Navigation button to registration page -->
                    <a href="register.jsp" class="btn" style="text-align: center; line-height: normal;">REGISTER</a>
                </div>
            </form>
        </section>
        <!--On successful login, the user will be redirected to the home page automatically-->
        <% if (request.getAttribute("success") != null) { %>
            <script>
                setTimeout(function () {
                    window.location.href = "home.jsp";
                }, 2000);
            </script>
        <% }%>
    </body>
</html>

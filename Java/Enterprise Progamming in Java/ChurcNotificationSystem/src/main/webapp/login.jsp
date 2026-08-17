<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>City Church Notification System</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" type="text/css" href="style.css" />
    <head>
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

            <form class="frm-styles">
                <!--Username group-->
                <div class="frm-group">
                    <label for="username" class="lbl">
                        Please enter your username
                    </label>
                    <input type="text" 
                           class="input-field" 
                           name="username" 
                           placeholder="Username" 
                    />
                </div>
                
                <!--Password group-->
                <div class="frm-group">
                    <label for="password" class="lbl">
                        Please enter your password
                    </label>
                    <input type="password" 
                           class="input-field" 
                           name="password" 
                           placeholder="Password" 
                    />
                </div>
                
                <!--Bottom buttons-->
                <div class="btn-group">
                    <input type="submit" 
                           value="Login" 
                           class="btn" />
                    
                    <!-- Navigation button to registration page -->
                    <a href="registration.jsp" class="btn" style="text-align: center; line-height: normal;">Register</a>
                </div>
            <form>
        </section>
    </body>
</html>

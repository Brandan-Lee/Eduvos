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
                Register to receive your latest church notifications
            </h2>

            <form class="frm-styles" action="RegistrationServlet" method="POST">
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
                
                <!--Role select as a database will not be included within this project-->
                <div class="frm-group">
                    <label for="user-role" class="lbl">
                        Please select your role
                    </label>
                    <select id="user-role" name="role" class="input-field">
                        <option value="" disabled selected>
                            Select your role
                        </option>
                        <option value="leader">
                            Church Leader
                        </option>
                        <option value="member">
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
            <form>
        </section>
    </body>
</html>

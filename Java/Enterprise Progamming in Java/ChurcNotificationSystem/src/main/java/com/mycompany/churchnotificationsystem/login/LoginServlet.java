package com.mycompany.churchnotificationsystem.login;

import com.mycompany.churchnotificationsystem.datastore.UserDataStore;
import com.mycompany.churchnotificationsystem.validation.ValidationUtil;
import com.mycompany.churchnotificationsystem.model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private final ValidationUtil validator = new ValidationUtil();
    private final UserDataStore data = new UserDataStore();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Retrieve the username and password from the cient and sanitise it
        String username = validator.sanitizeUserInput(request.getParameter("username"));
        String password = validator.sanitizeUserInput(request.getParameter("password"));

        //Validation checks
        if (validator.isEmpty(username)) {
            request.setAttribute("errorField", "username");
            validator.forwardWithFeedback(request, response, "login.jsp", "errorMessage", "The username field is empty");
            return;
        }

        if (validator.isEmpty(password)) {
            request.setAttribute("errorField", "password");
            validator.forwardWithFeedback(request, response, "login.jsp", "errorMessage", "The password field is empty");
            return;
        }

        //Check to see if the user exists
        if (!data.userNameExists(username)) {
            request.setAttribute("errorField", "username");
            validator.forwardWithFeedback(request, response, "login.jsp", "errorMessage", "User " + username + " does not exist. Please register to the system");
            return;
        }
        
        //Check to see if the password is correct
        User user = data.findUserByUserName(username);
        
        if (user == null || !user.getPassword().equals(password)) {
            request.setAttribute("errorField", "password");
            validator.forwardWithFeedback(request, response, "login.jsp", "errorMessage", "This password is incorrect. Please check your credentials");
            return;
        }
        
        //User does exist, password is correct. User can log in to the system
        HttpSession session = request.getSession();
        session.setAttribute("currentUser", user);
        session.setAttribute("userRole", user.getRole());
        validator.forwardWithFeedback(request, response, "login.jsp", "success", "User " + user.getUserName() + " has been successfully verified.");
    }

}

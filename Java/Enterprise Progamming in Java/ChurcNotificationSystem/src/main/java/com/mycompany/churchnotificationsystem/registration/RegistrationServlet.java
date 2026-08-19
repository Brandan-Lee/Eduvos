package com.mycompany.churchnotificationsystem.registration;

import com.mycompany.churchnotificationsystem.datastore.UserDataStore;
import com.mycompany.churchnotificationsystem.validation.ValidationUtil;
import com.mycompany.churchnotificationsystem.model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {

    private final ValidationUtil validator = new ValidationUtil();
    private final UserDataStore data = new UserDataStore();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Retrieve the username, password and role from the client and sanitize it
        String userName = validator.sanitizeUserInput(request.getParameter("username"));
        String password = validator.sanitizeUserInput(request.getParameter("password"));
        String role = validator.sanitizeUserInput(request.getParameter("role"));

        //Validation checks
        if (validator.isEmpty(userName)) {
            request.setAttribute("errorField", "username");
            validator.forwardWithFeedback(request, response, "register.jsp", "errorMessage", "The username field is empty");
            return;
        }

        if (validator.isEmpty(password)) {
            request.setAttribute("errorField", "password");
            validator.forwardWithFeedback(request, response, "register.jsp", "errorMessage", "The password field is empty");
            return;
        }

        if (validator.isEmpty(role) || role.equalsIgnoreCase("Select a role")) {
            request.setAttribute("errorField", "role");
            validator.forwardWithFeedback(request, response, "register.jsp", "errorMessage", "Please select a role");
            return;
        }

        //Check to see if the user has registered to the notification system already
        if (data.userNameExists(userName)) {
            request.setAttribute("errorField", "username");
            validator.forwardWithFeedback(request, response, "register.jsp", "errorMessage", "User " + userName + " already exists on this system");
            return;
        }

        //Store the user and send them a message that regisration was successfull
        User user = new User(userName, password, role);
        data.saveUser(user);
        validator.forwardWithFeedback(request, response, "register.jsp", "success", "User " + userName + " has been successfully registered on the system.");

    }

}

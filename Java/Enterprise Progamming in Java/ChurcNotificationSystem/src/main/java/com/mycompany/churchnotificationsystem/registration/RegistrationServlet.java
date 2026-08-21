package com.mycompany.churchnotificationsystem.registration;

import com.mycompany.churchnotificationsystem.datastore.UserDataStore;
import com.mycompany.churchnotificationsystem.validation.ValidationUtil;
import com.mycompany.churchnotificationsystem.model.User;
import com.mycompany.churchnotificationsystem.validation.ValidationResult;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    
    @Inject
    private ValidationUtil validator;
    
    @Inject
    private UserDataStore data;
    
    private static final String VIEW_PAGE = "register.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(VIEW_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Retrieve the username, password and role from the client and sanitize it
        String userName = validator.sanitizeUserInput(request.getParameter("username"));
        String password = validator.sanitizeUserInput(request.getParameter("password"));
        String role = validator.sanitizeUserInput(request.getParameter("role"));
        
        //Validation checks for username, password and role user input using the ValidationResult helper class and validation util. Also check if the user already exists on the system
        ValidationResult result = validator.validateRegistration(userName, password, role);
        
        if (!result.isValid()) {
            request.setAttribute("errorField", result.getErrorField());
            validator.forwardWithFeedback(request, response, VIEW_PAGE, "errorMessage", result.getErrorMessage());
            return;
        }

        //Store the user and send them a message that regisration was successfull
        data.saveUser(new User(userName, password, role));
        validator.forwardWithFeedback(request, response, VIEW_PAGE, "success", "User " + userName + " has been successfully registered on the system.");

    }

}

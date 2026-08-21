
package com.mycompany.churchnotificationsystem.validation;

import com.mycompany.churchnotificationsystem.datastore.UserDataStore;
import com.mycompany.churchnotificationsystem.model.User;
import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ApplicationScoped
public class ValidationUtil {
    
    @Inject
    private UserDataStore data;
    
    public String sanitizeUserInput(String input) {
        if (input != null) {
            return input.trim();
        }
        
        return "";
    }
    
    public boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }
    
    //Helper method to ensure that the user has entered their credentials into client input fields
    private ValidationResult validateCredentialsPresence(String username, String password) {
        if (isEmpty(username)) {
            return ValidationResult.fail("username", "The username field is empty");
        }
        
        if (isEmpty(password)) {
            return ValidationResult.fail("password", "The password field is empty");
        }
        
        return ValidationResult.success();
    }
    
    public ValidationResult validateRegistration(String username, String password, String role) {
        //Ensure that the users credential data is present
        ValidationResult presenceCheck = validateCredentialsPresence(username, password);
        
        if (!presenceCheck.isValid()) {
            return presenceCheck;
        }
        
        //Ensure that the user has selected a role
        if (isEmpty(role) || "Select a role".equalsIgnoreCase(role)) {
            return ValidationResult.fail("role", "Please select a role");
        }
        
        //Check the system if the user has registered on the system or not
        if (data.userNameExists(username)) {
            return ValidationResult.fail("username", "User " + username + " already exists on this system");
        }
         
        return ValidationResult.success();
    }
    
    public ValidationResult validateLogin(String username, String password) {
        //Ensure that the users credential data is present
        ValidationResult presenceCheck = validateCredentialsPresence(username, password);
        
        if (!presenceCheck.isValid()) {
            return presenceCheck;
        }
        
        //Check to see if the user exists on the system, and validate the input password to the users password stored on the system.
        User user = data.findUserByUserName(username);
        
        if (user == null || !user.getPassword().equals(password)) {
            return ValidationResult.fail("login", "Invalid username or password. Please check your credentials and try again");
        }
        
        return ValidationResult.success();
    }
    
    public ValidationResult validateNotification(String notification) {
        //Ensure that the user entered texts within the notification input field
        if (isEmpty(notification)) {
            return ValidationResult.fail("notification", "The notification field is empty");
        }
        
        return ValidationResult.success();
    }
    
    public void forwardWithFeedback(HttpServletRequest request, HttpServletResponse response, String viewName, String attributeName, String message) throws ServletException, IOException {
        request.setAttribute(attributeName, message);
        request.getRequestDispatcher(viewName).forward(request, response);
    }
}

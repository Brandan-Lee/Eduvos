
package com.mycompany.churchnotificationsystem.validation;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidationUtil {
    
    public String sanitizeUserInput(String input) {
        if (input != null) {
            return input.trim();
        }
        
        return "";
    }
    
    public boolean isEmpty(String input) {
        return input.trim().isEmpty() || input == null;
    }
    
    public void forwardWithFeedback(HttpServletRequest request, HttpServletResponse response, String viewName, String attributeName, String message) throws ServletException, IOException {
        request.setAttribute(attributeName, message);
        request.getRequestDispatcher(viewName).forward(request, response);
    }
}

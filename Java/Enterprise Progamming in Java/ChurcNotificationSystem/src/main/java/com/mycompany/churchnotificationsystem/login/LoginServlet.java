package com.mycompany.churchnotificationsystem.login;

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
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Inject
    private ValidationUtil validator;

    @Inject
    private UserDataStore data;

    private static final String VIEW_PAGE = "login.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(VIEW_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Retrieve the username and password from the cient and sanitise it
        String username = validator.sanitizeUserInput(request.getParameter("username"));
        String password = validator.sanitizeUserInput(request.getParameter("password"));

        //Validation checks for username and password input using the ValidationResult helper class and validation util. Also check if the user already exists on the system and verify their password
        ValidationResult result = validator.validateLogin(username, password);

        if (!result.isValid()) {
            request.setAttribute("errorField", result.getErrorField());
            validator.forwardWithFeedback(request, response, VIEW_PAGE, "errorMessage", result.getErrorMessage());
            return;
        }

        //User has been authenticated. Check to see if the user has a pre-existing session and terminate it. Create a new one and set attributes for the new session.
        HttpSession oldSession = request.getSession(false);

        if (oldSession != null) {
            oldSession.invalidate();
        }

        HttpSession newSession = request.getSession(true);
        User user = data.findUserByUserName(username);
        newSession.setAttribute("currentUser", user);
        newSession.setAttribute("userRole", user.getRole());

        validator.forwardWithFeedback(request, response, VIEW_PAGE, "success", "User " + user.getUserName() + " has been successfully verified.");
    }

}

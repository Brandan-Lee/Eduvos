package com.example.tripbuddy.back_end.bll;

import android.database.Cursor;

import com.example.tripbuddy.back_end.dal.dao.UserDAO;
import com.example.tripbuddy.back_end.dal.dto.UserDTO;
import com.example.tripbuddy.utils.ValidationUtils;

import java.util.ArrayList;

public class UserBLL {

    private UserDAO userDAO;
    private ValidationUtils validation;

    public UserBLL(UserDAO userDAO) {
        this.userDAO = userDAO;
        this.validation = new ValidationUtils();
    }

    public long registerUser(UserDTO userDTO) {

        return userDAO.registerUser(userDTO);

    }

    public ArrayList<UserDTO> getAllUsers() {

        ArrayList<UserDTO> users = new ArrayList<>();
        Cursor cursor = userDAO.getAllUsers();

        if (cursor != null) {

            while (cursor.moveToNext()) {
                users.add(new UserDTO(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getString(5)
                ));
            }

            cursor.close();

        }

        return users;

    }

    public boolean validateAndRegisterUser(String email, String name, String surname, String age, String password, String confirmPassword) {

        //Validation
        if (!validation.validateEmail(email)) {
            return false;
        }

        if (!validation.validateName(name)) {
            return false;
        }

        if (!validation.validateSurname(surname)) {
            return false;
        }

        int ageNum;
        if (age.isEmpty()) {
            return false;
        }

        try {
            ageNum = Integer.parseInt(age);
        } catch (NumberFormatException e) {
            return false;
        }

        if (!validation.validateAge(ageNum)) {
            return false;
        }

        if (!validation.validatePassword(password)) {
            return false;
        }

        if (!validation.validatePassword(confirmPassword)) {
            return false;
        }

        if (!validation.arePasswordsMatching(password, confirmPassword)) {
            return false;
        }

        //Register user check
        ArrayList<UserDTO> users = getAllUsers();
        for (UserDTO userObj : users) {

            if (userObj.getEmail().equals(email)) {
                return false;
            }

        }

        UserDTO user = new UserDTO(
                0,
                email,
                name,
                surname,
                ageNum,
                password
        );

        long result = registerUser(user);

        if (result == -1) {
            return false;
        }

        return true;
    }

    public UserDTO validateAndLoginUser(String email, String password) {

        UserDTO user = userDAO.getUserByEmail(email);

        if (user != null) {
            if (user.getPassword().equals(password)) {
                return user;
            }
        }

        return null;

    }

}

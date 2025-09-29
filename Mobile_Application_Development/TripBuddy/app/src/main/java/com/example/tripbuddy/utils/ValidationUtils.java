package com.example.tripbuddy.utils;

public class ValidationUtils {

    public boolean arePasswordsMatching(String password, String confirmPassword) {

        if (!password.equals(confirmPassword)) {
            return false;
        }

        return true;

    }

    public boolean validateName(String name) {

        if (name.isEmpty()) {
            return false;
        }

        return true;
    }

    public boolean validateSurname(String surname) {

        if (surname.isEmpty()) {
            return false;
        }

        return true;

    }

    public boolean validateEmail(String email) {

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        }

        return true;

    }

    public boolean validatePassword(String password) {

        if (password.isEmpty()) {
            return false;
        }

        return true;

    }

    public boolean validateAge(int age) {

        if (age <= 0) {
            return false;
        }

        return true;

    }

}

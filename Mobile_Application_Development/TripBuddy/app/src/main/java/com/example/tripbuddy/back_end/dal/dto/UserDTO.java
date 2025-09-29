package com.example.tripbuddy.back_end.dal.dto;

public class UserDTO {

    private int userId, userAge;
    private String email, password, userFirstName, userLastName;

    public UserDTO (int userId, String email, String userFirsName, String userLastName, int userAge, String password) {

        this.userId = userId;
        this.email = email;
        this.userFirstName = userFirsName;
        this.userLastName = userLastName;
        this.userAge = userAge;
        this.password = password;

    }

    public int getUserId() {
        return userId;
    }


    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

}

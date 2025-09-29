package com.example.tripbuddy.ui.login;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.tripbuddy.R;
import com.example.tripbuddy.back_end.bll.UserBLL;
import com.example.tripbuddy.back_end.dal.dao.UserDAO;
import com.example.tripbuddy.back_end.database.MyDatabaseHelper;
import com.example.tripbuddy.utils.SwitchUIUtils;
import com.example.tripbuddy.utils.ValidationUtils;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private CircleImageView userImg;
    private EditText etEmail, etName, etSurname, etAge, etPassword, etConfirmPassword;
    private UserBLL userBLL;;
    private UserDAO userDAO;
    MyDatabaseHelper databaseHelper;
    private SwitchUIUtils switchActivity;
    private ValidationUtils validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViews();
        initializeComponents();
        handleButtons();


    }

    private void findViews() {

        etEmail = findViewById(R.id.email_et);
        etName = findViewById(R.id.name_et);
        etSurname = findViewById(R.id.surname_et);
        etAge = findViewById(R.id.age_et);
        etPassword = findViewById(R.id.password_et);
        etConfirmPassword = findViewById(R.id.confirm_password_et);

    }

    private void initializeComponents() {

        databaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        userDAO = new UserDAO(db);
        userBLL = new UserBLL(userDAO);
        validation = new ValidationUtils();
        switchActivity = new SwitchUIUtils();

    }

    private void handleButtons() {

        Button registerAccountBtn = findViewById(R.id.register_account_btn);
        Button backToLoginBtn = findViewById(R.id.back_to_login_btn);

        registerAccountBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                registerAccount();
            }
        });

        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity.switchToLoginActivity(RegisterActivity.this);
            }
        });

    }

    //ToDo keep this for future use


    private void registerAccount() {

        String email = etEmail.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String surname = etSurname.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String age = etAge.getText().toString().trim();

        if (validateAllFields(email, name, surname, age, password, confirmPassword)) {
            boolean success = userBLL.validateAndRegisterUser(
                    email,
                    name,
                    surname,
                    age,
                    password,
                    confirmPassword
            );

            if (success) {
                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
                clearInputs();
                switchActivity.switchToLoginActivity(this);
            } else {
                Toast.makeText(this, "User account already exists", Toast.LENGTH_SHORT).show();
                etEmail.setError("User already exists");
            }

        }

    }

    private boolean validateAllFields(String email, String name, String surname, String age, String password, String confirmPassword) {

        boolean isValid = true;

        if (!validation.validateEmail(email)) {
            etEmail.setError("Invalid email address");
            isValid = false;
        }

        if (!validation.validateName(name)) {
            etName.setError("Name cannot be empty");
            isValid = false;
        }

        if (!validation.validateSurname(surname)) {
            etSurname.setError("Surname cannot be empty");
            isValid = false;
        }

        int ageNum;
        if (age.isEmpty()) {
            etAge.setError("Age cannot be empty");
            isValid = false;
        }

        try {
            ageNum = Integer.parseInt(age);
            if (!validation.validateAge(ageNum)) {
                etAge.setError("Age must be greater than 0");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            etAge.setError("Age must be a number");
            isValid = false;
        }

        if (!validation.validatePassword(password)) {
            etPassword.setError("Password cannot be empty");
            isValid = false;
        }

        if (!validation.validatePassword(confirmPassword)) {
            etConfirmPassword.setError("Confirm password cannot be empty");
            isValid = false;
        }

        if (!validation.arePasswordsMatching(password, confirmPassword)) {
            etPassword.setError("Passwords do not match");
            etConfirmPassword.setError("Passwords do not match");
            etPassword.setText("");
            etConfirmPassword.setText("");
            isValid = false;
        }

        return isValid;
    }

    private void clearInputs() {

        userImg.setImageResource(R.drawable.account_circle_icon);
        etEmail.setText("");
        etName.setText("");
        etSurname.setText("");
        etAge.setText("");
        etPassword.setText("");
        etConfirmPassword.setText("");

    }

}

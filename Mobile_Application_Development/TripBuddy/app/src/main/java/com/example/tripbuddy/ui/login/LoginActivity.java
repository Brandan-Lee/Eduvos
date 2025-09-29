package com.example.tripbuddy.ui.login;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.tripbuddy.R;
import com.example.tripbuddy.back_end.bll.UserBLL;
import com.example.tripbuddy.back_end.dal.dao.UserDAO;
import com.example.tripbuddy.back_end.dal.dto.UserDTO;
import com.example.tripbuddy.back_end.shared_preferences.SharedPreferencesManager;
import com.example.tripbuddy.back_end.database.MyDatabaseHelper;
import com.example.tripbuddy.utils.SwitchUIUtils;
import com.example.tripbuddy.utils.ValidationUtils;

public class LoginActivity extends AppCompatActivity {

    private TextView tvLogin, tvRememberMe;
    private ImageView userImg;
    private EditText etEmail, etPassword;
    private Button btnLogin, btnRegister, btnForgotPassword;
    private SwitchCompat switchRememberMe;
    private ValidationUtils validation;
    private SwitchUIUtils switchActivity;
    private MyDatabaseHelper databaseHelper;
    private UserDAO userDAO;
    private UserBLL userBLL;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        initializeComponents();
        loadSharedPreferences();
        handleButtons();

    }

    private void findViews() {

        tvLogin = findViewById(R.id.login_tv);
        userImg = findViewById(R.id.user_iv);
        etEmail = findViewById(R.id.email_et);
        etPassword = findViewById(R.id.password_et);
        tvRememberMe = findViewById(R.id.remember_me_tv);
        switchRememberMe = findViewById(R.id.remember_me_switch);
        btnLogin = findViewById(R.id.login_btn);
        btnRegister = findViewById(R.id.register_btn);

    }

    private void initializeComponents() {

        databaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        userDAO = new UserDAO(db);
        userBLL = new UserBLL(userDAO);
        validation = new ValidationUtils();
        switchActivity = new SwitchUIUtils();
        sharedPreferencesManager = new SharedPreferencesManager(this);

    }

    private void loadSharedPreferences() {

        String email = sharedPreferencesManager.getUserEmail();
        String password = sharedPreferencesManager.getUserPassword();

        if (email != null && password != null) {
            etEmail.setText(email);
            etPassword.setText(password);
            switchRememberMe.setChecked(true);
        }

    }

    private void handleButtons() {

        SwitchUIUtils switchActivity = new SwitchUIUtils();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switchActivity.switchToRegisterActivity(LoginActivity.this);
                clearInputs();
            }
        });

    }

    private void handleLogin() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (validateAllFields(email, password)) {

            UserDTO loggedInUser = userBLL.validateAndLoginUser(email, password);

            if (loggedInUser != null) {

                if (switchRememberMe.isChecked()) {

                    sharedPreferencesManager.saveUserData(
                            loggedInUser.getUserId(),
                            loggedInUser.getEmail(),
                            loggedInUser.getPassword(),
                            loggedInUser.getUserFirstName()
                    );

                }

                Toast.makeText(this, "Log in successfully", Toast.LENGTH_SHORT).show();
                clearInputs();
                switchActivity.switchToMainActivity(this);

            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                etEmail.setError("Invalid email or password");
                etPassword.setError("Invalid email or password");
            }

        }

    }

    private boolean validateAllFields(String email,String password) {

        boolean isValid = true;

        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if (!validation.validateEmail(email)) {
            etEmail.setError("Invalid email address");
            isValid = false;
        }

        if (!validation.validatePassword(password)) {
            etPassword.setError("Password cannot be empty");
            isValid = false;
        }

        return isValid;

    }

    private void clearInputs() {

        etEmail.setText("");
        etPassword.setText("");

        if (switchRememberMe.isChecked()) {
            switchRememberMe.setChecked(false);
        }

    }

}

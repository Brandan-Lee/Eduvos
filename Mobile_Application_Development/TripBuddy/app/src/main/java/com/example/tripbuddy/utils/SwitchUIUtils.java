package com.example.tripbuddy.utils;

import android.content.Context;
import android.content.Intent;

import com.example.tripbuddy.MainActivity;
import com.example.tripbuddy.ui.login.LoginActivity;
import com.example.tripbuddy.ui.login.RegisterActivity;

public class SwitchUIUtils {

    public void switchToRegisterActivity(Context context) {
        switchActivity(context, RegisterActivity.class);
    }

    public void switchToLoginActivity(Context context) {
        switchActivity(context, LoginActivity.class);
    }

    public void switchToMainActivity(Context context) {
        switchActivity(context, MainActivity.class);
    }

    private static void switchActivity(Context context, Class<?> targetActivity) {

        Intent intent = new Intent(context, targetActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }


}

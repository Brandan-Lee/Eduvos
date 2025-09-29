package com.example.tripbuddy.ui.login;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tripbuddy.R;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_SCREEN_DURATION = 4000;
    private MediaPlayer mediaPlayer;
    private ImageView imgView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //find the image for the animation
        imgView = findViewById(R.id.splash_image);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.zoom);

        if (imgView != null && animation != null) {
            imgView.startAnimation(animation);
        }

        //to start the splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        }, SPLASH_SCREEN_DURATION);
    }

}

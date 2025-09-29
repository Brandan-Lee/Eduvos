package com.example.tripbuddy.ui.view_gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.tripbuddy.R;

public class FullScreenActivity extends AppCompatActivity {

    private ImageView img;
    private Button btnBack, btnPlay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        findViews();
        getImage();

    }

    private void findViews() {

        img = findViewById(R.id.fullScreenImageView);
        btnBack = findViewById(R.id.back_button);
        btnPlay = findViewById(R.id.play_memory_button);

    }

    private void getImage() {

        Intent intent = getIntent();
        String path = intent.getStringExtra("path");

        if (path != null) {

            Glide.with(this)
                    .load(path)
                    .into(img);

        }

    }

    private void handleButtons() {

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}

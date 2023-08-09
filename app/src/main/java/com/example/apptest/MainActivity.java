package com.example.apptest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton arrow_camera_card = findViewById(R.id.CameraCardArrow);
        ImageView view_camera = findViewById(R.id.CameraView);

        arrow_camera_card.setOnClickListener(view -> {
            int flag = view_camera.getVisibility();
            switch (flag) {
                case View.VISIBLE: {
                    view_camera.setVisibility(View.GONE);
                    arrow_camera_card.setImageResource(R.drawable.arrow_down);
                    return;
                }
                case View.GONE: {
                    view_camera.setVisibility(View.VISIBLE);
                    arrow_camera_card.setImageResource(R.drawable.arrow_up);
                    return;
                }
            }

        });
    }
}

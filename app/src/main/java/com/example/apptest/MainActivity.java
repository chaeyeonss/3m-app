package com.example.apptest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton goto_camera = findViewById(R.id.CameraCardArrow);
        goto_camera.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, camera.class);
            startActivity(intent);
        });
    }
}

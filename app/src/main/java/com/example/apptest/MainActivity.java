package com.example.apptest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
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

        CardView catchCard = findViewById(R.id.CatchCard);
        catchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCatchCard(view);
            }
        });

        CardView reportedCard = findViewById(R.id.ReportedCard);
        reportedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickReportedCard(view);
            }
        });
    }

    public void clickCatchCard(View view){
        Intent intent = new Intent(this, ActiveReports.class);
        startActivity(intent);
    }

    public void clickReportedCard(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

package com.example.apptest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {

    private String username;
    private String password;
    private String fcm_token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 뷰 바인딩
        EditText id = findViewById(R.id.et_id);
        EditText pwd = findViewById(R.id.et_pass);
        Button btn_login = findViewById(R.id.btn_login);
        Button btn_register = findViewById(R.id.btn_register);

        // 회원가입 버튼을 클릭 시 수행
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve input values
                username = id.getText().toString();
                password = pwd.getText().toString();

                // Basic validation
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "공백 불가", Toast.LENGTH_SHORT).show();
                    return;
                }

                // FirebaseMessaging을 사용하여 FCM 토큰을 받아오고 서버로 전송하는 부분 추가
                FirebaseMessaging.getInstance().getToken()
                        .addOnCompleteListener(task -> {
                            if (!task.isSuccessful()) {
                                // 토큰 얻어오기 실패
                                Log.w("FCM_TOKEN", "Fetching FCM registration token failed", task.getException());
                                return;
                            }
                            // 토큰 얻어오기 성공
                            fcm_token = task.getResult();
                            Log.d("FCM_TOKEN", "FCM token: " + fcm_token);

                            // 토큰을 서버로 전달하는 AsyncTask 실행
                            new SendTokenToServerTask().execute(fcm_token);
                            boolean isSuccess = MyFirebaseMessagingService.getSuccess();

                            if(!isSuccess){ // 임시로 넘어가도록 설정한거임 MyFirevaseMessagingService에서 요청 성공하면 LoginActivity 메인으로 넘어가도록 해야하는데 자꾸 false값만 받아옴 왜 why how 어떻게 해야하는지 모르겠음;
                                SharedPreferences preferences = getSharedPreferences("UserData", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("MemberID", username);
                                editor.putString("Password", password);
                                editor.apply();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                });

                            }
                        });
            }
        });
    }

    //AsyncTask를 사용하여 FCM 토큰을 서버로 전송하는 역할을 하는 클래스
    private class SendTokenToServerTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... tokens) {
            String token = tokens[0];
            MyFirebaseMessagingService messagingService = new MyFirebaseMessagingService();
            messagingService.createUserAndSendToken(token, username, password);
            return null;
        }
    }
}
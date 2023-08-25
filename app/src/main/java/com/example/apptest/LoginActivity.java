package com.example.apptest;

import androidx.appcompat.app.AppCompatActivity;

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
        btn_register.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

<<<<<<< HEAD
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve input values
                String username = id.getText().toString();
                String password = pwd.getText().toString();

                // Basic validation
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "공백 불가", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences preferences = getSharedPreferences("UserData", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("MemberID", username);
                editor.putString("Password", password);
                editor.apply();

                try {
                    JSONObject signInData = new JSONObject();
                    signInData.put("MemberID", username);
                    signInData.put("Password", password);

                    sendSignInDataToServer(signInData);
                } catch (JSONException e) {
                    e.printStackTrace();
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
                            String token = task.getResult();
                            Log.d("FCM_TOKEN", "FCM token: " + token);

                            // 토큰을 서버로 전달하는 AsyncTask 실행
                            new SendTokenToServerTask(username, password).execute(token);
                        });
            }
=======
        btn_login.setOnClickListener(view -> {
            // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
            String MemberID = id.getText().toString();
            String Password = pwd.getText().toString();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            Toast.makeText(getApplicationContext(),"Hello, user185!",Toast.LENGTH_SHORT).show();
            startActivity(intent);

            Response.Listener<String> responseListener = response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("message");

                    Log.i("[로그인]", MemberID + " " + Password);
                    Toast.makeText(getApplicationContext(),String.format("%s, %s", MemberID, Password),Toast.LENGTH_SHORT).show();

                    if (success) { // 로그인에 성공한 경우
                        String MemberID1 = jsonObject.getString("MemberID");
                        String Password1 = jsonObject.getString("Password");
                        String Name = jsonObject.getString("Name");

                        //Toast.makeText(getApplicationContext(), String.format("%s님 환영합니다.", Name), Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                        //intent.putExtra("MemberID", MemberID1);
                        //intent.putExtra("Password", Password1);
                        //intent.putExtra("Name", Name);

                        //startActivity(intent);

                    } else { // 로그인에 실패한 경우
                        Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };
            LoginRequest loginRequest = new LoginRequest(MemberID, Password, responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
>>>>>>> 64edbc2b754e95ff9cca625481fb5fb448442905
        });
    }

    private void sendSignInDataToServer(JSONObject signInData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://10.0.2.2:8000/user/signin");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST"); // POST 형식으로 전송
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json; utf-8");

                    String jsonData = signInData.toString();
                    try (OutputStream os = connection.getOutputStream()) {
                        byte[] input = jsonData.getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    int responseCode = connection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        Log.d("HTTP_RESPONSE", "Request successful");

                    } else {
                        Log.e("HTTP_RESPONSE", "Request failed with code: " + responseCode);

                    }

                    connection.disconnect(); // 연결 종료

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("HTTP_RESPONSE", "Connection failed: " + e.getMessage()); // 추가된 부분
                }
            }
        }).start();
    }

    // AsyncTask를 사용하여 FCM 토큰을 서버로 전송하는 역할을 하는 클래스
    private class SendTokenToServerTask extends AsyncTask<String, Void, Void> {
        private String username;
        private String password;

        SendTokenToServerTask(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected Void doInBackground(String... tokens) {
            String token = tokens[0];
            MyFirebaseMessagingService messagingService = new MyFirebaseMessagingService();
            messagingService.createUserAndSendToken(token, username, password);
            return null;
        }
    }
}
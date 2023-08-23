package com.example.apptest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText id = findViewById(R.id.et_id);
        EditText pwd = findViewById(R.id.et_pass);
        Button btn_login = findViewById(R.id.btn_login);
        Button btn_register = findViewById(R.id.btn_register);


        // 회원가입 버튼을 클릭 시 수행
        btn_register.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

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
        });
    }
}
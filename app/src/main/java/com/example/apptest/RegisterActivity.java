package com.example.apptest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText etId, etPass, etPassCheck, etAccount, etUnique, etName, etRegistration, etArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 뷰들 바인딩
        etId = findViewById(R.id.et_id);
        etPass = findViewById(R.id.et_pass);
        etPassCheck = findViewById(R.id.et_passcheck);
        etAccount = findViewById(R.id.et_account);
        etUnique = findViewById(R.id.et_unique);
        etName = findViewById(R.id.et_name);
        etRegistration = findViewById(R.id.et_registration);
        etArea = findViewById(R.id.et_area);
        CheckBox checkBox = findViewById(R.id.checkbox);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("회원가입");

        Button btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UniqueNumber = etUnique.getText().toString();
                String Name = etName.getText().toString();
                String Registration = etRegistration.getText().toString();
                String Area = etArea.getText().toString();
                String MemberID = etId.getText().toString();
                String Password = etPass.getText().toString();
                String CheckPass = etPassCheck.getText().toString();
                String Account = etAccount.getText().toString();

                Intent intent = new Intent(RegisterActivity.this, camera.class);
                intent.putExtra("MemberID", MemberID);
                startActivity(intent);

                Log.d("이름", Name);
                Log.d("제품번호", UniqueNumber);
                Log.d("계좌", Account);
                Log.d("주민번호", Registration);
                Log.d("아이디", MemberID);
                Log.d("비밀번호", Password);

                if (isValidInput()) {
                    // 입력된 정보를 JSON 형식으로 변환하여 서버로 전송
                    JSONObject signUpData = createSignUpDataJson();
                    // JSON 데이터 확인을 위한 Log 추가
                    Log.d("JSON Data", signUpData.toString());
                    sendSignUpDataToServer(signUpData);
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:{
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isValidInput() {
        // 입력된 정보의 유효성 검사 로직을 추가 (비밀번호 유효성 추가하기)

        return true;
    }

    private JSONObject createSignUpDataJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("MemberID", etId.getText().toString());
            jsonObject.put("Password", etPass.getText().toString());
            jsonObject.put("BankAccount", etAccount.getText().toString());
            jsonObject.put("UniqueNumber", etUnique.getText().toString());
            jsonObject.put("Name", etName.getText().toString());
            jsonObject.put("ResidentRegistration", etRegistration.getText().toString());
            jsonObject.put("ResidentialArea", etArea.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void sendSignUpDataToServer(JSONObject signUpData) {
        // 서버 URL 넣기
        String serverUrl = "https://your_server_url.com/api/signup";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, serverUrl, signUpData,
                response -> {
                    // 서버 응답 처리
                    try {
                        boolean success = response.getBoolean("success");
                        String message = response.getString("message");

                        if (success) {
                            // 회원가입 성공
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                            // 회원가입 성공 시의 처리 추가
                        } else {
                            // 회원가입 실패
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                            // 회원가입 실패 시의 처리 추가
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "JSON 파싱 오류", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    // 에러 처리
                    Toast.makeText(RegisterActivity.this, "서버 통신 오류", Toast.LENGTH_SHORT).show();
                });

        // 요청을 큐에 추가
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}







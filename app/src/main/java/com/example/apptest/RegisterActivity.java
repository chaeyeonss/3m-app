package com.example.apptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Member;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

<<<<<<< HEAD
    private EditText etId, etPass, etAge, etAccount, etcarnumber ,etUnique, etName, etRegistration, etArea, etpasscehck ;

    private boolean servercheck;
=======
    private EditText etId, etPass, etPassCheck, etAccount, etUnique, etName, etRegistration, etArea;
>>>>>>> 03490e12f29232cbced10504fc11c19846542bbe

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 뷰들 바인딩
        etId = findViewById(R.id.et_id);
        etPass = findViewById(R.id.et_pass);
        etpasscehck = findViewById(R.id.et_passcheck);
        etAge = findViewById(R.id.et_age);
        etcarnumber = findViewById(R.id.et_carnumber);
        etAccount = findViewById(R.id.et_account);
        etUnique = findViewById(R.id.et_unique);
        etName = findViewById(R.id.et_name);
        etRegistration = findViewById(R.id.et_registration);
        etArea = findViewById(R.id.et_area);
        CheckBox checkBox = findViewById(R.id.checkbox);
<<<<<<< HEAD
        CheckBox PassCheckBox = findViewById(R.id.passcheckbox);
=======
>>>>>>> 03490e12f29232cbced10504fc11c19846542bbe

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("회원가입");

        Button btnRegister = findViewById(R.id.btn_register);

        // 체크 박스 선택시 중복 확인
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    String MemberID = etId.getText().toString();
                    checkDuplicateUsername(MemberID);
                }
            }
        });

        PassCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (etPass.getText().toString().equals(etpasscehck.getText().toString())) {
                        Toast.makeText(RegisterActivity.this, "비밀번호가 일치합니다", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //회원가입 버튼 클릭 시 수행 하는 부분
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UniqueNumber = etUnique.getText().toString();
                String Name = etName.getText().toString();
                String Registration = etRegistration.getText().toString();
                String Area = etArea.getText().toString();
                String MemberID = etId.getText().toString();
                String Password = etPass.getText().toString();
                String Age = etAge.getText().toString();
                String Carnumber = etcarnumber.getText().toString();
                String Account = etAccount.getText().toString();

                Intent intent = new Intent(RegisterActivity.this, camera.class);
                intent.putExtra("MemberID", MemberID);
                startActivity(intent);

                if (isValidInput(Password)) {
                    // 입력된 정보를 JSON 형식으로 변환하여 서버로 전송
                    JSONObject signUpData = createSignUpDataJson();
                    // JSON 데이터 확인을 위한 Log 추가
                    Log.d("JSON Data", signUpData.toString());
                    sendSignUpDataToServer(signUpData);

                    if (servercheck){
                        Toast.makeText(RegisterActivity.this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                        Intent intentlogin = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intentlogin);
                    }else {
                        Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                    }
                }else{

                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:{
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

<<<<<<< HEAD
    private boolean isValidInput(String password) {
        // 비밀번호가 8글자 이상인지 확인
        if (password.length() < 8) {
            Toast.makeText(RegisterActivity.this, "8글자 이상인지 확인해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }

        // 특수문자가 하나 이상 포함되어 있는지 확인
        boolean hasSpecialCharacter = false;
        for (char c : password.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                hasSpecialCharacter = true;
                break;
            }
        }
        if (!hasSpecialCharacter) {
            Toast.makeText(RegisterActivity.this, "특수 문자 포함 확인", Toast.LENGTH_SHORT).show();
            return false;
        }

        // 대문자가 하나 이상 포함되어 있는지 확인
        boolean hasUpperCase = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
                break;
            }
        }
        if (!hasUpperCase) {
            Toast.makeText(RegisterActivity.this, "대문자 1개 포함", Toast.LENGTH_SHORT).show();
            return false;
        }
=======
    private boolean isValidInput() {
        // 입력된 정보의 유효성 검사 로직을 추가 (비밀번호 유효성 추가하기)
>>>>>>> 03490e12f29232cbced10504fc11c19846542bbe

        return true;
    }

    private JSONObject createSignUpDataJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UniqueNumber", etUnique.getText().toString());
            jsonObject.put("Name", etName.getText().toString());
            jsonObject.put("ResidentRegistration", etRegistration.getText().toString());
            jsonObject.put("ResidentialArea", etArea.getText().toString());
            jsonObject.put("MemberID", etId.getText().toString());
            jsonObject.put("Password", etPass.getText().toString());
            jsonObject.put("Age", etAge.getText().toString());
            jsonObject.put("CarNumber", etcarnumber.getText().toString());
            jsonObject.put("BankAccount", etAccount.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

<<<<<<< HEAD
    //데이터 서버 전송 부분
    private void sendSignUpDataToServer(@NonNull JSONObject signUpData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://10.0.2.2:8000/user/signup"); // 서버 URL 변경 가능
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json; utf-8");

                    String jsonData = signUpData.toString();
                    try (OutputStream os = connection.getOutputStream()) {
                        byte[] input = jsonData.getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    int responseCode = connection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        Log.d("HTTP_RESPONSE", "Request successful");
                        servercheck = true;
                    } else {
                        Log.e("HTTP_RESPONSE", "Request failed with code: " + responseCode);
                        servercheck = false;
                    }

                    connection.disconnect(); // 연결 종료

                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle the exception
                    // ...
                }
            }
        }).start();
    }

    //중복 확인 부분
    private void checkDuplicateUsername(String MemberID) {
        // 서버에서 중복된 아이디를 확인하는 요청을 보냅니다.
        String duplicateCheckUrl = "http://10.0.2.2:8000/user/useridcheck"; // 실제 URL로 변경

        JSONObject requestData = new JSONObject();
        try {
            requestData.put("MemberID", MemberID);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest duplicateCheckRequest = new JsonObjectRequest(Request.Method.POST, duplicateCheckUrl, requestData,
                response -> {
                    try {
                        boolean isDuplicate = response.getBoolean("isDuplicate");
                        if (isDuplicate) {
                            // 중복된 아이디가 발견되었습니다.
                            Toast.makeText(RegisterActivity.this, "이미 사용 중인 아이디입니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            // 중복된 아이디가 없습니다.
                            // 여기서는 특별한 처리를 하지 않습니다.
=======
    private void sendSignUpDataToServer(JSONObject signUpData) {
        // 서버 URL 넣기
        String serverUrl = "http://www.univ237.com/Login.php";

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
>>>>>>> 03490e12f29232cbced10504fc11c19846542bbe
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "JSON 파싱 오류", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
<<<<<<< HEAD
                    // 오류 처리
=======
                    // 에러 처리
>>>>>>> 03490e12f29232cbced10504fc11c19846542bbe
                    Toast.makeText(RegisterActivity.this, "서버 통신 오류", Toast.LENGTH_SHORT).show();
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(duplicateCheckRequest);
    }


}







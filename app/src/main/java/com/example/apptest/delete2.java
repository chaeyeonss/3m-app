package com.example.apptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class delete2 extends AppCompatActivity {

    // 서버 URL
    private static final String SERVER_URL = "http://your_server_url.com/delete_member";

    // 회원 ID (가정)
    private static final String memberId = "your_member_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원 탈퇴 처리를 위해 서버로 데이터 전송
                deleteMemberOnServer(memberId);
            }
        });
    }

    // 서버로 회원 탈퇴 요청을 보내는 함수
    private void deleteMemberOnServer(String memberId) {
        // 서버에 보낼 데이터를 JSONObject에 담기
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("memberId", memberId);
            // 추가적으로 필요한 데이터가 있다면 여기에 추가하면 됩니다.
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Volley Request Queue 생성
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // 서버로 보낼 POST 요청 생성
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, SERVER_URL, requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            String message = response.getString("message");
                            if (success) {
                                // 서버에서 성공적인 응답을 받았을 때의 처리
                                // TODO: 성공적인 탈퇴 처리 및 액티비티 종료 등의 작업
                                finish(); // 현재 액티비티 종료
                            } else {
                                // 서버에서 실패 응답을 받았을 때의 처리
                                // TODO: 실패 처리
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 서버 통신 실패
                        // TODO: 에러 처리
                    }
                });

        // Request Queue에 요청 추가
        requestQueue.add(request);
    }
}
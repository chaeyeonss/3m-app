package com.example.apptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class camera extends AppCompatActivity {

    // 서버 URL (가정)
    private static final String SERVER_URL = "서버 url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // OCR 처리를 통해 번호판, 위치, 시간 정보 가정

        String licensePlate = "1234ABCD";
        String location = "서울시 강남구";
        String time = "2023-07-30 12:34:56";

        Intent intent = getIntent();
        // 회원 ID
        String memberId = intent.getStringExtra("MemberID");

        // 30분
        PeriodicWorkRequest periodicWorkRequest =
                new PeriodicWorkRequest.Builder(MyWorker.class, 30, TimeUnit.MINUTES)
                        .build();

        WorkManager.getInstance(this).enqueue(periodicWorkRequest);
    }

    public static class MyWorker extends Worker {

        private final String memberId;

        public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
            super(context, workerParams);
            memberId = workerParams.getInputData().getString("memberId");
        }

        @NonNull
        @Override
        public Result doWork() {
            // OCR 결과로 추출된 정보
            String licensePlate = "1234ABCD"; // 번호판 정보 (가정)
            String location = "서울시 강남구"; // 위치 정보 (가정)
            String time = "2023-07-30 12:34:56"; // 시간 정보 (가정)

            // 서버로 데이터를 전송하는 코드 작성
            sendDataToServer(memberId, licensePlate, location, time);

            return Result.success(); // 작업 성공 시 success 반환
        }

        // 서버로 데이터를 전송하는 함수
        private void sendDataToServer(String memberId, String licensePlate, String location, String time) {
            // 서버로 보낼 데이터를 JSONObject에 담기
            JSONObject requestData = new JSONObject();
            try {
                requestData.put("memberId", memberId);
                requestData.put("licensePlate", licensePlate);
                requestData.put("location", location);
                requestData.put("time", time);
                // 추가적으로 필요한 데이터가 있다면 여기에 추가하면 됩니다.
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("memberId", "memberId");
            Log.d("licensePlate", "licensePlate");
            Log.d("location", "location");
            Log.d("time", "time");
            // Volley Request Queue 생성
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            // 서버로 보낼 POST 요청 생성
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, SERVER_URL, requestData,
                    response -> {
                        try {
                            // 서버로부터 받은 응답 데이터 처리
                            String serverResponse = response.getString("success");
                            // 예를 들어, 서버에서 "success"라는 메시지를 받았을 때 성공적으로 전송된 것으로 간주할 수 있습니다.
                            if (serverResponse.equals("success")) {
                                // 전송 성공 부분
                            } else {
                                // 전송 실패 부분
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        // 서버 통신 실패 (생략)
                    });

            // Request Queue에 요청 추가
            requestQueue.add(request);
        }
    }
}
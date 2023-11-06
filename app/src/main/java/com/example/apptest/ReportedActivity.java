package com.example.apptest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ReportedActivity extends AppCompatActivity {

    ArrayList<ReportedItem> reportedItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycarreportcheck);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("내 차 신고된 내역");

        this.initializeList();

        TextView date = (TextView) findViewById(R.id.reported_detail_date);
        TextView address = (TextView) findViewById(R.id.reported_detail_address);
        TextView status = (TextView) findViewById(R.id.reported_detail_car);


        ListView listView = (ListView) findViewById(R.id.reported_table);
        ReportedListAdapter adapter = new ReportedListAdapter(this, reportedItemList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                date.setText(adapter.getItem(position).getDate());
                address.setText(adapter.getItem(position).getLongitube());
                status.setText(adapter.getItem(position).getId());
            }
        });


        //Intent intent = getIntent();

        //ListView reportedListView = (ListView) findViewById(R.id.reported_table);
        //List<ReportedItem> reportedItemList = new ArrayList<ReportedItem>();

        //ReportedListAdapter adapter = new ReportedListAdapter(getApplicationContext(), reportedItemList);
        //reportedListView.setAdapter(adapter);

        //try {
        //    JSONObject jsonObject = new JSONObject(intent.getStringExtra("reportedList"));

        //    JSONArray jsonArray = jsonObject.getJSONArray("response");
        //    int count = 0;
        //    String date, latitude, longitube, carNum, uniqNum, id, reportStatus;

        //    while (count < jsonArray.length()){
       //         JSONObject object = jsonArray.getJSONObject(count);

        //        date = object.getString("Date");
        //        latitude = object.getString("Latitude");
        //        longitube = object.getString("Longitube");
       //         carNum = object.getString("CarNum");
        //        uniqNum = object.getString("UniqueNumber");
       //         id = object.getString("MemberID");
        //        reportStatus = object.getString("ReportStatus");

       //         ReportedItem reportedItem = new ReportedItem(date, latitude, longitube, carNum, uniqNum, id, reportStatus);
        //        reportedItemList.add(reportedItem);
        //        count++;
       //     }
       // } catch (JSONException e) {
        //    e.printStackTrace();
       // }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home: {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void initializeList() {
        reportedItemList = new ArrayList<ReportedItem>();

//        reportedItemList.add(new ReportedItem("2023.03.01. 17:32:33","17:38:20","충청남도 아산시 중앙로 17","12모3456","1","고지서 발송 D+12","승인"));
//        reportedItemList.add(new ReportedItem("2023.06.06. 13:02:58","13:10:03","충청남도 아산시 순천향로","34가5421","2","신고 반려됨","반려"));
//        reportedItemList.add(new ReportedItem("2023.08.19. 19:48:03","20:01:16","인천광역시 연수구 선학로 65","82서8275","3","접수 중","접수 중"));

        SharedPreferences preferences = getSharedPreferences("UserData", MODE_PRIVATE);
        //String savedCarNum = preferences.getString("CarNum", "");
        String savedCarNum = "1234모34";
        String urls = "http://10.0.2.2:8000/reportcheck/mycar";

        Thread.currentThread().interrupt();
        new Thread(() -> {
            try {
                URL url = new URL(urls);
                Log.d("teeeeeest", String.valueOf(url));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // HTTP POST 설정
                connection.setRequestMethod("POST"); // POST 형식으로 전송
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json; utf-8");

                // JSON 데이터 생성
                JSONObject jsonRequest = new JSONObject();
                jsonRequest.put("CarNum", savedCarNum);

                // JSON 데이터 전송
                OutputStream os = connection.getOutputStream();
                Log.e("Debug", os.toString());
                os.write(jsonRequest.toString().getBytes(StandardCharsets.UTF_8));
                os.close();

                // HTTP 응답 코드 확인
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK){
                    // JSON 응답 읽기
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // JSON 파싱
                    JSONArray jsonArray = new JSONArray(response.toString());

//                    "before_time": report.BeforeDate,
//                            "after_time": report.AfterDate,
//                            "latitude": car.Latitude,
//                            "longitude": car.Longitube
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String date = jsonObject.getString("date");
                        String latitude = jsonObject.getString("latitude");
                        String longitude = jsonObject.getString("longitude");
                        String carNum = jsonObject.getString("carNum");
                        String uniqNum = jsonObject.getString("uniqNum");
                        String id = jsonObject.getString("id");
                        String reportStatus = jsonObject.getString("reportStatus");

                        ReportedItem reportedItem = new ReportedItem(date, latitude, longitude, carNum, uniqNum, id, reportStatus);
                        reportedItemList.add(reportedItem);
                    }

                    // reportedList를 사용하여 필요한 작업 수행
                    for (ReportedItem item : reportedItemList) {
                        System.out.println(item);
                    }
                    System.out.println("API 요청 성공");
                }else {
                    System.out.println("API 요청 실패: " + responseCode);
                }

            } catch (Exception e) {
                reportedItemList.add(new ReportedItem("2023.07.12. 17:32:33","17:38:20","충청남도 아산시 중앙로 17","12모3456","1","8,000","승인"));
                e.printStackTrace();
            }
        });
    }

}
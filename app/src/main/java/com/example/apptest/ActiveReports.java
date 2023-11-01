package com.example.apptest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSInput;

import java.util.ArrayList;
import java.util.List;

public class ActiveReports extends AppCompatActivity {

    ArrayList<ReportedItem> reportedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myreportcheck);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("내가 신고한 내역");

        this.initializeList();

        TextView date = (TextView) findViewById(R.id.reported_detail_date);
        TextView address = (TextView) findViewById(R.id.reported_detail_address);
        TextView carNum = (TextView) findViewById(R.id.reported_detail_car);
        TextView status = (TextView) findViewById(R.id.reported_detail_status);
        TextView reward = (TextView) findViewById(R.id.reported_detail_reward);

        ListView listView = (ListView) findViewById(R.id.reported_table);
        ReportedListAdapter adapter = new ReportedListAdapter(this, reportedList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                date.setText(adapter.getItem(position).getDate());
                address.setText(adapter.getItem(position).getLongitube());
                carNum.setText(adapter.getItem(position).getCarNum());
                status.setText(adapter.getItem(position).getReportStatus());
                reward.setText(adapter.getItem(position).getId());
            }
        });

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
        reportedList = new ArrayList<ReportedItem>();

        reportedList.add(new ReportedItem("2023.07.12. 17:32:33","17:38:20","충청남도 아산시 중앙로 17","12모3456","1","8,000","승인"));
        reportedList.add(new ReportedItem("2023.07.22. 13:02:58","13:10:03","충청남도 아산시 순천향로","34가5421","2","-","반려"));
        reportedList.add(new ReportedItem("2023.07.29. 22:00:07","22:05:28","서울시 마포구 월드컵북로","64길7869","3","8,000","승인"));
        reportedList.add(new ReportedItem("2023.08.12. 09:23:25","10:00:03","대구광역시 달서구 월배로","33바2947","4","5,000","승인"));
        reportedList.add(new ReportedItem("2023.08.20. 19:48:03","20:01:16","인천광역시 연수구 선학로 65","82서8275","5","-","접수 중"));
    }
}
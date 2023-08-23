package com.example.apptest;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ReportedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycarreportcheck);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("내 차 신고된 내역");


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

}
package com.example.apptest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActiveReports extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myreportcheck);

<<<<<<< HEAD
        Intent intent = getIntent();

        ListView reportedListView = (ListView) findViewById(R.id.reported_table);
        List<ReportedItem> reportedItemList = new ArrayList<ReportedItem>();

        ReportedListAdapter adapter = new ReportedListAdapter(getApplicationContext(), reportedItemList);
        reportedListView.setAdapter(adapter);

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("reportedList"));

            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String date, latitude, longitube, carNum, uniqNum, id, reportStatus;

            while (count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);

                date = object.getString("Date");
                latitude = object.getString("Latitude");
                longitube = object.getString("Longitube");
                carNum = object.getString("CarNum");
                uniqNum = object.getString("UniqueNumber");
                id = object.getString("MemberID");
                reportStatus = object.getString("ReportStatus");

                ReportedItem reportedItem = new ReportedItem(date, latitude, longitube, carNum, uniqNum, id, reportStatus);
                reportedItemList.add(reportedItem);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
=======
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
>>>>>>> 03490e12f29232cbced10504fc11c19846542bbe
    }
}
package com.example.apptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ReportedListAdapter extends BaseAdapter {

    private Context context = null;
    LayoutInflater mLayoutInflater = null;
    private List<ReportedItem> reportedItemList;

    public ReportedListAdapter(Context context, List<ReportedItem> reportedItemList){
        this.context = context;
        this.reportedItemList = reportedItemList;
        mLayoutInflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return reportedItemList.size();
    }

    @Override
    public ReportedItem getItem(int i) {
        return reportedItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = mLayoutInflater.inflate(R.layout.reported_item, null);


        TextView no = (TextView) v.findViewById(R.id.no);
        TextView reportedDate = (TextView) v.findViewById(R.id.first_reported);
        TextView reportedAddress = (TextView) v.findViewById(R.id.reported_address);
        TextView secondReported = (TextView) v.findViewById(R.id.second_reported);
        TextView reportedCar = (TextView) v.findViewById(R.id.car_num);
        TextView reportedStatus = (TextView) v.findViewById(R.id.report_status);

        //reportedDate.setText(reportedItemList.get(i).getDate());
        //reportedAddress.setText(); TODO 위경도 -> 주소값으로 바꿔 출력하기
        //secondReported.setText(reportedItemList.get(i).getDate()); // TODO 두 번째 감지로 변경
        //reportedCar.setText(reportedItemList.get(i).getCarNum());
        //reportedStatus.setText(reportedItemList.get(i).getReportStatus());

        //v.setTag(reportedItemList.get(i).getId());

        no.setText(reportedItemList.get(i).getUniqNum());
        reportedDate.setText(reportedItemList.get(i).getDate());
        reportedAddress.setText(reportedItemList.get(i).getLongitube());
        secondReported.setText(reportedItemList.get(i).getLatitude());
        secondReported.setText(reportedItemList.get(i).getDate());
        reportedCar.setText(reportedItemList.get(i).getCarNum());
        reportedStatus.setText(reportedItemList.get(i).getReportStatus());

        return v;
    }
}

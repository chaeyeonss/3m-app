package com.example.apptest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ReportedListAdapter extends BaseAdapter {

    private Context context;
    private List<ReportedItem> reportedItemList;

    public ReportedListAdapter(Context context, List<ReportedItem> reportedItemList){
        this.context = context;
        this.reportedItemList = reportedItemList;
    }

    @Override
    public int getCount() {
        return reportedItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return reportedItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context, R.layout.reported_item, null);

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

        reportedDate.setText(reportedItemList.get(i).getDate());
        reportedAddress.setText("주소 위치");
        secondReported.setText(reportedItemList.get(i).getDate());
        reportedCar.setText(reportedItemList.get(i).getCarNum());
        reportedStatus.setText(reportedItemList.get(i).getReportStatus());

        return v;
    }
}

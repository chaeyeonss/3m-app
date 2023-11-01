package com.example.apptest;

public class ReportedItem {
    // (db) carlist
    String date;
    String latitude;
    String longitube; // TODO 스펠링 확인하기
    String carNum;
    String uniqNum;
    String id;
    String reportStatus;

    public ReportedItem(String date, String latitude, String longitube, String carNum, String uniqNum, String id, String reportStatus) {
        this.date = date;
        this.latitude = latitude;
        this.longitube = longitube;
        this.carNum = carNum;
        this.uniqNum = uniqNum;
        this.id = id;
        this.reportStatus = reportStatus;
    }

    public String getDate() {
        return date;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitube() {
        return longitube;
    }

    public String getCarNum() {
        return carNum;
    }

    public String getUniqNum() {
        return uniqNum;
    }

    public String getId() {
        return id;
    }

    public String getReportStatus() {
        return reportStatus;
    }
}
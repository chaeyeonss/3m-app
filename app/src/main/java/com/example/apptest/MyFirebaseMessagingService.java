package com.example.apptest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private SharedPreferences preferences;
    private String MemberID;
    private String password;
    private static boolean success = false;

    public static boolean getSuccess() {
        return success;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getSharedPreferences("UserData", MODE_PRIVATE);
        MemberID = preferences.getString("MemberID", null);
        password = preferences.getString("Password", null);
    }

    public class User {

        private String MemberID;
        private String password;
        private String fcm_token;

        public String getMemberID() {
            return MemberID;
        }

        public void setMemberID(String MenberID) {
            this.MemberID = MenberID;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFcm_token() {
            return fcm_token;
        }

        public void setFcm_token(String fcm_token) {
            this.fcm_token = fcm_token;
        }
    }

    // FCM 토큰이 생성될 때 호출되는 메서드
    @Override
    public void onNewToken(String fcm_token) {
        super.onNewToken(fcm_token);
        Log.d("FCM_TOKEN", fcm_token); // 토큰 출력

        // 사용자 정보 생성 및 토큰 서버로 전달
        createUserAndSendToken(fcm_token, MemberID, password);
    }

    // FCM 메시지 수신 시 호출되는 메서드
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            // 알림 메시지를 수신할 때 처리
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            // 알림 생성
            createNotification(title, body);
        }
    }

    // 알림 생성 및 표시 함수
    private void createNotification(String title, String body) {
        String channelId = "my_channel_id"; // 알림 채널 ID
        String channelName = "My Channel"; // 알림 채널 이름
        int importance = NotificationManager.IMPORTANCE_HIGH; // 알림 중요도

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_notification) // 작은 아이콘
                .setContentTitle(title) // 제목
                .setContentText(body) // 내용
                .setColor(Color.BLUE) // 아이콘 색상
                .setAutoCancel(true); // 알림을 클릭하면 자동으로 해제

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Android Oreo 이상부터는 알림 채널을 생성해야 함
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, builder.build()); // 알림 표시
    }

    // 백그라운드에서 사용자 정보와 토큰을 서버로 전송하는 AsyncTask
    private class SendUserAndTokenTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            User user = users[0];
            sendUserAndTokenToServer(user, success);
            return null;
        }
    }

    // 사용자 정보 생성 및 토큰 전송 함수
    void createUserAndSendToken(String fcm_token, String MemberID, String Password) {
        User user = new User();
        user.setMemberID(MemberID);
        user.setPassword(Password);
        user.setFcm_token(fcm_token);

        // 사용자 정보와 토큰 서버로 전송
        new SendUserAndTokenTask().execute(user);
    }

    // 사용자 정보와 토큰을 서버로 전송하는 함수
    private static void sendUserAndTokenToServer(User user, boolean success) {
        try {
            URL url = new URL("http://10.0.2.2:8000/user/signin"); // Django 서버의 엔드포인트 URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            // 사용자 정보를 요청에 추가
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(getUserAsJsonString(user)); // user 객체를 JSON 문자열로 변환하여 전송
            writer.flush();
            writer.close();
            outputStream.close();

            // 응답 코드 확인
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Log.e("FCM_TOKEN", "Request success: " + responseCode);
                // 요청이 성공적으로 처리됨
                Log.d("FCM_TOKEN", "User and token sent to server");
                success = true;

            } else {
                // 요청이 실패한 경우
                Log.e("FCM_TOKEN", "Failed to send user and token to server");
                Log.e("FCM_TOKEN", "Request failed: " + responseCode);
                success = false;

            }
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
    }

    // 사용자 정보 객체를 JSON 형식의 문자열로 변환하는 함수
    private static String getUserAsJsonString(User user) {
        // 사용자 정보를 JSON 문자열로 변환하여 반환
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("MemberID", user.getMemberID());
            jsonObject.put("Password", user.getPassword());
            jsonObject.put("fcm_token", user.getFcm_token());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
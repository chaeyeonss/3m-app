package com.example.apptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements Camera2APIs.Camera2Interface, TextureView.SurfaceTextureListener {

    private int REQUEST_CODE_PERMISSIONS = 1001;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};
    private TextureView mTextureView;
    private Camera2APIs mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SharedPreferences에서 MemberID 값을 가져옴
        SharedPreferences preferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String savedMemberID = preferences.getString("MemberID", "");

        // 가져온 MemberID 값을 화면에 출력
        TextView homeUserText = findViewById(R.id.HomeUserText);
        homeUserText.setText("Hello, " + savedMemberID + "!");

        //if (allPermissionsGranted()){
        //    startCamera(); // 회원 확인됐으면 카메라 시작
        //} else {
        //    ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        //}

        mTextureView = (TextureView) findViewById(R.id.CameraView);
        mTextureView.setSurfaceTextureListener(this);

        mCamera = new Camera2APIs(this);

        ImageButton arrow_camera_card = findViewById(R.id.CameraCardArrow);

        arrow_camera_card.setOnClickListener(view -> {
            int flag = mTextureView.getVisibility();
            switch (flag) {
                case View.VISIBLE: {
                    mTextureView.setVisibility(View.GONE);
                    onPause();
                    arrow_camera_card.setImageResource(R.drawable.arrow_down);
                    return;
                }
                case View.GONE: {
                    mTextureView.setVisibility(View.VISIBLE);
                    openCamera();
                    arrow_camera_card.setImageResource(R.drawable.arrow_up);
                    return;
                }
            }

        });

        TextView cameraCardTitle = (TextView) findViewById(R.id.CameraCardTitle);




        CardView catchCard = findViewById(R.id.CatchCard);
        catchCard.setOnClickListener(this::clickCatchCard);

        CardView reportedCard = findViewById(R.id.ReportedCard);
        reportedCard.setOnClickListener(this::clickReportedCard);
    }

    private void openCamera(){
        CameraManager cameraManager = mCamera.CameraManager_1(this);
        String cameraId = mCamera.CameraCharacteristics_2(cameraManager);
        mCamera.CameraDevice_3(cameraManager, cameraId);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera();
            } else {
                Toast.makeText(this, "회원 권한이 없습니다.", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        }
    }

    private void startCamera() {
    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void clickCatchCard(View view){
        Intent intent = new Intent(this, ActiveReports.class);
        startActivity(intent);
    }

    public void clickReportedCard(View view){
        Intent intent = new Intent(this, ReportedActivity.class);
        startActivity(intent);
    }

    private void uploadImage(Bitmap tempSelectFile) {
        RequestBody date = RequestBody.create(MediaType.parse("text/plain"), "날짜");
        RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"), "위도");
        RequestBody longitube = RequestBody.create(MediaType.parse("text/plain"), "경도");
        RequestBody memberID = RequestBody.create(MediaType.parse("text/plain"), "회원 아이디");
        RequestBody status = RequestBody.create(MediaType.parse("text/plain"), "상태");
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), String.valueOf(tempSelectFile));

        // TODO 서버로 데이터 전송하기
        // https://opheliesaysone.tistory.com/30
        //MultipartBody.Part parts = MultipartBody.Part.createFormData("image", tempSelectFile);

    }

    @Override
    public void onCameraDeviceOpened(CameraDevice cameraDevice, Size cameraSize) {
        SurfaceTexture texture = mTextureView.getSurfaceTexture();
        texture.setDefaultBufferSize(cameraSize.getWidth(), cameraSize.getHeight());
        Surface surface = new Surface(texture);

        mCamera.CaptureSession_4(cameraDevice, surface);
        mCamera.CaptureRequest_5(cameraDevice, surface);
    }

    @Override
    protected void onResume() {

        super.onResume();

        if (mTextureView.isAvailable()){
            openCamera();
        } else {
            mTextureView.setSurfaceTextureListener(this);
        }
    }

    @Override
    public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {
        openCamera();
    }

    @Override
    public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {

    }

    private void closeCamera(){
        mCamera.closeCamera();
    }

    @Override
    protected void onPause() {
        closeCamera();
        super.onPause();
    }
}

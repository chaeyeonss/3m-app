package com.example.apptest;

import android.content.Context;
import android.hardware.camera2.CameraManager; // camera2 api 사용하려면 cameramanager를 달라고 하는 것 같네요?
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class CameraSufaceView extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder holder;
    CameraManager camera = null;
    // https://developer.android.com/reference/android/hardware/camera2/package-summary

    public CameraSufaceView(Context context){
        super(context);
        init(context);
    }

    public CameraSufaceView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
}

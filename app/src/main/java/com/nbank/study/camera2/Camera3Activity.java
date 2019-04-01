package com.nbank.study.camera2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nbank.study.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import ico.ico.camera.widget.CameraPreviewView;
import ico.ico.helper.WindowHelper;
import ico.ico.ico.BaseFragActivity;

public class Camera3Activity extends BaseFragActivity {

    @BindView(R.id.cameraPreviewView)
    CameraPreviewView cameraPreviewView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowHelper windowHelper = new WindowHelper(getWindow());
        windowHelper.setWindowImmersive();
        setContentView(R.layout.activity_camera3);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 2000);
    }
}

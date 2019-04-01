package com.nbank.study.camera2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nbank.study.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ico.ico.camera.widget.CameraPreviewView;
import ico.ico.helper.WindowHelper;
import ico.ico.ico.BaseFragActivity;

public class Camera2Activity extends BaseFragActivity {
    @BindView(R.id.cameraPreviewView)
    CameraPreviewView cameraPreviewView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowHelper windowHelper = new WindowHelper(getWindow());
        windowHelper.setWindowImmersive();
        setContentView(R.layout.activity_camera2);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_jump)
    public void onClickJump(View v) {
        startActivity(new Intent(mActivity, JumpCamera3Activity.class));
    }
}

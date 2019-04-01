package com.nbank.study.camera2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nbank.study.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ico.ico.ico.BaseFragActivity;

public class JumpCamera3Activity extends BaseFragActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_camera3);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_jump)
    public void onClickJump(View v) {
        startActivityForResult(new Intent(mActivity, Camera3Activity.class), 0x001);
    }
}

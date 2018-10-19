package com.nbank.study;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nbank.study.camera.CameraActivity;
import com.nbank.study.cipher.SqlCipherActivity;
import com.nbank.study.tbs.TbsActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ico.ico.ico.BaseFragActivity;

public class MainActivity extends BaseFragActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_camera)
    public void onClickCamera(View v) {
        startActivity(new Intent(this, CameraActivity.class));
    }

    @OnClick(R.id.btn_tbs)
    public void onClickTBS(View v) {
        startActivity(new Intent(this, TbsActivity.class));
    }

    @OnClick(R.id.btn_encrypt)
    public void onClickCipher(View v) {
        startActivity(new Intent(this, SqlCipherActivity.class));
    }
}

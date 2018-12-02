package com.nbank.study;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.nbank.study.camera.CameraActivity;
import com.nbank.study.cipher.SqlCipherActivity;
import com.nbank.study.tbs.TbsActivity;
import com.nbank.study.test.TestActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ico.ico.helper.WebViewHelper;
import ico.ico.ico.BaseFragActivity;
import ico.ico.util.WindowHelper;
import ico.ico.util.log;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseFragActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        String d = WebViewHelper.genJsCode("dasdasd(das,dasd)");
        System.out.println("=="+d);
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

    @OnClick(R.id.btn_test)
    public void onClickTest(View v) {
        startActivity(new Intent(this, TestActivity.class));
    }

}

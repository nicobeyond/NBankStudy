package com.nbank.study;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.nbank.study.camera.CameraActivity;
import com.nbank.study.cipher.SqlCipherActivity;
import com.nbank.study.tbs.TbsActivity;
import com.nbank.study.test.TestActivity;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ico.ico.ico.BaseFragActivity;
import ico.ico.util.Common;
import ico.ico.util.log;

public class MainActivity extends BaseFragActivity {
    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            log.w("111=========asd.asd.asd received");
        }
    };
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            log.w("222=========asd.asd.asd received");
        }
    };
    EditText edit;

    public native int readByte();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        TextView txt = findViewById(R.id.txt);
        txt.setAutofillHints(View.AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_DATE);
        log.w("===" + ((TextView) findViewById(R.id.text)).getText().toString());

//        String[] d = new String[0];
//        SharedPreferences share = getSharedPreferences("das", Context.MODE_PRIVATE);
//        log.w("===" + share.getString("aaa", "ccc"));
//        share.edit().putString("aaa", "bbb").apply();
//        log.w("===" + share.getString("aaa", "ccc"));

//        Spinner spinner1 = (Spinner) findViewById(R.id.spinner_1);
//        ArrayAdapter<String> spinner1_adapter = new ArrayAdapter<String>(
//                mActivity, R.layout.spinner, d);
//        spinner1_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner1.setAdapter(spinner1_adapter);
//        String da = null;
//        ((TextView) findViewById(R.id.text)).setText(da);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(br);
    }

    @OnClick(R.id.btn_camera)
    public void onClickCamera(View v) {
        startActivity(new Intent(this, CameraActivity.class));
    }

    @OnClick(R.id.btn_tbs)
    public void onClickTBS(View v) {
        ParcelTest parcelTest = new ParcelTest();
        Intent intent = new Intent(this, TbsActivity.class);
        intent.putExtra("a", parcelTest);
        startActivity(intent);
    }

    @OnClick(R.id.btn_encrypt)
    public void onClickCipher(View v) {
        startActivity(new Intent(this, SqlCipherActivity.class));
    }

    @OnClick(R.id.btn_test)
    public void onClickTest(View v) {
        startActivity(new Intent(this, TestActivity.class));
    }

    PopupWindow popupWindow;

    @OnClick(R.id.btn_pop)
    public void onClickPop(View v) {
        if (popupWindow == null) {
            View view = getLayoutInflater().inflate(R.layout.activity_main, null, false);
            popupWindow = new PopupWindow(view, 500, 500);
        }
        if (popupWindow.isShowing()) {

        }
        popupWindow.showAsDropDown(findViewById(R.id.btn_camera));
    }


    @OnClick(R.id.btn_io)
    public void onClickIO(View v) {
        File file = new File(Environment.getExternalStorageDirectory() + "/aaa/bbb/ccc/222.txt");
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                showToast("文件夹创建失败");
                return;
            }
        }
        if (file.exists()) {
            if (!file.delete()) {
                showToast("文件已存在，文件删除失败");
                return;
            }
        }
        boolean flag;
        try {
            flag = file.createNewFile();
            showToast("文件创建" + flag);
        } catch (IOException e) {
            e.printStackTrace();
            showToast("文件创建异常" + e.toString());
        }
    }

    @OnClick(R.id.btn_obtain)
    public void onClickObtain(View v) {
        log.w("===" + Common.getScreenRealHeight(this));//2160  2160    2160
        log.w("===" + Common.getScreenHeight(this));//2160      2016    2016
        log.w("===" + findViewById(R.id.container).getHeight());//2088  1944    2088
        log.w("===" + Common.getStatusHeight(this));//72
        log.w("===" + Common.getNavigationBarHeight(this));//144
//        log.w("===" + Common.checkNavigationBar(this));//true true

    }

    @OnClick(R.id.btn_dialog)
    public void onClickDialog(View v) {
        startActivity(new Intent(MainActivity.this, DialogActivity.class));
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }, 5000);
    }

    @OnClick(R.id.btn_mem)
    public void onClickMem(View v) {
        String d = null;
        //57.2 70.3 69.6 69.2 =12
        //57.1 68.9 = 11.8

        long time = System.currentTimeMillis();
        for (int i = 0; i < 250; i++) {
            if (d != null) {
                log.w("===" + d.equals(""));
            }
        }
        log.w("===" + (System.currentTimeMillis() - time), "1");
        time = System.currentTimeMillis();
        //58 69.2
        //57.2
        try {
            for (int i = 0; i < 250; i++) {
                log.w("===" + d.equals(""));
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
        log.w("===" + (System.currentTimeMillis() - time), "2");
    }
}

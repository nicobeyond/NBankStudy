package com.nbank.study.logan;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dianping.logan.Logan;
import com.dianping.logan.SendLogRunnable;
import com.nbank.study.R;

import java.io.File;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ico.ico.ico.BaseFragActivity;
import ico.ico.util.Common;
import ico.ico.util.DateUtil;
import ico.ico.util.FileUtil;

public class LoganActivity extends BaseFragActivity {

    @BindView(R.id.btn_debug)
    Button btn_debug;
    @BindView(R.id.tv_query)
    TextView tv_query;
    @BindView(R.id.tv_upload)
    TextView tv_upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logan);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_debug)
    public void onClickDebug(View v) {
        if (v.getTag().toString().equals("0")) {
            Logan.setDebug(true);
            v.setTag("1");
            btn_debug.setText("关闭DEBUG模式");
        } else {
            Logan.setDebug(false);
            v.setTag("0");
            btn_debug.setText("开启DEBUG模式");
        }
    }

    int i = 1;

    @OnClick(R.id.btn_log)
    public void onClickLog(View v) {
        Logan.w(String.format("这是第%d次记录日志", i), i);
        Logan.f();
    }

    @OnClick(R.id.btn_query)
    public void onClickQuery(View v) {
        Map<String, Long> map = Logan.getAllFilesInfo();
        String d = "null";
        try {
            d = map.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_query.setText(d);
    }

    @OnClick(R.id.btn_upload1)
    public void onClickUpload1(View v) {
        tv_upload.setText("");
        Logan.s(new String[]{"2019-01-01"}, new SendLogRunnable() {
            @Override
            public void sendLog(File logFile) {
                File newFile = FileUtil.genFile(Environment.getExternalStorageDirectory().getAbsolutePath(), logFile.getName());
                boolean flag = FileUtil.copyFile(logFile, newFile);
                Common.refreshMediaSync(mActivity, newFile.getAbsolutePath());
                tv_upload.setText(String.format("upload结果：%b%n源文件：%s%n新文件：%s", flag, logFile.getAbsolutePath(), newFile.getAbsolutePath()));
            }
        });
    }

    @OnClick(R.id.btn_upload2)
    public void onClickUpload2(View v) {
        tv_upload.setText("");
        Logan.s(new String[]{DateUtil.getCurrentTime(DateUtil.Format.YEAR2DAY)}, new SendLogRunnable() {
            @Override
            public void sendLog(final File logFile) {
                final File newFile = FileUtil.genFile(Environment.getExternalStorageDirectory().getAbsolutePath(), logFile.getName());
                final boolean flag = FileUtil.copyFile(logFile, newFile);
                Common.refreshMediaSync(mActivity, newFile.getAbsolutePath());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_upload.setText(String.format("upload结果：%b%n源文件：%s%n新文件：%s", flag, logFile.getAbsolutePath(), newFile.getAbsolutePath()));
                    }
                });
            }
        });
    }
}

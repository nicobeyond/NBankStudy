package com.nbank.study.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;

import com.nbank.study.R;

import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import ico.ico.util.Common;
import ico.ico.widget.SimpleTextWatcher;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.et_md5)
    EditText etMd5;
    @BindView(R.id.tv_md5)
    TextView tvMd5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        etMd5.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                try {
                    String str1 = Common.toMD5(s.toString());
                    tvMd5.setText(str1);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

            }
        });
    }


}

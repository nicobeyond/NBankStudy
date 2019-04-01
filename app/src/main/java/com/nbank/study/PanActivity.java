package com.nbank.study;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class PanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pan);


        getSupportFragmentManager().beginTransaction().add(R.id.container, BlankFragment.newInstance("", ""), "123").commit();
    }
}

package com.nbank.study;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import ico.ico.widget.BottomDialogFrag;

public class MyBottomDialogFrag extends BottomDialogFrag {
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setWindowAnimations(R.style.login_popup_window_anim_style);
    }

    @Override
    public void onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, boolean isSaveStateFlag) {
        setContentView(R.layout.layout_login_pwd_more);
    }

    @Override
    public void onCreateDialog(Bundle savedInstanceState, boolean isSaveStateFlag) {

    }
}

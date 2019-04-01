package com.nbank.study;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class UIUtil {
    static DisplayMetrics metric;
    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int widthPixels(Context context) {
        return getMetric(context).widthPixels;
    }

    private static DisplayMetrics getMetric(Context context){
        if (metric == null) {
            metric = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay()
                    .getMetrics(metric);
        }
        return metric;
    }

    class A extends Thread{

    }

}

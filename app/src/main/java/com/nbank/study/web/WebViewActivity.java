package com.nbank.study.web;

import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.nbank.study.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import ico.ico.helper.WebViewHelper;
import ico.ico.ico.BaseFragActivity;
import ico.ico.util.log;

public class WebViewActivity extends BaseFragActivity {


    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        WebViewHelper.init(this, webview);
//        WebViewHelper.loadNetHtml(webview, "http://www.baidu.com");
        WebViewHelper.loadAssetsHtml(webview, "test.html");
        webview.addJavascriptInterface(new Object(){

            @JavascriptInterface
            public void asd(){
                log.w("===");
            }
        },"Android");
    }
}

package com.nbank.study;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.tencent.smtt.sdk.QbSdk;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ico.ico.ico.BaseFragActivity;
import ico.ico.util.log;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class TbsActivity extends BaseFragActivity implements TbsHelper.TbsListener {

    /** RequestCodePermission TBS */
    final static int RC_P_TBS = 100;

    TbsHelper tbsHelper;
    Runnable waitTask;

    @BindView(R.id.layout_readview)
    RelativeLayout layoutReadview;
    @BindView(R.id.et_file)
    EditText et_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbs);
        ButterKnife.bind(this);

        tbsHelper = new TbsHelper(mActivity, this);
        if (checkPermission()) return;
        initQbSdk();
    }


    @AfterPermissionGranted(RC_P_TBS)
    public void initQbSdk() {
        tbsHelper.init(false);
    }


    @OnClick(R.id.btn_reset)
    public void reset() {
        et_file.setText(Environment.getExternalStorageDirectory() + "");
    }

    final String filePath = "/storage/emulated/0/tencent/MicroMsg/Download/陈方毅劳动合同.pdf";
//    final String filePath = "/storage/emulated/0/tencent/MicroMsg/Download/订餐服务.docx";

    /* 使用sdk打开本地文件，需要手机安装有QQ浏览器或者微信，然后加载pdf插件总是加载失败 */
    @OnClick(R.id.btn_pdf)
    public void onClickPDF() {
        if (checkPermission()) return;

        String filePath = obtainFilePath();
        if (TextUtils.isEmpty(filePath)) {
            mActivity.showToast("文件不存在");
            return;
        }

        Runnable _task = new Runnable() {
            @Override
            public void run() {
                log.w("onClickPDF Runnable is run");
                tbsHelper.openFileOuter(filePath);
            }
        };
        if (QbSdk.isTbsCoreInited()) {
            mHandler.post(_task);
        } else {
            waitTask = _task;
        }
    }


    /** 应用内打开本地文件 */
    @OnClick(R.id.btn_pdf_inner)
    public void onClickPDFInner() {
        String filePath = obtainFilePath();
        if (TextUtils.isEmpty(filePath)) {
            mActivity.showToast("文件不存在");
            return;
        }
        Runnable _task = new Runnable() {
            @Override
            public void run() {
                log.w("onClickPDFInner Runnable is run");
                try {
                    tbsHelper.openFileInner(layoutReadview, filePath, Environment.getExternalStorageDirectory() + "/temp");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    showToast(e.getMessage());
                }

            }
        };
        if (QbSdk.isTbsCoreInited()) {
            mHandler.post(_task);
        } else {
            waitTask = _task;
        }
    }


    //region 权限相关

    /**
     * 检查权限
     *
     * @return boolean 是否需要请求
     */
    private boolean checkPermission() {
        if (!EasyPermissions.hasPermissions(mActivity, TbsHelper.PERMISSIONS)) {
            EasyPermissions.requestPermissions(mActivity, "需要权限才可以读取本地文件哦", RC_P_TBS, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE);
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    //endregion


    /** 获取文本输入框中的文件路径 */
    private String obtainFilePath() {
        String _filePath = et_file.getText().toString();
        File file = new File(_filePath);
        if (!file.exists()) return null;
        return _filePath;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tbsHelper.onDestory();
    }

    //region Tbs监听
    @Override
    public void onInitSuccess() {
        if (waitTask != null) {
            mHandler.post(waitTask);
        }
    }

    @Override
    public void onInitFail(int statusCode) {
        showToast("初始化失败，" + tbsHelper.getErrCodeMessage(statusCode));
    }

    @Override
    public void onDownloadProgress(int progress) {
        showToast("正在下载X5内核，进度：" + progress);
    }
    //endregion
}

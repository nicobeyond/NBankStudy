package com.nbank.study;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Printer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nbank.study.camera.CameraActivity;
import com.nbank.study.camera2.Camera2Activity;
import com.nbank.study.cipher.SqlCipherActivity;
import com.nbank.study.image.ImageTestActivity;
import com.nbank.study.logan.LoganActivity;
import com.nbank.study.tbs.TbsActivity;
import com.nbank.study.test.TestActivity;
import com.nbank.study.web.WebViewActivity;
import com.yitong.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ico.ico.constant.ImageLoaderPrefixConstant;
import ico.ico.ico.BaseFragActivity;
import ico.ico.util.Common;
import ico.ico.util.log;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseFragActivity implements EasyPermissions.PermissionCallbacks {
    EditText edit;
    Button button;

    @BindViews({R.id.icons1, R.id.icons2, R.id.icons3, R.id.icons4, R.id.icons5, R.id.icons6})
    TextView[] icons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Dump当前的MessageQueue信息.
//        getMainLooper().dump(new Printer() {
//
//            @Override
//            public void println(String x) {
//                log.w(x);
//            }
//        }, "onCreate");

         CountDownTimer countDownTimer = new CountDownTimer(1000,100) {
            @Override
            public void onTick(long millisUntilFinished) {
                log.w("onTick"+millisUntilFinished);
            }

            @Override
            public void onFinish() {
                log.w("onFinish");
            }
        };
         countDownTimer.start();
        test();
    }



    public void test() {
        try {
            byte[] byte1 = Base64.encode("aklsjdklas".getBytes(), Base64.DEFAULT);
            byte[] byte2 = Base64.encode(",.zxnmvnjaoerp".getBytes(), Base64.DEFAULT);
            byte[] byte3 = Base64.encode("roweijfskldmv,cx".getBytes(), Base64.DEFAULT);
            byte[] byte4 = Base64.encode("v,lzxjnfhwioeur".getBytes(), Base64.DEFAULT);
            byte[] byte5 = Base64.encode("qweiropjvmklc".getBytes(), Base64.DEFAULT);
            System.out.println("1===" + bytes2Int16(" ", byte1));
            System.out.println("2===" + bytes2Int16(" ", byte2));
            System.out.println("3===" + bytes2Int16(" ", byte3));
            System.out.println("4===" + bytes2Int16(" ", byte4));
            System.out.println("5===" + bytes2Int16(" ", byte5));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将单字节转化为16进制
     *
     * @param buffer
     * @return 转化后的16进制字符串，如果是长度为1则前面加0
     */
    public static String byte2Int16(byte buffer) {
        String str = Integer.toString(buffer & 0xFF, 16).toUpperCase();
        return str.length() == 1 ? 0 + str : str;
    }

    /**
     * 将一个字节数组转化为16进制然后通过连接符拼接在一起
     *
     * @param buffers 字符数组
     * @param joinStr 连接符
     * @return
     */
    public static String bytes2Int16(String joinStr, byte... buffers) {
        if (joinStr == null) {
            joinStr = "";
        }
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < buffers.length; i++) {
            sb.append(byte2Int16(buffers[i]));
            if (i != buffers.length - 1) {
                sb.append(joinStr);
            }
        }
        return sb.toString().toUpperCase();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(br);
    }

    @BindView(R.id.et_jubing)
    EditText etJubing;

    @OnClick(R.id.btn_jubing)
    public void onClickJubing(View v) {
        int size = Integer.parseInt(etJubing.getText().toString());
        for (int i = 0; i < size; i++) {
//            com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage("https://mobile.nbcb.com.cn/mobilebank/resources/bankmenu/657.png", ivTest);
//            ImageLoader.getInstance().displayImage("https://mobile.nbcb.com.cn/mobilebank/resources/bankmenu/657.png", ivTest);
//            ImageLoader.getInstance().displayImage(ImageLoaderPrefixConstant.FILE + "/storage/emulated/0/DCIM/1547280700753.jpg", ivTest);
            ImageLoader.getInstance().displayImage(ImageLoaderPrefixConstant.FILE + "/storage/emulated/0/tencent/MicroMsg/WeiXin/wx_camera_1550073465390.mp4", ivTest);
        }
    }

    @BindView(R.id.et_file)
    EditText etFile;

    @OnClick(R.id.btn_refresh)
    public void onClickRefresh(View v) {
        Common.refreshMediaSync(mActivity, etFile.getText().toString());
    }

    @OnClick(R.id.btn_insert)
    public void onClickInsert(View v) {
        SQLiteDatabase db = DBHelper.getDbHelper(this).openHelper.getWritableDatabase();
        db.execSQL("INSERT INTO test(id) VALUES(" + System.currentTimeMillis() + ");");
        db.close();
        Logger log = Logger.getLogger(getPackageName());
        log.setLevel(Level.ALL);
        log.severe("severe");
        log.warning("warning");
        log.info("info");
        log.config("config");
        log.fine("fine");
        log.finer("finer");
        log.finest("finest");

        log.entering("com.mycompany.mylib.Reader", "read", new Object[]{v.toString()});
        log.exiting("com.mycompany.mylib.Reader", "read", "dd");
    }

    @OnClick(R.id.btn_select)
    public void onClickSelect(View v) {
        SQLiteDatabase db1 = DBHelper.getDbHelper(this).openHelper.getWritableDatabase();

        Cursor cursor = db1.rawQuery("select id from test", null);
        String ids = "";
        while (cursor.moveToNext()) {
            ids += cursor.getInt(cursor.getColumnIndex("id"));
        }
        mPromptHelper.showToast(ids);
    }

    @OnClick(R.id.btn_permission)
    public void onClickPermission(View v) {
        EasyPermissions.requestPermissions(mActivity, "a", 0x003, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        startActivity(new Intent(mActivity, PanActivity.class));
    }

    @OnClick(R.id.btn_webview)
    public void onClickWeb(View v) {
        startActivity(new Intent(this, WebViewActivity.class));
    }

    @OnClick(R.id.btn_logan)
    public void onClickLogan(View v) {
        startActivity(new Intent(this, LoganActivity.class));
    }

    @OnClick(R.id.btn_camera)
    public void onClickCamera(View v) {
        if (!EasyPermissions.hasPermissions(mActivity, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(mActivity, "", 0x001, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return;
        }
        startActivity(new Intent(this, CameraActivity.class));
    }


    @OnClick(R.id.btn_camera2)
    public void onClickCamera2(View v) {
        if (!EasyPermissions.hasPermissions(mActivity, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(mActivity, "", 0x002, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return;
        }
        startActivity(new Intent(this, Camera2Activity.class));
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
                mPromptHelper.showToast("文件夹创建失败");
                return;
            }
        }
        if (file.exists()) {
            if (!file.delete()) {
                mPromptHelper.showToast("文件已存在，文件删除失败");
                return;
            }
        }
        boolean flag;
        try {
            flag = file.createNewFile();
            mPromptHelper.showToast("文件创建" + flag);
        } catch (IOException e) {
            e.printStackTrace();
            mPromptHelper.showToast("文件创建异常" + e.toString());
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


    @OnClick({R.id.btn_image, R.id.btn_glide, R.id.btn_picasso})
    public void onClickImage(View v) {
        int type = 1;
        switch (v.getId()) {
            case R.id.btn_image:
                type = 1;
                break;
            case R.id.btn_glide:
                type = 2;
                break;
            case R.id.btn_picasso:
                type = 3;
                break;
        }
        startActivity(ImageTestActivity.getIntent(mActivity, type));
    }


    @OnClick(R.id.btn_dialog)
    public void onClickDialog(View v) {
//        startActivity(new Intent(MainActivity.this, DialogActivity.class));
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            }
//        }, 5000);
        if (myBottomDialogFrag == null) {
            myBottomDialogFrag = new MyBottomDialogFrag();
        }
        mPromptHelper.showDialogFrag(myBottomDialogFrag, MyBottomDialogFrag.class.getSimpleName());
    }

    MyBottomDialogFrag myBottomDialogFrag;

    @BindView(R.id.iv_test)
    ImageView ivTest;

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        switch (requestCode) {
            case 0x001:
                onClickCamera(null);
                break;
            case 0x002:
                onClickCamera2(null);
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }


}

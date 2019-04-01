package com.nbank.study;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Printer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.nbank.study.camera.CameraActivity;
import com.nbank.study.camera2.Camera2Activity;
import com.nbank.study.cipher.SqlCipherActivity;
import com.nbank.study.logan.LoganActivity;
import com.nbank.study.tbs.TbsActivity;
import com.nbank.study.test.TestActivity;
import com.nbank.study.web.WebViewActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yitong.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
        getMainLooper().dump(new Printer() {

            @Override
            public void println(String x) {
                log.w(x);
            }
        }, "onCreate");

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

    @OnClick(R.id.btn_insert)
    public void onClickInsert(View v) {
        SQLiteDatabase db = DBHelper.getDbHelper(this).openHelper.getWritableDatabase();
        db.execSQL("INSERT INTO test(id) VALUES(" + System.currentTimeMillis() + ");");
        db.close();
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

    String url = "https://oimageb5.ydstatic.com/image?id=5057719474449425631&product=adpublish&w=640&h=480&sc=0&rm=2&gsb=0&gsbd=60";

    @OnClick(R.id.btn_download)
    public void onClickDown(View v) {
        Picasso.with(mActivity).load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                log.w("===onBitmapLoaded");
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                log.w("===onBitmapFailed");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                log.w("===onPrepareLoad");
            }
        });
    }

    @BindView(R.id.iv_test)
    ImageView ivTest;

    @OnClick(R.id.btn_load)
    public void onClickLoad(View v) {
        Picasso.with(mActivity).load(url).into(ivTest);
//        HttpResponseCache cache = HttpResponseCache.getInstalled();
//        try {
//            CacheResponse res = cache.get(URI.create(url), "GET", null);
//            Bitmap bitmap = BitmapFactory.decodeStream(res.getBody());
//            ivTest.setImageBitmap(bitmap);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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

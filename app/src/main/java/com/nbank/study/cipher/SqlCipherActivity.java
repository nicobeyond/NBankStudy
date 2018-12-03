package com.nbank.study.cipher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.nbank.study.R;
import com.nbank.study.tbs.TbsHelper;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ico.ico.ico.BaseFragActivity;
import pub.devrel.easypermissions.EasyPermissions;

public class SqlCipherActivity extends BaseFragActivity {

    final static int RC_P_DATABASE = 100;

    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_text1)
    TextView tvText1;
    @BindView(R.id.tv_text2)
    TextView tvText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_cipher);
        ButterKnife.bind(this);

        SQLiteDatabase.loadLibs(this);

        show();

    }

    SqlcipherOpenHelper sqlcipherOpenHelper;

    private void show() {
        sqlcipherOpenHelper = new SqlcipherOpenHelper(mActivity, "encrypt.db", null, 1);
        SQLiteDatabase database = sqlcipherOpenHelper.getWritableDatabase("icoxiaoshang");

        StringBuilder sb = new StringBuilder();
        Cursor cursor = database.query("book", new String[]{"name", "pages"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int pages = cursor.getInt(cursor.getColumnIndex("pages"));
            sb.append(name + "|" + pages);
            sb.append("\n");
        }
        tvText.setText(sb.toString());
    }

    //region 未加密数据库
    SqliteOpenHelper sqliteOpenHelper;

    @OnClick(R.id.btn_create)
    public void create() {
        sqliteOpenHelper = new SqliteOpenHelper(mActivity, "base.db", null, 1);

        StringBuilder sb = new StringBuilder();
        sb.append("数据库创建成功，数据库名称" + sqliteOpenHelper.getDatabaseName() + "\n");

        android.database.sqlite.SQLiteDatabase db = sqliteOpenHelper.getReadableDatabase();
        android.database.Cursor cursor = db.query("book", new String[]{"name", "pages"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int pages = cursor.getInt(cursor.getColumnIndex("pages"));
            sb.append(name + "|" + pages);
            sb.append("\n");
        }
        tvText1.setText(sb.toString());
    }
    //endregion

    //region 加密数据库
    SqliteEncryptOpenHelper sqliteEncryptOpenHelper;


    @OnClick(R.id.btn_encrypt)
    public void encrypt() {
        if (sqliteEncryptOpenHelper == null) {
            sqliteEncryptOpenHelper = new SqliteEncryptOpenHelper(mActivity, "base.db", null, 2, "xiaoshang");
        }

        try {
            StringBuilder sb = new StringBuilder();
            SQLiteDatabase db = sqliteEncryptOpenHelper.getWritableDatabase();
            Cursor cursor = db.query("book", new String[]{"name", "pages"}, null, null, null, null, null);
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                sb.append(name + "|" + pages);
                sb.append("\n");
            }
            tvText2.setText(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
            tvText2.setText(e.toString());
        }
    }
    //endregion

    //region 权限相关

    /**
     * 检查权限
     *
     * @return boolean 是否需要请求
     */
    private boolean checkPermission() {
        if (!EasyPermissions.hasPermissions(mActivity, TbsHelper.PERMISSIONS)) {
            EasyPermissions.requestPermissions(mActivity, "需要权限才可以读取本地文件哦", RC_P_DATABASE, TbsHelper.PERMISSIONS);
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
}

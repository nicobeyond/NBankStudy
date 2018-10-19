package com.nbank.study.cipher;

import android.content.Context;

import net.sqlcipher.DatabaseErrorHandler;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.io.IOException;

public class SqliteEncryptOpenHelper extends SQLiteOpenHelper {
    protected Context mContext;
    protected String mName;
    protected int mVersion;
    protected String mPassword;

    public SqliteEncryptOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, String password) {
        super(context, name, factory, version);
        init(context, name, version, password);
    }


    public SqliteEncryptOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, String password, SQLiteDatabaseHook hook) {
        super(context, name, factory, version, hook);
        init(context, name, version, password);
    }

    public SqliteEncryptOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, String password, SQLiteDatabaseHook hook, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, hook, errorHandler);
        init(context, name, version, password);
    }

    private void init(Context context, String name, int version, String password) {
        this.mContext = context;
        this.mName = name;
        this.mVersion = version;
        this.mPassword = password;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table book(name text, pages integer);");
        sqLiteDatabase.execSQL("insert into book(name,pages) values('SqliteEncryptOpenHelper onCreate',16);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("insert into book(name,pages) values('SqliteEncryptOpenHelper onUpgrade',11);");
    }

    public synchronized SQLiteDatabase getWritableDatabase() throws IOException {
        try {
            return super.getWritableDatabase(mPassword);
        } catch (Exception e) {
//            e.printStackTrace();
            //尝试加密后再打开
            try {
                encrypt();
                return super.getWritableDatabase(mPassword);
            } catch (IOException e1) {
                e1.printStackTrace();
                throw e1;
            }
        }
    }


    public synchronized SQLiteDatabase getReadableDatabase() throws IOException {
        try {
            return super.getReadableDatabase(mPassword);
        } catch (Exception e) {
//            e.printStackTrace();
            //尝试加密后再打开
            try {
                encrypt();
                return super.getReadableDatabase(mPassword);
            } catch (IOException e1) {
                e1.printStackTrace();
                throw e1;
            }
        }
    }

    private void encrypt() throws IOException {
        SqlcipherUtil.encrypt(mContext, mName, mPassword);
    }

}

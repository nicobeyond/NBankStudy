package com.nbank.study.cipher;

import android.content.Context;

import net.sqlcipher.DatabaseErrorHandler;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;
import net.sqlcipher.database.SQLiteOpenHelper;

public class SqlcipherOpenHelper extends SQLiteOpenHelper {
    public SqlcipherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SqlcipherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, SQLiteDatabaseHook hook) {
        super(context, name, factory, version, hook);
    }

    public SqlcipherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, SQLiteDatabaseHook hook, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, hook, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table book(name text, pages integer);");
        sqLiteDatabase.execSQL("insert into book(name,pages) values('SqlcipherOpenHelper onCreate',1);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

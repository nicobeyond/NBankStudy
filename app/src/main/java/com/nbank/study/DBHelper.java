package com.nbank.study;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.nbank.study.cipher.SqliteOpenHelper;

public class DBHelper {


    private static volatile DBHelper dbHelper;

    public MySqliteOpenHelper openHelper;

    public static DBHelper getDbHelper(Context context) {
        if (dbHelper == null) {
            synchronized (DBHelper.class) {
                if (dbHelper == null) {
                    dbHelper = new DBHelper(context);
                }
            }
        }
        return dbHelper;
    }

    private DBHelper(Context context) {
        openHelper = new MySqliteOpenHelper(context, "test", null, 1);
    }

    public class MySqliteOpenHelper extends SqliteOpenHelper {

        public MySqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            super.onCreate(sqLiteDatabase);
            String sql = "CREATE TABLE test(id INTEGER PRIMARY KEY)";
            sqLiteDatabase.execSQL(sql);
        }
    }
}

package com.page.a4.iot_app;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBManager extends SQLiteOpenHelper {

    public DBManager(Context context) {
        super(context, "myDB", null, 1);
    }

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table member(id text, password text, name text, gender text, post text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    /*
    public void insert(){
        SQLiteDatabase sqlitedb;
        sqlitedb = null;
        ContentValues values = new ContentValues();
        values.put("속성명", 속성값);

        long newRowId = sqlitedb.insert("테이블명", null, values);
    }*/

}

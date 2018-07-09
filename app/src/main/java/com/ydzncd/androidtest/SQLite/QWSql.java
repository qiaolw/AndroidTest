package com.ydzncd.androidtest.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QWSql extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "qwdbtest.db";
    private static final int FIRST_DATABASE_VERSION = 1;
    private static final int DATABASE_VERSION_NUM2 = 2;
    private static final int DATABASE_VERSION_NUM3 = 3;
    private static final int DATABASE_VERSION_NUM4 = 4;

    public QWSql(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION_NUM3);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("qob_db", "SQLiteOpenHelper onCreate");
        String sql = "create table user(id int primary key,name varchar(200))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("qob_db", "oldVersion: " + oldVersion + " newVersion: " + newVersion);
        for (int preVer = oldVersion + 1; preVer <= newVersion; ++preVer){
            switch (preVer){
                case DATABASE_VERSION_NUM2:{  //第二个版本修改表内容
                    break;
                }
                case DATABASE_VERSION_NUM3:{ //第三个版本修改表内容
                    break;
                }
                case DATABASE_VERSION_NUM4:{ //第四个版本修改表内容
                    break;
                }
            }
        }
    }
}

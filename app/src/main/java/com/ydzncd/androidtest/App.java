package com.ydzncd.androidtest;

import android.app.Application;

import com.ydzncd.androidtest.GreenDao.DaoMaster;
import com.ydzncd.androidtest.GreenDao.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by qiaoliwei on 2018/7/8.
 */

public class App extends Application{
    public static final boolean ENCRYPTED = true;
    private DaoSession mDaoSession;
    @Override
    public void onCreate() {
        super.onCreate();

        initGreenDb();
    }

    private void initGreenDb(){

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db");
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }
    public DaoSession getDaoSession(){
        return mDaoSession;
    }
}

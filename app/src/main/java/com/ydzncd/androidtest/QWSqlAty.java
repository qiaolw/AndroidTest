package com.ydzncd.androidtest;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.ydzncd.androidtest.GreenDao.Note;
import com.ydzncd.androidtest.GreenDao.NoteDao;
import com.ydzncd.androidtest.SQLite.QWSql;

import java.sql.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class QWSqlAty extends Activity {
    private QWSql mSqLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qwsql_aty);

        ButterKnife.bind(this);
        mSqLite = new QWSql(this);
        SQLiteDatabase tReadDb = mSqLite.getReadableDatabase();
        SQLiteDatabase tWriteDb = mSqLite.getWritableDatabase();
    }

    //GreenDao官方文档 http://greenrobot.org/greendao/documentation/
    //查看sqlite数据库  AS 右下角  Device File Explorer
    // Android字节码插桩 https://juejin.im/post/5aa0e7eff265da2395308f48

    @OnClick(R.id.sql_greendao_insert)
    public void onGreenDaoInsert(){
        Note tNote = new Note();
 //       tNote.setId(1000l);
        tNote.setText("qiaolw");
        tNote.setComment("greendao insert " + System.currentTimeMillis());

        try {
            ((App) getApplication()).getDaoSession().insert(tNote);
        }catch (Exception ex){
            ex.printStackTrace();
            ((App) getApplication()).getDaoSession().update(tNote);
        }
    }
    @OnClick(R.id.sql_greendao_Delete)
    public void onGreenDaoDeleteClick(){
        Note tNote = new Note();
        tNote.setId(1000l);
        ((App)getApplication()).getDaoSession().delete(tNote);
    }

    @OnClick(R.id.sql_greendao_update)
    public void onGreenDaoUpdateClick(){
        Note tNote = new Note();
        tNote.setId(1000l);
        tNote.setText("qiaolw update");
        ((App)getApplication()).getDaoSession().update(tNote);
    }
    @OnClick(R.id.sql_greendao_Queries)
    public void onGreenDaoQueriesClick(){
        NoteDao noteDao = ((App) getApplication()).getDaoSession().getNoteDao();
        List<Note> joes = noteDao.queryBuilder()
                .orderAsc(NoteDao.Properties.Id)
                .list();

        Log.e("qob", "List<Note> " + joes);
    }
}

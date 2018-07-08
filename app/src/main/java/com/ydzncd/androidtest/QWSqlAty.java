package com.ydzncd.androidtest;

import android.app.Activity;
import android.os.Bundle;

import com.ydzncd.androidtest.GreenDao.Note;

import java.sql.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class QWSqlAty extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qwsql_aty);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.sql_greendao_insert)
    public void onGreenDaoInsert(){
        Note tNote = new Note();
        tNote.setId(1000l);
        tNote.setText("qiaolw");
        tNote.setComment("greendao insert " + System.currentTimeMillis());

        ((App)getApplication()).getDaoSession().insert(tNote);
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
}

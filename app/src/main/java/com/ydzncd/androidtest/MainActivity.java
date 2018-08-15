package com.ydzncd.androidtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ydzncd.androidtest.Adapter.QWMainAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;

public class MainActivity extends Activity {

    @BindView(R.id.rv_main) RecyclerView rvMain;
    private QWMainAdapter mMainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        rvMain.setLayoutManager(new LinearLayoutManager(this));
        mMainAdapter = new QWMainAdapter(this);
        rvMain.setAdapter(mMainAdapter);
    }

    //第三方平台  Strava  googlefit
}

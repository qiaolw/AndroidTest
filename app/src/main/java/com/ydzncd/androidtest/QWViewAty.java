package com.ydzncd.androidtest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QWViewAty extends Activity {
    @BindView(R.id.view_progressbar)
    ProgressBar mProgressBar;
    private int mProgressIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qwview_aty);

        ButterKnife.bind(this);

        mProgressIndex = 0;
        mProgressBar.setProgress(mProgressIndex);
    }

    @OnClick(R.id.view_changeProgress)
    public void onChangeProgress(){
        mProgressIndex+=10;
        mProgressBar.setProgress(mProgressIndex);
        if (mProgressIndex > 99){
            mProgressIndex = 0;
        }
    }
}

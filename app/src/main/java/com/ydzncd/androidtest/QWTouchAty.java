package com.ydzncd.androidtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 参考博客
 * https://blog.csdn.net/carson_ho/article/details/54136311
 */

public class QWTouchAty extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qwtouch_aty);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("qob", "dispatchTouchEvent " + ev.toString());
        return super.dispatchTouchEvent(ev);
    }
}

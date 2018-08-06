package com.ydzncd.androidtest;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 参考博客
 * https://blog.csdn.net/carson_ho/article/details/54136311
 */

public class QWTouchAty extends Activity {
    @BindView(R.id.iv_FrameAnimation_01)
    ImageView mFrameAnimIv;
    private AnimationDrawable animationDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qwtouch_aty);

        ButterKnife.bind(this);

        animationDrawable = (AnimationDrawable)mFrameAnimIv.getBackground();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("qob", "dispatchTouchEvent " + ev.toString());
        return super.dispatchTouchEvent(ev);
    }

    @OnClick(R.id.bt_start_anim)
    public void onStartAnimClick(){
        if (animationDrawable != null && !animationDrawable.isRunning()){
            animationDrawable.start();
        }
    }
    @OnClick(R.id.bt_stop_anim)
    public void onStopAnimClick(){
        if (animationDrawable != null && animationDrawable.isRunning()){
            animationDrawable.stop();
        }
    }
}

package com.ydzncd.androidtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

import com.ydzncd.androidtest.MyView.ClipOutlineProvider;
import com.ydzncd.androidtest.MyView.QWDrawView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QWViewAty extends Activity {
    @BindView(R.id.view_progressbar) ProgressBar mProgressBar;
    @BindView(R.id.view_drawtest)
    QWDrawView mCustomDrawView;
    private int mProgressIndex;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_qwview_aty);

        ButterKnife.bind(this);

        mProgressIndex = 0;
        mProgressBar.setProgress(mProgressIndex);

 //       requestWindowFeature(Window.FEATURE_PROGRESS);   //设置窗口进度条特性风格
        setProgressBarIndeterminateVisibility(true);     //设置进度条可见性

        ClipOutlineProvider tColP = new ClipOutlineProvider();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mCustomDrawView.setOutlineProvider(tColP);
        }
    }

    @OnClick(R.id.view_changeProgress)
    public void onChangeProgress(){
        mProgressIndex+=10;
        mProgressBar.setProgress(mProgressIndex);
        if (mProgressIndex > 99){
            mProgressIndex = 0;
        }
    }
    @OnClick(R.id.view_progressDialog)
    public void onProgressDialogClick(){
        showProgressDialog("测试");
    }

    public void showProgressDialog(String text) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //转圈加载框
  //          mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//横向进度条
        }
        mProgressDialog.setMessage(text);    //设置内容
        mProgressDialog.setCancelable(true);//点击屏幕和按返回键都不能取消加载框
        mProgressDialog.show();
    }

    public Boolean dismissProgressDialog() {
        if (mProgressDialog != null){
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
                return true;//取消成功
            }
        }
        return false;//已经取消过了，不需要取消
    }
}

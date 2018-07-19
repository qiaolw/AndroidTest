package com.ydzncd.androidtest.MyView;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class QWDrawView extends View {
    private Paint mDstPaint;
    private Paint mSrcPaint;

    public QWDrawView(Context context) {
        super(context);
        Log.e("qob", "QWDrawView 1");
    }

    public QWDrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        Log.e("qob", "QWDrawView 2");
        mDstPaint = new Paint();
        mSrcPaint = new Paint();

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public QWDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e("qob", "QWDrawView 3");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public QWDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Log.e("qob", "QWDrawView 4");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("qob", "onDraw");


        mDstPaint.setAntiAlias(true);
        mDstPaint.setStyle(Paint.Style.FILL);
        mDstPaint.setColor(getResources().getColor(android.R.color.holo_blue_light));
        Bitmap tBig = Bitmap.createBitmap(500,200, Bitmap.Config.ARGB_8888);
        tBig.eraseColor(getResources().getColor(android.R.color.holo_blue_light));//填充颜色
        canvas.drawBitmap(tBig, 0, 0, mDstPaint);


        Bitmap tSmail = Bitmap.createBitmap(500,200, Bitmap.Config.ARGB_8888);
        tSmail.eraseColor(getResources().getColor(android.R.color.holo_red_dark));//填充颜色

        mSrcPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(tSmail, 50, 0, mSrcPaint);
    }
}

package com.ydzncd.androidtest.MyView;

import android.annotation.TargetApi;
import android.content.Context;
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

    public QWDrawView(Context context) {
        super(context);
    }

    public QWDrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public QWDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public QWDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("qob", "onDraw");

        Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(android.R.color.holo_blue_light));

        canvas.drawRect(0, 0, 500, 200, paint);

        paint.setColor(getResources().getColor(android.R.color.holo_red_dark));

        canvas.drawRect(0,0,500,100,paint);

        paint.setColor(getResources().getColor(android.R.color.background_dark));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10f);
        Path path = new Path();
//屏幕左上角（0,0）到（200,400）画一条直线
        path.moveTo(0, 0);
//(200, 400)到（400,600）画一条直线
        path.lineTo(400, 200);
       paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        canvas.drawPath(path, paint);
    }
}

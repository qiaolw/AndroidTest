package com.ydzncd.androidtest.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ydzncd.androidtest.R;

public class QWDrawClipView extends View {
    private Paint mPaint;
    public QWDrawClipView(Context context) {
        super(context);
    }

    public QWDrawClipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
    }

    public QWDrawClipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        tLinePath.lineTo(150, 120);
//        tLinePath.lineTo(50, 120);
//        tLinePath.lineTo(50, 50);
        //       canvas.clipRect(50, 50, 400, 120);
//        canvas.clipPath(tLinePath);

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(android.R.color.holo_blue_light));
        canvas.drawRect(0,0,500,100, mPaint);

        mPaint.setColor(getResources().getColor(android.R.color.holo_red_light));
        canvas.drawRect(0,100,500,200, mPaint);

        mPaint.setColor(getResources().getColor(R.color.white));
 //       canvas.drawRect(0,0,600,300, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2f);
        Path tLinePath = new Path();
        tLinePath.addRect(0, 0,600,300, Path.Direction.CCW);
        tLinePath.moveTo(50, 50);
        tLinePath.cubicTo(50, 50,150, 120, 400, 150);
 //       tLinePath.lineTo(150, 120);
        tLinePath.setFillType(Path.FillType.INVERSE_WINDING);
        canvas.drawPath(tLinePath, mPaint);


 //       canvas.clipRect(50, 50, 400, 120);
    }
}

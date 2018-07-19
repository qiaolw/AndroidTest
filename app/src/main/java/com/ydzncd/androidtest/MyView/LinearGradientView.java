package com.ydzncd.androidtest.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class LinearGradientView extends View {
    private LinearGradient linearGradient = null;
    private Paint paint = null;

    public LinearGradientView(Context context) {
        super(context);
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        linearGradient = new LinearGradient(0, 0, 0, 300, new int[] {Color.RED,Color.GREEN, Color.BLUE}, new float[]{0.3f, 0.5f, 0.7f},
                Shader.TileMode.CLAMP);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.e("qob", "width: " + canvas.getWidth() + " height " + canvas.getHeight());

        //设置渲染器
        paint.setAntiAlias(true);
        paint.setShader(linearGradient);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);
        paint.setColor(getResources().getColor(android.R.color.holo_blue_light));//填充颜色

        //绘制圆环
        Path tLinePath = new Path();
        tLinePath.moveTo(50, 0);
        tLinePath.cubicTo(50,0,150,300,500, 200);
        tLinePath.cubicTo(500,200,600,0,700, 200);
        canvas.drawPath(tLinePath, paint);
//        canvas.drawRect(0, 0, 500, 200, paint);
    }
}

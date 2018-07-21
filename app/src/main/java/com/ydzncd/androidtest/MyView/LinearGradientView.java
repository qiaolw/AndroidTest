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

        linearGradient = new LinearGradient(0, 0, 0, 300, new int[] {0xFFF55111, 0xFFFFCA0A, 0xFF5EC1FF, 0xFFC8C8C8}, new float[]{0.0f, 0.5f, 0.7f, 1.0f},
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
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5f);
//        paint.setColor(getResources().getColor(android.R.color.holo_blue_light));//填充颜色

       canvas.drawRect(0, 0, 500, 300, paint);
//        canvas.drawRect(0, 0, 500, 200, paint);
    }
}

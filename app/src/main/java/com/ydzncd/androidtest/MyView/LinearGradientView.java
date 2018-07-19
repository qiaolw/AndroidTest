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
import android.view.View;

public class LinearGradientView extends View {
    private LinearGradient linearGradient = null;
    private Paint paint = null;

    public LinearGradientView(Context context) {
        super(context);
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        linearGradient = new LinearGradient(0, 0, 100, 100, new int[] {Color.RED, Color.WHITE, Color.BLUE}, new float[]{0, 300,500},
                Shader.TileMode.CLAMP);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //设置渲染器
        paint.setShader(linearGradient);
        //绘制圆环
//        Path tLinePath = new Path();
//        tLinePath.moveTo(50, 50);
//        tLinePath.lineTo(400, 150);
//        tLinePath.close();
//        canvas.drawPath(tLinePath, paint);
        canvas.drawRect(0, 0, 500, 200, paint);
    }
}

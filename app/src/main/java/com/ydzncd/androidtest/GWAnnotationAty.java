package com.ydzncd.androidtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.ydzncd.androidtest.Annotation.MyAnnotation1;
import com.ydzncd.androidtest.Annotation.MyEntity1;

import java.lang.reflect.Method;

/**
 *自定义gradle插件，处理Annotaion https://blog.csdn.net/a568478312/article/details/8022508
 */

public class GWAnnotationAty extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gwannotation_aty);

        trackMyEntity(MyEntity1.class);
    }

    private void trackMyEntity(Class<?> cl){
        for (Method m : cl.getDeclaredMethods()){
            MyAnnotation1 mA = m.getAnnotation(MyAnnotation1.class);
            if (mA != null){
                Log.e("qob", "Found MyAnnotation1 tag: " + mA.tag() + " des " + mA.desc());
            }
            else {
                Log.e("qob", "Annotation is NUll method: " + m);
            }
        }
    }
}

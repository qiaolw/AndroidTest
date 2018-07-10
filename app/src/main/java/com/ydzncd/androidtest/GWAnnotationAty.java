package com.ydzncd.androidtest;

import android.app.Activity;
import android.os.Bundle;

import com.ydzncd.androidtest.Annotation.MyEntity1;
/**
 *自定义gradle插件，处理Annotaion https://blog.csdn.net/a568478312/article/details/8022508
 */

public class GWAnnotationAty extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gwannotation_aty);

        MyEntity1 tEntity1 = new MyEntity1();
        tEntity1.sayHello();
    }
}

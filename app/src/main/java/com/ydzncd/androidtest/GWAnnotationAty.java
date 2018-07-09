package com.ydzncd.androidtest;

import android.app.Activity;
import android.os.Bundle;

import com.ydzncd.androidtest.Annotation.MyEntity1;

public class GWAnnotationAty extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gwannotation_aty);

        MyEntity1 tEntity1 = new MyEntity1();
        tEntity1.sayHello();
    }
}

package com.ydzncd.androidtest.Annotation;

public class MyEntity1 {
    private int mId;

    @MyAnnotation1(tag = 10, desc = "sayHello")
    public void sayHello(){
        System.out.println("MyEntity1 syaHello!");
    }
}

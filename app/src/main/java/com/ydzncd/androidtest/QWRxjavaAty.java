package com.ydzncd.androidtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;


public class QWRxjavaAty extends Activity {
    private static String TAG = "QWRxjavaAty";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qwrxjava_aty);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_rxjava_test)
    public void rxjavaTest(){
        Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                Log.e(TAG, "Observable emit 1" + "\n");

                e.onNext(1);
                Log.e(TAG, "Observable emit 2" + "\n");
                e.onNext(2);
                Log.e(TAG, "Observable emit 3" + "\n");
                e.onNext(3);
                e.onComplete();
                Log.e(TAG, "Observable emit 4" + "\n" );
                e.onNext(4);
            }
        }).subscribe(new Observer<Object>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe");
            }

            @Override
            public void onNext(Object o) {
                Log.e(TAG, "onNext " + o);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }
        });

        hello("abc", "bbbc", "qob", "jow");
    }

    private void hello(String... names){
        Observable.fromArray(names).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("qob", "Hello " + s);
            }
        });
    }

    @OnClick(R.id.bt_rxjava_map)
    public void onRxjavaMapTestClick(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "maptest " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "accept " + s);
            }
        });
    }
    @OnClick(R.id.bt_rxjava_flatMap)
    public void onRxjavaFlatMapTestClick(){

    }
    @OnClick(R.id.bt_rxjava_Flowable)
    public void onRxjavaFlowableClick(){
        Flowable.just("Hello world").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("qob", "accept " + s);
            }
        });

        Flowable<Integer> flow = Flowable.range(1, 6).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) throws Exception {
                return integer*integer;
            }
        }).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return (integer % 3 == 0);
            }
        });

        flow.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("qob", "accept " + integer);
            }
        });
}

    private void readme(){
        //http://gank.io/post/560e15be2dca930e00da1083#toc_2
        //官方中文资料: https://mcxiaoke.gitbooks.io/rxdocs/content/Intro.html
    }
}

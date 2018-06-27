package com.ydzncd.androidtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;


public class QWRxjavaAty extends Activity {
    private static String TAG = "QWRxjavaAty";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qwrxjava_aty);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_rxjava_test)
    public void onRxjavaCreatingObservablesClick(){
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
    public void onRxjavaFilteringObservablesClick(){
        //Debounce — only emit an item from an Observable if a particular timespan has passed without it emitting another item
        //去抖 - 如果特定的时间间隔已经过去而没有发射另一个物品，则仅从Observable发射物品
        Observable.just(1,2,3,4).debounce(1, TimeUnit.SECONDS);

        //Distinct — suppress duplicate items emitted by an Observable
        //独特 - 压制Observable发出的重复项目

        //ElementAt — emit only item n emitted by an Observable

        //Filter — emit only those items from an Observable that pass a predicate test

        //First — emit only the first item, or the first item that meets a condition, from an Observable
        // 从Observable只发出第一个项目或满足条件的第一个项目

        //IgnoreElements — do not emit any items from an Observable but mirror its termination notification
        //IgnoreElements - 不会从Observable发出任何项目，但会镜像其终止通知
        Flowable.just(1,2,3,4).ignoreElements().subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });

        //Last — emit only the last item emitted by an Observable

        //Sample — emit the most recent item emitted by an Observable within periodic time intervals
        //在周期性时间间隔内发出由Observable发出的最新项目


        //Skip — suppress the first n items emitted by an Observable

        //Take — emit only the first n items emitted by an Observable
        Observable.just(1,2,3,4).take(2).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("qob", "take " + integer);
            }
        });
    }

    /**
     * 数学和集合运算符
     */
    @OnClick(R.id.bt_rxjava_flatMap)
    public void onRxjavaMathematicalAggregateOperatorsClick(){
        //Concat — emit the emissions from two or more Observables without interleaving them
        //从两个或多个Obserbles发射排放而不交叉它们
        Observable.concat(Observable.just(1,2), Observable.just(4,5));

        //Reduce — apply a function to each item emitted by an Observable, sequentially, and emit the final value
        //按顺序对由Observable发出的每个项目应用函数，并发出最终值
        Observable.just(1,2,3).reduce(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                return null;
            }
        });
    }

    @OnClick(R.id.bt_rxjava_concatMap)
    public void onRxjavaSingleClick(){
        Single.just(1).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("qob", "Single " + integer);
            }
        });
        Single.create(new SingleOnSubscribe<Integer>() {

            @Override
            public void subscribe(SingleEmitter<Integer> e) throws Exception {
  //              e.onSuccess(100);
                e.onError(new Throwable("Single error"));
            }
        }).subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {
                Log.e("qob", "Single onSuccess" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("qob", "Single onError" + e);
            }
        });
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

    @OnClick(R.id.bt_rxjava_zip)
    public void onRxjavaCombiningObservablesClick(){

        //Zip — combine the emissions of multiple Observables together via a specified function and emit single items for each combination based on the results of this function
        Observable.zip(Observable.just(1, 2, 3,7), Observable.just(4, 5, 6), new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("qob", "zip " + integer);
            }
        });

        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onComplete();
                e.onNext(2);
                Log.e("qob", "zip observable1 emitter");

            }
        });

        Observable<Integer> observable2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(4);
                e.onNext(5);
                Log.e("qob", "zip observable2 emitter");

            }
        });

        Observable.zip(observable1, observable2, new BiFunction<Integer, Integer, Integer>() {

            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        }).subscribe(new Consumer<Object>() {

            @Override
            public void accept(Object o) throws Exception {
                Log.e("qob", "zip Consumer " + o);
            }
        });
    }

    @OnClick(R.id.bt_rxjava_concat)
    public void onRxjavaBackpressureOperatorsClick(){
        Observable.concat(Observable.just(1,2,3), Observable.just(4,5,6))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("qob", "Concat " + integer);
                    }
                });

        Observable.concat(Observable.just(1, 2, 3), Observable.just(5, 6 ,7))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("qob", "concat 1 " + integer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("qob", "ThrowAble " + throwable);
                    }
                });

        Observable observable1 = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                e.onNext(1);
                Log.e("qob", "observable1 1");
                e.onNext(2);
                Log.e("qob", "observable1 2");

         //       e.onError(new Throwable("aaaaa"));
                e.onComplete();  // observable1 一定要调用onComlete()后才会 遍历observable2
            }
        });

        Observable observable2 = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                e.onNext(4);
                Log.e("qob", "observable2 4");
                e.onNext(5);
                Log.e("qob", "observable2 5");
            }
        });

        Observable.concat(observable1, observable2).timeInterval()
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.e("qob", "consumer " + o);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("qob", "Throwable " + throwable);
                    }
                });


    }

    @OnClick(R.id.bt_rxjava_time)
    public void onRxjavaTimeClick(){
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.e("qob", "observable emitter");
                e.onNext(1000);
            }
        }).delay(10, TimeUnit.SECONDS)
                .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("qob", "delay " + integer);
            }
        });

        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.e("qob", "observable emitter");
                e.onNext(1000);
            }
        }).timeInterval().subscribe(new Consumer<Timed<Integer>>() {
            @Override
            public void accept(Timed<Integer> integerTimed) throws Exception {
                Log.v(TAG, "accept:"+integerTimed);
            }
        });

        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                Thread.sleep(3000);
                e.onNext(2);
            }
        }).timeout(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("qob", "onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e("qob", "onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("qob", "onError " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.e("qob", "onComplete");
                    }
                });

        Observable.interval(200, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.e("qob", "interval accept " + aLong);
            }
        });
    }

    @OnClick(R.id.bt_rxjava_transforming)
    public void onTransformObservalbleClick(){
        //buffer 定期将Observable中的物品收集到捆绑包中并发出这些捆绑包，而不是一次只发放一个物品
        Observable.just(1,2).buffer(2).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                Log.e("qob", "TransformObservalble " + integers);
            }
        });
        Observable.just(1,2, 3, 4).buffer(2, 3).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                Log.e("qob", "TransformObservalble " + integers);
            }
        });

        //FlatMap 将Observable发射的物体转换为Observables，然后将这些物体的辐射平坦化为单个Observable
        Flowable.just(5,10).flatMap(new Function<Integer, Publisher<Integer>>() {
            @Override
            public Publisher<Integer> apply(Integer integer) throws Exception {
                if (integer == 5){
                    List tResut = new ArrayList();
                    tResut.add(1);
                    tResut.add(2);
                    tResut.add(3);
                    tResut.add(4);
                    return Flowable.fromIterable(tResut);
                }
                else {
                    List tResut = new ArrayList();
                    tResut.add(6);
                    tResut.add(7);
                    tResut.add(8);
                    tResut.add(9);
                    return Flowable.fromIterable(tResut);
                }
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("qob", "flatMap " + integer);
            }
        });

        Flowable.just(1, 2).flatMap(new Function<Integer, Publisher<Integer>>() {
            @Override
            public Publisher<Integer> apply(Integer integer) throws Exception {
                if (integer == 1){
                    Integer aaa[] = {1,2};
                    return Flowable.fromArray(aaa);
                }
                else {

                    return Flowable.just(7,8);
                }
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("qob", "flatMap2 " + integer);
            }
        });

        //GroupBy 将一个Observable分成一组Observable，每个Observable从原始Observable发出不同组的项目，按键组织
        Observable.just(1,2,3,4).groupBy(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                if (integer % 2 == 0){
                    return "aaaa";
                }
                return "bbbb";
            }
        }).subscribe(new Consumer<GroupedObservable<String, Integer>>() {
            @Override
            public void accept(final GroupedObservable<String, Integer> stringIntegerGroupedObservable) throws Exception {
                stringIntegerGroupedObservable.subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("qob", "groupBy " + integer + " key: " + stringIntegerGroupedObservable.getKey());
                    }
                });
            }
        });

        //Map 通过对每个项目应用函数来转换Observable发出的项目

        //Scan 对Observable发出的每个项目按顺序应用一个函数，然后逐个发送
        Observable.just(1, 2, 3).scan(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("qob", "Scan " + integer);
            }
        });

        //Window 定期将Observable中的项目细分为Observable窗口并发出这些窗口，而不是一次发送一个项目
    }



    private void readme(){
        //http://gank.io/post/560e15be2dca930e00da1083#toc_2
        //官方中文资料: https://mcxiaoke.gitbooks.io/rxdocs/content/Intro.html
    }
}

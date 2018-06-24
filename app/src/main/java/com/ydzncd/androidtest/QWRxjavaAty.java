package com.ydzncd.androidtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
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
        ArrayList<String[]> list=new ArrayList<>();
        String[] words1={"Hello,","I am","China!"};
        String[] words2={"Hello,","I am","Beijing!"};
        list.add(words1);
        list.add(words2);

        Flowable.fromIterable(list)
                .flatMap(new Function<String[], Publisher<String>>() {
                    @Override
                    public Publisher<String> apply(String[] strings) throws Exception {
                        return Flowable.fromArray(strings);
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("consumer", s.toString());
                    }
                });

    }

    @OnClick(R.id.bt_rxjava_concatMap)
    public void onRxjavaConcatMapClick(){

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
    public void onRxjavaZipClick(){
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
    public void onRxjavaConcatClick(){
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



    private void readme(){
        //http://gank.io/post/560e15be2dca930e00da1083#toc_2
        //官方中文资料: https://mcxiaoke.gitbooks.io/rxdocs/content/Intro.html
    }
}

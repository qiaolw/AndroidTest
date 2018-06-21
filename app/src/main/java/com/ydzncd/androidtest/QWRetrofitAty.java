package com.ydzncd.androidtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.ydzncd.androidtest.Retrofit.AppUpdateService;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class QWRetrofitAty extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qwretrofit_aty);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_retrofit_test)
    public void onRetrofitTestButtonClicked(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://18.218.84.54/")
                .build();
        AppUpdateService appUpdateService = retrofit.create(AppUpdateService.class);
        Call<ResponseBody> call = null;//appUpdateService.getUpdateInfo("1", "zh", "1.0.9", "1.1.27.0.0");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.e("qob", "onResponse " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("qob", "onFailure " + t);

            }
        });
    }

    @OnClick(R.id.bt_retrofitwithrxjava_test)
    public void onRetrofitWithRxjavaClick(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://18.218.84.54/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        AppUpdateService appUpdateService = retrofit.create(AppUpdateService.class);
        appUpdateService.getAppInfoWithRxjava("1", "en", "1.0.9", "1.1.27.0.0")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            Log.e("qob", "onNext " + responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void readme(){
        //https://www.jianshu.com/p/308f3c54abdd
        // http://square.github.io/retrofit/
        //https://www.jianshu.com/p/cdde02a0777c
    }
}

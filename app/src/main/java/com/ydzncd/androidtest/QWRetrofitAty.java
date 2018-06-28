package com.ydzncd.androidtest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.ydzncd.androidtest.Retrofit.AppUpdateService;
import com.ydzncd.androidtest.Retrofit.HeModel;
import com.ydzncd.androidtest.Retrofit.HeWeatherService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
        Call<ResponseBody> call = appUpdateService.getUpdateInfo("1", "zh", "1.0.9", "1.1.27.0.0");
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
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        Log.e("qob", "responseBody " + responseBody);
                    }
                });
    }

    @OnClick(R.id.bt_weather_test)
    public void onWeatherTestClick(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://free-api.heweather.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HeWeatherService appWeatherService = retrofit.create(HeWeatherService.class);
        Call<HeModel> call = appWeatherService.getHeWeatherInfo("22.57108146,113.86268045", "10a8b0f3908f44f2901c0db30b7af083", "zh", "m");
        call.enqueue(new Callback<HeModel>() {
            @Override
            public void onResponse(Call<HeModel> call, Response<HeModel> response) {
                Log.e("qob", "response body " + response.body() + " response " + response);
            }

            @Override
            public void onFailure(Call<HeModel> call, Throwable t) {

            }
        });
    }

    private String mOtaSaveFilePath;
    @OnClick(R.id.bt_ota_start)
    public void onOtaStartUpdateClick()
    {
        String netVer = "1.1.28.0.0";
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://18.218.84.54/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        AppUpdateService appUpdateService = retrofit.create(AppUpdateService.class);
        Observable<ResponseBody> downloadOtaObser = appUpdateService.getOtaPackage();

        Observable<Boolean> findExistFileObser = Observable.just(netVer)
                .map(new Function<String, String>() {
            @Override
            public String apply(String netV) throws Exception {
                String otaFilePathBase = Environment.getExternalStorageDirectory() + "/yuedongTest";
                File yuedongDir = new File(otaFilePathBase);
                if (!yuedongDir.exists()){
                    yuedongDir.mkdir();
                }
                String otaFilePath = otaFilePathBase + "/YD_device(V" + netV + ").img";
                mOtaSaveFilePath = otaFilePath;

                return otaFilePath;
            }
        }).flatMap(new Function<String, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(String s) throws Exception {
                        File otaFile = new File(s);
                        Log.e("qob", "otaFile: " + s);
                        if (otaFile.exists()){
                            Log.e("qob", "本地存在OTA文件,不需要下载");
                            return Observable.create(new ObservableOnSubscribe<Boolean>() {
                                @Override
                                public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                                    e.onNext(true);
                                }
                            });
                        }

                        return Observable.create(new ObservableOnSubscribe<Boolean>() {
                            @Override
                            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                                Log.e("qob", "本地不存在OTA文件,需要下载");
                                e.onComplete();
                            }
                        });
                    }
                });

        Observable.concat(findExistFileObser, downloadOtaObser)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Object>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                Log.e("qob", "ota onNext " + o.getClass().toString());
                if (o instanceof Boolean){

                }
                else {
                    writeResponseBodyToDisk((ResponseBody) o);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.e("qob", "ota onComplete ");
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(mOtaSaveFilePath);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    private void readme(){
        //https://www.jianshu.com/p/308f3c54abdd
        // http://square.github.io/retrofit/
        //https://www.jianshu.com/p/cdde02a0777c
        //http://reactivex.io/documentation/operators.html
    }
}

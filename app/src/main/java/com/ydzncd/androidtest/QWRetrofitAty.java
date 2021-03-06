package com.ydzncd.androidtest;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ProgressBar;

import com.ydzncd.androidtest.Retrofit.AppUpdateService;
import com.ydzncd.androidtest.Retrofit.FileDownloadInterceptor;
import com.ydzncd.androidtest.Retrofit.FileDownloadListener;
import com.ydzncd.androidtest.Retrofit.HeModel;
import com.ydzncd.androidtest.Retrofit.HeWeatherService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
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
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class QWRetrofitAty extends Activity {

    @BindView(R.id.ota_progress) ProgressBar mOtaProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qwretrofit_aty);

        ButterKnife.bind(this);

        if (checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
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
        FileDownloadInterceptor mInterceptor = new FileDownloadInterceptor(new FileDownloadListener() {
            @Override
            public void onStartDownload() {
                Log.e("qob", "onStartDownload");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mOtaProgressBar.setProgress(0);
                    }
                });
            }

            @Override
            public void onProgress(final int progress) {
                Log.e("qob", "onProgress " + progress + " Thread " + Thread.currentThread().getName());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mOtaProgressBar.setProgress(progress);
                    }
                });
            }

            @Override
            public void onFinishDownload() {
                Log.e("qob", "onFinishDownload");
            }

            @Override
            public void onFail(String errorInfo) {
                Log.e("qob", "onFail");
            }
        });

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(mInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        String netVer = "1.1.28.0.0";
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://18.218.84.54/")
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        AppUpdateService appUpdateService = retrofit.create(AppUpdateService.class);
        Observable<Boolean> downloadOtaObser = appUpdateService.getOtaPackage()
                .map(new Function<ResponseBody, Boolean>() {
            @Override
            public Boolean apply(ResponseBody responseBody) throws Exception {
                Log.e("qob", "network download file, save to file");
                writeResponseBodyToDisk(responseBody);

                return true;
            }
        });

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
                            return Observable.just(true);
                        }
                        Log.e("qob", "本地不存在OTA文件, 需要下载");
                        return Observable.empty();
                    }
                });

        Observable.concat(findExistFileObser, downloadOtaObser)
                .take(1)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                Log.e("qob", "ota onNext " + o.getClass().toString());
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

    @OnClick(R.id.bt_file_upload)
    public void onFileUploadClick(){
        Map<String, RequestBody> params = new HashMap<>();

        Map<String, String> params1 = new HashMap<>();
        params1.put("nickName", "String你好你好吧");
        params1.put("birthYear", "1999");

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), " Map<String, RequestBody>你好你好吧");
        params.put("nickName", requestBody);
        RequestBody requestBody11 = RequestBody.create(MediaType.parse("text/plain"), "2000");
        params.put("birthYear", requestBody11);

        RequestBody formBody = new FormBody.Builder(Charset.defaultCharset())
                .add("nickName", "formBody你好你好吧")
                .add("sex", "1")
                .add("birthYear", "1990")
                .add("weight", "70")
                .add("height", "179")
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://18.218.84.54/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        //temp1529891334414.jpg 公司手机
        //temp1530352862434.jpg
        String tOtaFilePathBase = Environment.getExternalStorageDirectory() + "/yuedongTest/temp1529891334414.jpg";
        File headImageFile = new File(tOtaFilePathBase);

        //构建body
        RequestBody reqBody = new MultipartBody.Builder().setType(MultipartBody.MIXED)
                .addPart(formBody)
                .addFormDataPart("file", headImageFile.getName(), RequestBody.create(MediaType.parse("image/*"), headImageFile))
                .build();

        AppUpdateService tLoginService = retrofit.create(AppUpdateService.class);

        RequestBody tHeadIconBody = RequestBody.create(MediaType.parse("multipart/form-data"), headImageFile);
        MultipartBody.Part headIcon = MultipartBody.Part.createFormData("headIcon", headImageFile.getName(), tHeadIconBody);
        params.put("headIcon\";filename=\"" + headImageFile.getName(), tHeadIconBody);

        String tToken = "b0126153d58b4cbfe843f69cdfdf02bb";
        String tUserId = "321";

        Observable<ResponseBody> appsetUser1 = tLoginService.appSetUserInfoWithHeadImage(params, tToken, tUserId);
        Observable<ResponseBody> appsetUser2 = tLoginService.appSetUserInfoWithHeadImage111(formBody, headIcon,tToken, tUserId);
        Observable<ResponseBody> appsetUser22 = tLoginService.appSetUserInfoWithHeadImage1111(reqBody,tToken, tUserId);
        Observable<ResponseBody> appsetUser3 = tLoginService.appSetUserInfoWithHeadImage222(params1, headIcon,tToken, tUserId);
        Observable<ResponseBody> appsetUser4 = tLoginService.appSetUserInfo(params1, tToken, "321");
        Observable<ResponseBody> appsetUser5 = tLoginService.appSetUserInfo222("Field测试测试中文", tToken, tUserId);
        Observable<ResponseBody> appsetUser6 = tLoginService.appSetUserInfo333("GET Query测试测试中文", tToken, tUserId);
        try {
            String tEncodeParams = URLEncoder.encode("GET Queryæµè¯æµè¯ä¸­æ", "utf-8");
            Log.e("qob", "tEncodeParams " + tEncodeParams + " " + new String("GET Queryæµè¯æµè¯ä¸­æ".getBytes("iso-8859-1"), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ArrayList tAppSettingReqs = new ArrayList();
        Collections.addAll(tAppSettingReqs, appsetUser1, appsetUser2, appsetUser22, appsetUser3, appsetUser4, appsetUser5, appsetUser6);

        Observable.concat(tAppSettingReqs).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(new Consumer<ResponseBody>() {
            @Override
            public void accept(ResponseBody o) throws Exception {
                Log.e("qob", "appSetUserInfo" + o.string());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("qob", "throwable" + throwable);
            }
        });

        //https://blog.csdn.net/sinat_30822393/article/details/75529910
        //https://blog.csdn.net/jdsjlzx/article/details/52301505
        //Retrofit 图文上传
        //error
        //1. @FieldMap parameters can only be used with form encoding
    }

    @OnClick(R.id.bt_starwrist_test)
    public void onStarWristTestClick(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://18.218.84.54/")
                .build();
        HeWeatherService appUpdateService = retrofit.create(HeWeatherService.class);
        String tUserName = "qiaolw0709@foxmail.com";
        String tPwd = "123456";
        String tMd5 = "99999";
        Call<ResponseBody> call = appUpdateService.testStartWristApi(tUserName, tPwd, tMd5);
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

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(mOtaSaveFilePath);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[1024];

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

package com.ydzncd.androidtest.Retrofit;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;

public interface  AppUpdateService {
    @GET("YueDongService/app/getAppInfo.do?product=device")
    Observable<ResponseBody> getAppInfoWithRxjava(@Query("customerId") String customerId, @Query("language") String language,
                                                  @Query("appVersion") String appVersion, @Query("deviceVersion") String deviceVersion);

    @GET("YueDongService/app/getAppInfo.do?product=device")
    Call<ResponseBody> getUpdateInfo(@Query("customerId") String customerId, @Query("language") String language,
                                     @Query("appVersion") String appVersion, @Query("deviceVersion") String deviceVersion);

    //"http://18.218.84.54/k6File/appFile/device/1/K6_watch_device.img"
    @Streaming
    @GET("k6File/appFile/device/1/K6_watch_device.img")
    Observable<ResponseBody> getOtaPackage();

    @Multipart
    @POST("YueDongService/user/setUserInfo.do?")
    Observable<ResponseBody> appSetUserInfoWithHeadImage(@QueryMap Map<String, String> param,
                                                     @Part MultipartBody.Part headIcon,
                                                     @Header("charset") String charset,
                                                     @Header("token") String token,
                                                     @Header("userId") String userId);

}

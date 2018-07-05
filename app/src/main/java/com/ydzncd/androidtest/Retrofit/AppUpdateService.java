package com.ydzncd.androidtest.Retrofit;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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

    //图文上传，中文不乱码
    @Multipart
    @POST("YueDongService/user/setUserInfo.do?")
    Observable<ResponseBody> appSetUserInfoWithHeadImage(@PartMap Map<String, RequestBody> param,
                                                     @Header("token") String token,
                                                     @Header("userId") String userId);

    // Error @Part annotation must supply a name or use MultipartBody.Part parameter type.
    // TODO 怎么把多个参数一起放在 RequestBody里使用, FormBody里面参数已有key; Body怎么不用传key.
    @Multipart
    @POST("YueDongService/user/setUserInfo.do?")
    Observable<ResponseBody> appSetUserInfoWithHeadImage111(@Part("xxxxx") RequestBody param,
                                                            @Part MultipartBody.Part headIcon,
                                                         @Header("token") String token,
                                                         @Header("userId") String userId);

    //ERROR  @Body parameters cannot be used with form or multi-part encoding.
    //@Multipart
    @POST("YueDongService/user/setUserInfo.do?")
    Observable<ResponseBody> appSetUserInfoWithHeadImage1111(@Body RequestBody param,
                                                            @Header("token") String token,
                                                            @Header("userId") String userId);

    //图文上传，中文乱码
    @Multipart
    @POST("YueDongService/user/setUserInfo.do?")
    Observable<ResponseBody> appSetUserInfoWithHeadImage222(@QueryMap Map<String, String> param,
                                                            @Part MultipartBody.Part headIcon,
                                                         @Header("token") String token,
                                                         @Header("userId") String userId);
    //纯文字上传
    //FieldMap必需与 FormUrlEncoded 一起使用 Error @FieldMap parameters can only be used with form encoding
    //Form-encoded method must contain at least one @Field
    @FormUrlEncoded
    @POST("YueDongService/user/setUserInfo.do?")
    Observable<ResponseBody> appSetUserInfo(@FieldMap Map<String, String> param,
                                                         @Header("token") String token,
                                                         @Header("userId") String userId);

    // Error @Field parameters can only be used with form encoding.
    @FormUrlEncoded
    @POST("YueDongService/user/setUserInfo.do?")
    Observable<ResponseBody> appSetUserInfo222(@Field("nickName") String nickName,
                                            @Header("token") String token,
                                            @Header("userId") String userId);

    //乱码
    //TODO GET方式下Query 怎么防止中文乱码
    @GET("YueDongService/user/setUserInfo.do?")
    Observable<ResponseBody> appSetUserInfo333(@Query("nickName") String nickName,
                                               @Header("token") String token,
                                               @Header("userId") String userId);


}

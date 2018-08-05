package com.ydzncd.androidtest.Retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HeWeatherService {
    @GET("s6/weather/now?parameters")
    Call<HeModel> getHeWeatherInfo(@Query("location") String location, @Query("key") String key,
                                        @Query("lang") String lang, @Query("unit") String unit);

    @POST("abc")
    Call<ResponseBody> testStartWristApi(@Query("userName") String userName, @Query("password") String pwd, @Query("md5") String md5);
}

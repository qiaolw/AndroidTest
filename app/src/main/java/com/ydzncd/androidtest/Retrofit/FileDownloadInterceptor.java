package com.ydzncd.androidtest.Retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class FileDownloadInterceptor implements Interceptor {
    private FileDownloadListener downloadListener;

    public FileDownloadInterceptor(FileDownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder().body(
                new FileResponseBody(response.body(), downloadListener)).build();
    }
}

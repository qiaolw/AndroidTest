package com.ydzncd.androidtest.Retrofit;

public interface FileDownloadListener {
    void onStartDownload();
    void onProgress(int progress);
    void onFinishDownload();
    void onFail(String errorInfo);
}

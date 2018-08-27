package com.ydzncd.androidtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.ydzncd.androidtest.IO.TcpConnection;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class QWIOAty extends Activity implements TcpConnection.TCPCallback {
    private TcpConnection mClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qwioaty);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_socket)
    public void onSocketClicked(){
        mClient = new TcpConnection(this);
        mClient.connect();
    }

    @Override
    public void onDisconnected(TcpConnection tcpConn) {
        Log.e("qob", "onDisconnected");
    }

    @Override
    public void onConnected(TcpConnection tcpConn) {
        Log.e("qob", "onConnected");
        Gson gson = new Gson();

        Map map = new LinkedHashMap();
        map.put("cmd", "02");
        map.put("sid", "xxx");
        Map tData = new HashMap();
        tData.put("UserName", "lrs");
        tData.put("Pwd", "123456");
        map.put("data", tData);
        String tGsonStr = gson.toJson(map);
        Log.e("qob", "tGsonStr " + tGsonStr);


        try {
 //           Thread.sleep(1000);
            mClient.write(tGsonStr.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReceiveData(TcpConnection tcpConn, byte[] data) {
        String tStr = new String(data);
        Log.e("qob", "onReceiveData: " + tStr);
    }

    @Override
    public void onWriteFailed(TcpConnection tcpConn) {
        Log.e("qob", "onWriteFailed");
    }

    @Override
    public void onWriteSuccess(TcpConnection tcpConn) {
        Log.e("qob", "onWriteSuccess");
    }
}

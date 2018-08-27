package com.ydzncd.androidtest.IO;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/8/3.
 */

public class TcpConnection {

    public interface TCPCallback {

        void onDisconnected(TcpConnection tcpConn);

        void onConnected(TcpConnection tcpConn);

        void onReceiveData(TcpConnection tcpConn, byte[] data);

        void onWriteFailed(TcpConnection tcpConn);

        void onWriteSuccess(TcpConnection tcpConn);
    }

    private WorkThread mWorkThread;
    private TCPCallback mCallback;

    public TcpConnection(TCPCallback callback) {
        mCallback = callback;
    }

    public boolean write(byte[] out) {
        WorkThread t;
        // 创建临时的TransThread, 使写数据的操作, 不在同步块中
        synchronized (this) {
            if (mWorkThread == null || mWorkThread.mOutStream == null) {
                mCallback.onWriteFailed(TcpConnection.this);
                return false;
            }
            t = mWorkThread;
        }
        return t.write(out);
    }

    public synchronized boolean connect() {

        String tHost = "www.honggps.com";
        int tPort = 8882;

        Log.e("qob", "开始连接 Host：" + tHost + " Port " + tPort);
        if (false) {  //判断网络是否OK
            disconnectAndCallback();
            return false;
        }

        disconnect();
        mWorkThread = new WorkThread();
        mWorkThread.start();

        return true;
    }

    public synchronized void disconnect() {
        if (mWorkThread != null) {
            mWorkThread.cancel();
            mWorkThread.mOutStream = null;
            mWorkThread = null;
        }
    }

    private void disconnectAndCallback() {
        disconnect();
        mCallback.onDisconnected(this);
    }

    private class WorkThread extends Thread {

        private boolean needdisconnect = false;

        private boolean isConnecting = false;

        private Socket mSocket;
        private InputStream mInStream;
        private OutputStream mOutStream;
        public WorkThread() {
            super(WorkThread.class.getSimpleName());
        }
        @Override
        public void run() {
            // 连接
            try {
                if (needdisconnect) {
                    this.cancel();
                    return;
                }
                isConnecting = true;
                mSocket = new Socket("106.14.11.194", 8882);
                //           mSocket.connect();
                Log.e("qob", "连接完成");
                isConnecting = false;
                if (needdisconnect) {
                    Log.e("qob", "连接成功后，需要断开");
                    this.cancel();
                    return;
                }
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
                isConnecting = false;
                disconnectAndCallback();
                Log.e("qob", "Socket连接异常:" + e.toString());
                return;
            }
            finally {

            }
            // 获取读写流
            try {
                mInStream = mSocket.getInputStream();
                mOutStream = mSocket.getOutputStream();
                Log.e("qob", "流获取成功");
            } catch (IOException e) {
                Log.e("qob", "流获取失败");
                e.printStackTrace();
                disconnectAndCallback();
                return;
            }
            // 连接成功
            mCallback.onConnected(TcpConnection.this);

            // 循环读取数据
            byte[] buffer = new byte[2048];
            while (true) try {
                int length = mInStream.read(buffer);
                if (length < 0){
                    break;
                }
                byte[] bytes = new byte[length];
                System.arraycopy(buffer, 0, bytes, 0, length);
                mCallback.onReceiveData(TcpConnection.this, bytes);
            } catch (IOException e) {
                e.printStackTrace();
                disconnectAndCallback();
                return;
            }
        }

        public boolean write(byte[] bytes) {
            try {
                mOutStream.write(bytes);
                mOutStream.flush();
                mCallback.onWriteSuccess(TcpConnection.this);
                Log.d("qob", "onWriteSuccess: " + Arrays.toString(bytes));
            } catch (IOException e) {
                e.printStackTrace();
                mCallback.onWriteFailed(TcpConnection.this);
                return false;
            }
            return true;
        }

        public void cancel() {
            needdisconnect = true;
            try {
                if (mSocket != null) {
                    mSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.interrupt();// 唤醒sleep
        }
    }
}

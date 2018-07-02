package com.ydzncd.androidtest;

import android.app.Activity;
import android.os.Bundle;

import com.polidea.rxandroidble2.RxBleClient;
import com.polidea.rxandroidble2.scan.ScanResult;
import com.polidea.rxandroidble2.scan.ScanSettings;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class GWRxBleAty extends Activity {

    private RxBleClient mRxBleClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gwrx_ble_aty);

        ButterKnife.bind(this);

        mRxBleClient = RxBleClient.create(this);
    }

    @OnClick(R.id.ble_scan)
    public void onBleScanClick(){
        Disposable scanSubscription = mRxBleClient.scanBleDevices(
                new ScanSettings.Builder()
                        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY) // change if needed
                        // .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES) // change if needed
                        .build()
                // add filters if needed
        ).subscribe(new Consumer<ScanResult>() {
            @Override
            public void accept(ScanResult scanResult) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });

    }
}

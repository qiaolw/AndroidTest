package com.ydzncd.androidtest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QWLocationAty extends Activity {

    @BindView(R.id.tv_location)
    TextView mLocatinTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qwlocation_aty);

        ButterKnife.bind(this);

        if (checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                checkCallingOrSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                Log.e("qob", "onRequestPermissionsResult " + permissions + " grantResults " + grantResults);
            }
            default:
                break;
        }
    }

    @OnClick(R.id.bt_location_test)
    public void onLocationTestClick() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && checkCallingOrSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                Log.e("qob", "location lat: " + location.getLatitude() + " lon: " + location.getLongitude());
                mLocatinTv.setText("lat: " + location.getLatitude() + " lon: " + location.getLongitude());
            }
            else {
                Log.e("qob", "location == null");
            }
        }
        else {
            Log.e("qob", "无定位权限");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        }
    }
    @OnClick(R.id.bt_location_gps_test)
    public void onLocationGpsClick() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String provider;
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            // 当没有可用的位置提供器时，弹出Toast提示用户
            Toast.makeText(this, "No location provider to use", Toast.LENGTH_SHORT).show();
            return;
        }
        if (checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && checkCallingOrSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                // 显示当前设备的位置信息
                Log.e("qob", "location lat: " + location.getLatitude() + " lon: " + location.getLongitude());
                mLocatinTv.setText("lat: " + location.getLatitude() + " lon: " + location.getLongitude());
            }
            else {
                Log.e("qob", "location == null");
            }
        }
        else {
            Log.e("qob", "无定位权限");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

            }
        }

        locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e("qob", "onStatusChanged " + provider + " status " + status + " extras " + extras);
        }
        @Override
        public void onProviderEnabled(String provider) {
            Log.e("qob", "onProviderEnabled " + provider);
        }
        @Override
        public void onProviderDisabled(String provider) {
            Log.e("qob", "onProviderDisabled " + provider);
        }
        @Override
        public void onLocationChanged(Location location) {
            // 更新当前设备的位置信息
            Log.e("qob", "onLocationChanged lat: " + location.getLatitude() + " lon: " + location.getLongitude());
            mLocatinTv.setText("lat: " + location.getLatitude() + " lon: " + location.getLongitude());
        }
    };
}

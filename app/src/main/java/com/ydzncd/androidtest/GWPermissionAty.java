package com.ydzncd.androidtest;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.util.Log;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class GWPermissionAty extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gwpermission);

        ButterKnife.bind(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @OnClick(R.id.bt_reqpermission)
    public void reqPermissionOnClick(){
        Log.e("qob", "checkCallingOrSelfPermission " + checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_STATE));
        if (checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.PROCESS_OUTGOING_CALLS}, 2);
        }
    }

    @OnClick(R.id.bt_enterappsetting)
    public void enterAppSetting(){
        //https://blog.csdn.net/donkor_/article/details/79374442
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    @OnClick(R.id.bt_enterNtfOpenSetting)
    public void onEnterNtfOpenSettingClicked(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//android 8.0引导
        if(Build.VERSION.SDK_INT >= 26){
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
        }
//android 5.0-7.0
        if(Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT < 26) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", getPackageName());
            intent.putExtra("app_uid", getApplicationInfo().uid);
        }
//其他
        if(Build.VERSION.SDK_INT < 21){
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", getPackageName(), null));
        }
        startActivity(intent);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("qob", "权限成功");
                }
                else {
                    Log.e("qob", "权限禁止");
                }
                break;
            }

        }
    }
}

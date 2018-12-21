package com.product.nearme.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

public class AppConstantsM {
    private static final int PERMISSION_REQUEST_CODE = 1;
    public static boolean isMarshmallow() {
        if (Build.VERSION.SDK_INT >= 23) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isMarshmallowGETACCOUNTS(Context context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isMarshmallownWRITE_EXTERNAL_STORAGE(Context context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isMarshmallownCALL_PHONE(Context context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isMarshmallownREAD_EXTERNAL_STORAGE(Context context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isMarshmallownPHONE_STATE(Context context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isMarshmallownCAMERA(Context context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isMarshmallownACCESS_FINE_LOCATION(Context context) {
        int ACCESS_FINE_LOCATION = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        int ACCESS_COARSE_LOCATION = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (ACCESS_FINE_LOCATION == PackageManager.PERMISSION_GRANTED && ACCESS_COARSE_LOCATION == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isMarshmallownSMS(Context context) {
        int sms = ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS);
        if (sms == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }
}


package com.product.nearme.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class constant {
    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim().length() <= 0);
    }
    public static boolean haveCameraPermissions(Activity caller) {
        int permissionCheck = ContextCompat.checkSelfPermission(caller,
                android.Manifest.permission.CAMERA);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        return false;
    }

    public static boolean writePermissions(Activity caller) {
        int permissionCheck = ContextCompat.checkSelfPermission(caller,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        return false;
    }

    public static boolean haveLocationPermissions(FragmentActivity activity) {
        int permissionCheck = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        return false;
    }

    public static void requestLocationPermission(Activity caller) {
        List<String> permissionList = new ArrayList<String>();

        if (ContextCompat.checkSelfPermission(caller, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);

        }

        if (permissionList.size() > 0) {
            String[] permissionArray = new String[permissionList.size()];

            for (int i = 0; i < permissionList.size(); i++) {
                permissionArray[i] = permissionList.get(i);
            }

            ActivityCompat.requestPermissions(caller,
                    permissionArray,
                    100);
        }
    }

    public static void requestwritePermission(Activity caller) {
        List<String> permissionList = new ArrayList<String>();

        if (ContextCompat.checkSelfPermission(caller, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        }

        if (permissionList.size() > 0) {
            String[] permissionArray = new String[permissionList.size()];

            for (int i = 0; i < permissionList.size(); i++) {
                permissionArray[i] = permissionList.get(i);
            }

            ActivityCompat.requestPermissions(caller,
                    permissionArray,
                    100);
        }
    }
    public static void requestCameraPermission(Activity caller) {
        List<String> permissionList = new ArrayList<String>();

        if (ContextCompat.checkSelfPermission(caller, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);

        }

        if (permissionList.size() > 0) {
            String[] permissionArray = new String[permissionList.size()];

            for (int i = 0; i < permissionList.size(); i++) {
                permissionArray[i] = permissionList.get(i);
            }

            ActivityCompat.requestPermissions(caller,
                    permissionArray,
                    100);
        }

    }

    public static final boolean isValidEmail(CharSequence target) {
        if (target == null || TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

}

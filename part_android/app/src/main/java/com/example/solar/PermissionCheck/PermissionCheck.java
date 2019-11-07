//package com.example.solar.PermissionCheck;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.pm.PackageManager;
//import android.os.Build;
//
//import androidx.core.app.ActivityCompat;
//
//public class PermissionCheck {
//    public PermissionCheck() {
//    }
//
//    public void getPerimission(Activity activity) {
//        // 권한 요청을 해야되는 버전에서만 실행
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            // checkSelfPermission returns PackageManager.PERMISSION_GRANTED or PackageManager.PERMISSION_DENIED
//            if (ActivityCompat.checkSelfPermission(
//                    activity, Manifest.permission.ACCESS_COARSE_LOCATION) // low battery, approximate location
//                    != PackageManager.PERMISSION_GRANTED
//
//                    || ActivityCompat.checkSelfPermission(activity,
//                    Manifest.permission.ACCESS_FINE_LOCATION) // gps + nework, precise location
//                    != PackageManager.PERMISSION_GRANTED) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(
//                        activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
//                    ActivityCompat.requestPermissions(activity, new String[]
//                            {Manifest.permission.ACCESS_FINE_LOCATION}, 101);
//                } else {
//                    // Log.e("LocaPermission", "getLocationPermission already set");
//                }
//            }
//        }
//
//    }
//}

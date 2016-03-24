package com.srikanth.sharkfeed.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by srikanth on 3/23/16.
 */
public class PermissionUtil {

    private static final String TAG = PermissionUtil.class.getSimpleName();

    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkIfPermissionsAlreadyGranted(Context context, String[] permissions) {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String perm : permissions) {
            if (ActivityCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, perm + " " + "not granted");
                return false;
            }
        }
        return true;
    }
}

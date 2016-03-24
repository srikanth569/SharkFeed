package com.srikanth.sharkfeed.util;

import android.os.Environment;

/**
 * Created by srikanth on 3/23/16.
 */
public class StorageUtils {
    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}

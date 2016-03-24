package com.srikanth.sharkfeed.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

public class StorageUtils {
    private static final String SHARED_PREFERENCES = "shared_pref";
    private static final String PAGE_LOAD = "page_load";

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static void WriteToSharedPrefrences(Context context, int page_number) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(PAGE_LOAD, page_number);
        editor.apply();
    }

    public static int readFromSharedPreferences(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return prefs.getInt(PAGE_LOAD, -1);
    }

}

package com.srikanth.sharkfeed.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;

import com.facebook.stetho.server.http.HttpStatus;
import com.srikanth.sharkfeed.bus.DownloadComplete;
import com.srikanth.sharkfeed.util.StorageUtils;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import de.greenrobot.event.EventBus;

/**
 * Created by srikanth on 3/23/16.
 */
public class DownloadImageService extends IntentService {

    private static final String EXTRA_URL = "url";
    private static final String TAG = DownloadImageService.class.getSimpleName();
    private boolean downloadResult = false;

    public DownloadImageService() {
        super(DownloadImageService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle bundle = intent.getExtras();
        String url = bundle.getString(EXTRA_URL);
        if (url != null) {
            Log.v(TAG, "The External memory is readable " + StorageUtils.isExternalStorageWritable());
            if (StorageUtils.isExternalStorageWritable()) {
                File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES), "SharkFeed");
                if (!file.mkdirs()) {
                    Log.e(TAG, "Directory not created");
                }

            }
            Bitmap bitmap = downloadBitmap(url);
            if (bitmap != null) {
                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Test", "Testing");
            } else {
                downloadResult = false;
            }
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new DownloadComplete(downloadResult));
            }
        });
    }

    private Bitmap downloadBitmap(String url) {
        HttpURLConnection urlConnection = null;
        try {
            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();

            int statusCode = urlConnection.getResponseCode();
            if (statusCode != HttpStatus.HTTP_OK) {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                downloadResult = true;
                return BitmapFactory.decodeStream(inputStream);
            }
        } catch (Exception e) {
            Log.d("Testing", e.toString());
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            Log.w("Testing", "Error downloading image from " + url);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();

            }
        }
        return null;
    }
}

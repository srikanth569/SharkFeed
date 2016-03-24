package com.srikanth.sharkfeed.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.srikanth.sharkfeed.bus.RefreshCompleteEvent;
import com.srikanth.sharkfeed.data.SharkFeedContentProvider;
import com.srikanth.sharkfeed.model.FlickrPhotosFeed;
import com.srikanth.sharkfeed.model.Photo;
import com.srikanth.sharkfeed.util.APIUtils;

import java.io.IOException;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ImageFeedService : Handles the responsibility of retrieving json from the API
 */
public class ImageFeedService extends IntentService {

    private final static String EXTRA_PAGE_NUMBER = "extra_page_number";
    // The default search term for this app is Shark,
    // but leaving it configurable in case we want to change it
    private String tagTerm = "shark";
    // By default we want to get the first page, unless otherwise specified
    private int page_number = 1;
    private final int per_page = 99;

    public ImageFeedService() {
        super(ImageFeedService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            page_number = bundle.getInt(EXTRA_PAGE_NUMBER);
            page_number = page_number == 0 ? 1 : page_number;
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(APIUtils.buildUrlToQuery(tagTerm, page_number))
                .build();
        Call call = client.newCall(request);
        Response response;
        FlickrPhotosFeed photosFeed;
        try {
            response = call.execute();
            Log.v("Testing", "response received");
            photosFeed = FlickrPhotosFeed.parseJson(response.body().string());
            if (photosFeed != null) {
                ContentValues[] values = new ContentValues[per_page];
                for (int i = 0; i < per_page; i++) {
                    Photo photo = photosFeed.getPhotos().getPhoto().get(i);
                    values[i] = photo.getContentValues();
                }
                // Received json, write it to DB
                getContentResolver().bulkInsert(
                        SharkFeedContentProvider.getTableUri(Photo.TABLE_NAME),
                        values);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("Testing", "IO exception");
        } catch (Exception e) {
            Log.v("Testing", "DB Exception ");
            e.printStackTrace();
        }
        notifyCompletion();
    }

    private void notifyCompletion() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new RefreshCompleteEvent());
            }
        });
    }
}

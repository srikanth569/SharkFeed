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
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ImageFeedService : Handles the responsibility of retrieving json from the API
 */
public class ImageFeedService extends IntentService {

    private final static String TAG = ImageFeedService.class.getSimpleName();
    private final static String EXTRA_PAGE_NUMBER = "extra_page_number";
    private static final String EXTRA_SEARCH_TAG = "extra_search_tag";
    // The default search term for this app is Shark,
    // but leaving it configurable in case we want to change it
    private String tagTerm = "shark";
    // By default we want to get the first page, unless otherwise specified
    private int page_number = 1;
    private final int per_page = 99;
    private int previously_loaded_page = 0;

    public ImageFeedService() {
        super(ImageFeedService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            page_number = bundle.getInt(EXTRA_PAGE_NUMBER);
            page_number = page_number == 0 ? 1 : page_number;
            page_number = page_number == previously_loaded_page ? page_number + 1 : page_number;
            tagTerm = bundle.getString(EXTRA_SEARCH_TAG);
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
            photosFeed = FlickrPhotosFeed.parseJson(response.body().string());
            Log.v(TAG, "response received from API");
            if (photosFeed != null) {
                List<ContentValues> contentValues = new ArrayList<>();
                for (int i = 0; i < per_page; i++) {
                    Photo photo = photosFeed.getPhotos().getPhoto().get(i);
                    if (photo.getUrlC() != null && photo.getUrlL() != null) {
                        contentValues.add(photo.getContentValues());
                    }
                }

                ContentValues[] values = contentValues.toArray(new ContentValues[contentValues.size()]);
                // Received json, write it to DB
                getContentResolver().bulkInsert(
                        SharkFeedContentProvider.getTableUri(Photo.TABLE_NAME), values);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(TAG, "Caught IO exception while fetching photo feed");
        } catch (Exception e) {
            Log.v(TAG, "Caught unexpected exception while fetching photo feed ");
            e.printStackTrace();
        }
        previously_loaded_page = page_number;
        notifyCompletion();
    }

    /**
     * Notifies the main activity that service has finished loading
     * - This enables refresh layout loading to be terminated
     * - Runs on the main thread
     */
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

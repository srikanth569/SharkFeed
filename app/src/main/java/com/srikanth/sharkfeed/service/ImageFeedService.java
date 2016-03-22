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

import java.io.IOException;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by srikanth on 3/19/16.
 */
public class ImageFeedService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    private final static String EXTRA_PAGE_NUMBER = "extra_page_number";

    private final static String BASE_URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search";
    private final static String API_KEY = "api_key=949e98778755d1982f537d56236bbb42";
    // Tags with which the images are tagged
    private final static String TAG = "tags=";
    private final static String FORMAT = "format=json&nojsoncallback=1";
    private final static String PAGE = "page=";
    // Using a 3 collum span, this makes sure the last row is fully filled
    private final static String PER_PAGE = "per_page=99";
    // This makes sure we get only images
    private final static String CONTENT_TYPE = "content_type=1";
    private final static String EXTRAS = "extras=url_c,url_l,url_o,url_sq";
    private final static String SEPARATOR = "&";

    // By default we want to get the first page, unless otherwise specified
    private int page_number = 1;
    private final int per_page = 99;
    // The default search term for this app is Shark,
    // but leaving it configurable in case we want to change it
    private String tagTerm = "shark";

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
                .url(buildUrlToQuery())
                .build();
        Call call = client.newCall(request);
        Response response = null;
        FlickrPhotosFeed photosFeed = null;
        try {
            response = call.execute();
            Log.v("Testing", "response recived");
            photosFeed = FlickrPhotosFeed.parseJson(response.body().string());
            ContentValues[] values = new ContentValues[per_page];
            for (int i = 0; i < per_page
                    ; i++) {
                Photo photo = photosFeed.getPhotos().getPhoto().get(i);
                values[i] = photo.getContentValues();
            }
            try {
                getContentResolver().bulkInsert(
                        SharkFeedContentProvider.getTableUri(Photo.TABLE_NAME),
                        values);
            } catch (Exception e) {
                Log.v("Testing", "DB Exception ");
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("Testing", "IO exception");
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new RefreshCompleteEvent());
            }
        });

    }

    private String buildUrlToQuery() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(BASE_URL);
        buffer.append(SEPARATOR);
        buffer.append(API_KEY);
        buffer.append(SEPARATOR);
        buffer.append(TAG);
        buffer.append(tagTerm);
        buffer.append(SEPARATOR);
        buffer.append(FORMAT);
        buffer.append(SEPARATOR);
        buffer.append(PAGE);
        buffer.append(page_number);
        buffer.append(SEPARATOR);
        buffer.append(PER_PAGE);
        buffer.append(SEPARATOR);
        buffer.append(CONTENT_TYPE);
        buffer.append(SEPARATOR);
        buffer.append(EXTRAS);
        return buffer.toString();
    }
}

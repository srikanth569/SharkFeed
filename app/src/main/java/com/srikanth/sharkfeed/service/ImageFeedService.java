package com.srikanth.sharkfeed.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.srikanth.sharkfeed.data.SharkFeedContentProvider;
import com.srikanth.sharkfeed.model.FlickrPhotosFeed;
import com.srikanth.sharkfeed.model.Photo;

import java.io.IOException;

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
    public ImageFeedService() {
        super(ImageFeedService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v("Testing", "came to load in background");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=949e98778755d1982f537d56236bbb42&tags=shark&format=json&nojsoncallback=1&page=1&extras=url_c,url_l,url_o,url_sq")
                .build();
        Log.v("Testing", "built network request");
        Call call = client.newCall(request);
        Response response = null;
        FlickrPhotosFeed photosFeed = null;
        try {
            response = call.execute();
            Log.v("Testing", "response recived");
            photosFeed = FlickrPhotosFeed.parseJson(response.body().string());
            for (Photo photo : photosFeed.getPhotos().getPhoto()) {
                try {
                    getContentResolver().insert(
                            SharkFeedContentProvider.getTableUri(Photo.TABLE_NAME),
                            photo.getContentValues()
                    );
                } catch (Exception e) {
                    Log.v("Testing", "DB Exception ");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("Testing", "IO exception");
        }
    }
}

package com.srikanth.sharkfeed.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.srikanth.sharkfeed.model.FlickrPhotosFeed;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by srikanth on 3/18/16.
 */
public class ImageFeedLoader extends AsyncTaskLoader<FlickrPhotosFeed> {

    public ImageFeedLoader(Context context) {
        super(context);
        Log.v("Testing", "creating image loader 12");
    }

    @Override
    public FlickrPhotosFeed loadInBackground() {
        Log.v("Testing", "came to load in background");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=949e98778755d1982f537d56236bbb42&tags=shark&format=json&nojsoncallback=1&page=1&extras =url_t,url_c,url_l,url_o")
                .build();
        Log.v("Testing", "built network request");
        Call call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute();
            Log.v("Testing", "response recived");
            FlickrPhotosFeed photosFeed = FlickrPhotosFeed.parseJson(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("Testing", "IO exception");
        }
        return null;
    }
}

package com.srikanth.sharkfeed.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by srikanth on 3/18/16.
 */

public class FlickrPhotosFeed {

    private Photos photos ;
    private String stat;


    public static FlickrPhotosFeed parseJson(String response) {
        Log.v("Testing","The response is "+response);
        Gson gson = new GsonBuilder().create();
        FlickrPhotosFeed photosFeed = gson.fromJson(response, FlickrPhotosFeed.class);
        Log.v("Testing","The stat is "+photosFeed.getPhotos().getPhoto().get(0).getUrlL());
        return photosFeed;
    }


    /**
     * @return The photos
     */
    public Photos getPhotos() {
        return photos;
    }

    /**
     * @param photos The photos
     */
    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    /**
     * @return The stat
     */
    public String getStat() {
        return stat;
    }

    /**
     * @param stat The stat
     */
    public void setStat(String stat) {
        this.stat = stat;
    }
}
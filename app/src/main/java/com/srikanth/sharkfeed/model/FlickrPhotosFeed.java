package com.srikanth.sharkfeed.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by srikanth on 3/18/16.
 */

public class FlickrPhotosFeed {

    private Photos photos;
    private String stat;


    public static FlickrPhotosFeed parseJson(String response) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, FlickrPhotosFeed.class);
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
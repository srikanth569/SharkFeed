package com.srikanth.sharkfeed.model;

/**
 * Created by srikanth on 3/18/16.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;

public class Photo implements BaseColumns {

    private final static String ID = "id";
    private final static String OWNER = "owner";
    private final static String SECRET = "secret";
    private final static String SERVER = "server";
    private final static String FARM = "farm";
    private final static String TITLE = "title";
    private final static String URLC = "url_c";
    private final static String URLL = "url_l";
    private final static String URLO = "url_o";
    private final static String URLSQ = "url_sq";

    private String id;
    private String owner;
    private String secret;
    private String server;
    private Integer farm;
    private String title;
    @SerializedName("url_c")
    private String urlC;
    @SerializedName("url_l")
    private String urlL;
    @SerializedName("url_o")
    private String urlO;
    @SerializedName("url_sq")
    private String urlSq;

    public static final String TABLE_NAME = "PhotoFeed";
    public static final String TABLE_STRUCTURE = "CREATE TABLE " + TABLE_NAME + " ( "
            + ID + " VARCHAR NOT NULL UNIQUE,"
            + OWNER + " VARCHAR,"
            + SECRET + " VARCHAR,"
            + SERVER + " VARCHAR,"
            + FARM + " INTEGER,"
            + TITLE + " VARCHAR,"
            + URLC + " VARCHAR,"
            + URLL + " VARCHAR,"
            + URLO + " INTEGER,"
            + URLSQ + " VARCHAR"
            + ")";

    public ContentValues getContentValues() {
        final ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(OWNER, owner);
        values.put(SECRET, secret);
        values.put(FARM, farm);
        values.put(TITLE, title);
        values.put(URLC, urlC);
        values.put(URLL, urlL);
        values.put(URLO, urlO);
        values.put(URLSQ, urlSq);
        return values;
    }

    public Photo inflateFromCursor(Cursor cr) {
        id = cr.getString(cr.getColumnIndex(ID));
        owner = cr.getString(cr.getColumnIndex(OWNER));
        secret = cr.getString(cr.getColumnIndex(SECRET));
        farm = cr.getInt(cr.getColumnIndex(FARM));
        title = cr.getString(cr.getColumnIndex(TITLE));
        urlC = cr.getString(cr.getColumnIndex(URLC));
        urlL = cr.getString(cr.getColumnIndex(URLL));
        urlO = cr.getString(cr.getColumnIndex(URLO));
        urlSq = cr.getString(cr.getColumnIndex(URLSQ));
        return this;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner The owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return The secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * @param secret The secret
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * @return The server
     */
    public String getServer() {
        return server;
    }

    /**
     * @param server The server
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * @return The farm
     */
    public Integer getFarm() {
        return farm;
    }

    /**
     * @param farm The farm
     */
    public void setFarm(Integer farm) {
        this.farm = farm;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The urlC
     */
    public String getUrlC() {
        return urlC;
    }

    /**
     * @param urlC The url_c
     */
    public void setUrlC(String urlC) {
        this.urlC = urlC;
    }

    /**
     * @return The urlL
     */
    public String getUrlL() {
        return urlL;
    }

    /**
     * @param urlL The url_l
     */
    public void setUrlL(String urlL) {
        this.urlL = urlL;
    }

    /**
     * @return The urlO
     */
    public String getUrlO() {
        return urlO;
    }

    /**
     * @param urlO The url_o
     */
    public void setUrlO(String urlO) {
        this.urlO = urlO;
    }

    public String getUrlSq() {
        return urlSq;
    }

    public void setUrlSq(String urlSq) {
        this.urlSq = urlSq;
    }
}
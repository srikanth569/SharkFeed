package com.srikanth.sharkfeed.model;

/**
 * Created by srikanth on 3/18/16.
 */

import com.google.gson.annotations.SerializedName;

public class Photo {

    private String id;
    private String owner;
    private String secret;
    private String server;
    private Integer farm;
    private String title;
    private Integer ispublic;
    private Integer isfriend;
    private Integer isfamily;
    @SerializedName("url_t")
    private String urlT;
    @SerializedName("height_t")
    private String heightT;
    @SerializedName("width_t")
    private String widthT;
    @SerializedName("url_c")
    private String urlC;
    @SerializedName("height_c")
    private Integer heightC;
    @SerializedName("width_c")
    private String widthC;
    @SerializedName("url_l")
    private String urlL;
    @SerializedName("height_l")
    private String heightL;
    @SerializedName("width_l")
    private String widthL;
    @SerializedName("url_o")
    private String urlO;
    @SerializedName("height_o")
    private String heightO;
    @SerializedName("width_o")
    private String widthO;

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
     * @return The ispublic
     */
    public Integer getIspublic() {
        return ispublic;
    }

    /**
     * @param ispublic The ispublic
     */
    public void setIspublic(Integer ispublic) {
        this.ispublic = ispublic;
    }

    /**
     * @return The isfriend
     */
    public Integer getIsfriend() {
        return isfriend;
    }

    /**
     * @param isfriend The isfriend
     */
    public void setIsfriend(Integer isfriend) {
        this.isfriend = isfriend;
    }

    /**
     * @return The isfamily
     */
    public Integer getIsfamily() {
        return isfamily;
    }

    /**
     * @param isfamily The isfamily
     */
    public void setIsfamily(Integer isfamily) {
        this.isfamily = isfamily;
    }

    /**
     * @return The urlT
     */
    public String getUrlT() {
        return urlT;
    }

    /**
     * @param urlT The url_t
     */
    public void setUrlT(String urlT) {
        this.urlT = urlT;
    }

    /**
     * @return The heightT
     */
    public String getHeightT() {
        return heightT;
    }

    /**
     * @param heightT The height_t
     */
    public void setHeightT(String heightT) {
        this.heightT = heightT;
    }

    /**
     * @return The widthT
     */
    public String getWidthT() {
        return widthT;
    }

    /**
     * @param widthT The width_t
     */
    public void setWidthT(String widthT) {
        this.widthT = widthT;
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
     * @return The heightC
     */
    public Integer getHeightC() {
        return heightC;
    }

    /**
     * @param heightC The height_c
     */
    public void setHeightC(Integer heightC) {
        this.heightC = heightC;
    }

    /**
     * @return The widthC
     */
    public String getWidthC() {
        return widthC;
    }

    /**
     * @param widthC The width_c
     */
    public void setWidthC(String widthC) {
        this.widthC = widthC;
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
     * @return The heightL
     */
    public String getHeightL() {
        return heightL;
    }

    /**
     * @param heightL The height_l
     */
    public void setHeightL(String heightL) {
        this.heightL = heightL;
    }

    /**
     * @return The widthL
     */
    public String getWidthL() {
        return widthL;
    }

    /**
     * @param widthL The width_l
     */
    public void setWidthL(String widthL) {
        this.widthL = widthL;
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

    /**
     * @return The heightO
     */
    public String getHeightO() {
        return heightO;
    }

    /**
     * @param heightO The height_o
     */
    public void setHeightO(String heightO) {
        this.heightO = heightO;
    }

    /**
     * @return The widthO
     */
    public String getWidthO() {
        return widthO;
    }

    /**
     * @param widthO The width_o
     */
    public void setWidthO(String widthO) {
        this.widthO = widthO;
    }

}
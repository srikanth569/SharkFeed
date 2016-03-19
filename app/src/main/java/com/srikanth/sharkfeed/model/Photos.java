package com.srikanth.sharkfeed.model;

/**
 * Created by srikanth on 3/18/16.
 */

import java.util.ArrayList;
import java.util.List;

public class Photos {

    private Integer page;
    private Integer pages;
    private Integer perpage;
    private String total;
    private List<Photo> photo = new ArrayList<>();


    public Integer getPage() {
        return page;
    }


    public void setPage(Integer page) {
        this.page = page;
    }


    public Integer getPages() {
        return pages;
    }


    public void setPages(Integer pages) {
        this.pages = pages;
    }


    public Integer getPerpage() {
        return perpage;
    }


    public void setPerpage(Integer perpage) {
        this.perpage = perpage;
    }


    public String getTotal() {
        return total;
    }


    public void setTotal(String total) {
        this.total = total;
    }


    public List<Photo> getPhoto() {
        return photo;
    }


    public void setPhoto(List<Photo> photo) {
        this.photo = photo;
    }

}

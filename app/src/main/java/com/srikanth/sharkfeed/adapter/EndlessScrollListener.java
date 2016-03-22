package com.srikanth.sharkfeed.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by srikanth on 3/21/16.
 */
public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    //The implementing activity will implement this method
    public abstract void loadNextPage(int page);
}

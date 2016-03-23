package com.srikanth.sharkfeed.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by srikanth on 3/21/16.
 */
public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private GridLayoutManager mLayoutManager;
    private boolean loading = false;
    private long previousTotal = 0;
    private final static int minimum_threshold = 50;
    private long visibleThreshold = minimum_threshold;


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        mLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        visibleThreshold = totalItemCount / 2;
        visibleThreshold = visibleThreshold < minimum_threshold ? minimum_threshold : visibleThreshold;
        if (totalItemCount < previousTotal) {
            previousTotal = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }

        if (loading && (totalItemCount > previousTotal)) {
            loading = false;
            previousTotal = totalItemCount;
        }
        Log.v("Testing", "Checking loading " + loading + " lastVisibleItem " + lastVisibleItemPosition + " visibleThreshold " + visibleThreshold + " totalItemCount " + totalItemCount);
        if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
            Log.v("Testing", "The next page is " + (totalItemCount % 99) + 1);
            loadNextPage((totalItemCount / 99) + 1);
            loading = true;
        }
    }

    //The implementing activity will implement this method
    public abstract void loadNextPage(int page);
}

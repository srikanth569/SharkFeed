package com.srikanth.sharkfeed.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Logic for EndlessScrolling
 * Tries to fetch more data when the last visible item has scrolled past 75% of the capacity
 */
public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private static final String TAG = EndlessScrollListener.class.getSimpleName();
    private boolean isLoading = false;
    private final static int minimum_threshold = 50;
    private long visibleThreshold = minimum_threshold;


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        GridLayoutManager mLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        visibleThreshold = totalItemCount/2;
        visibleThreshold = visibleThreshold < minimum_threshold ? minimum_threshold : visibleThreshold;

        if (totalItemCount == 0) {
            this.isLoading = true;
        }

        Log.v(TAG, "Checking loading " + isLoading + " lastVisibleItem " + lastVisibleItemPosition + " visibleThreshold " + visibleThreshold + " totalItemCount " + totalItemCount);
        if (!isLoading && (lastVisibleItemPosition > visibleThreshold)) {
            loadNextPage();
            isLoading = true;
        }
    }

    //The implementing activity will implement this method
    public abstract void loadNextPage();

    public void setLoading(boolean _isLoading) {
        isLoading = _isLoading;
    }
}

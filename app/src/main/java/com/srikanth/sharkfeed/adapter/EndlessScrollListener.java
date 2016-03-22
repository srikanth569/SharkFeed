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

    public EndlessScrollListener(GridLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
        Log.v("Testing", "The span count is " + layoutManager.getSpanCount());
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        Log.v("Testing", "Scrolled " + dx + " " + dy + " the last visible item is " + mLayoutManager.findLastVisibleItemPosition() + " " + mLayoutManager.getItemCount());
        if (mLayoutManager.findLastVisibleItemPosition() > 50 && !loading) {
            loadNextPage(2);
            loading = true;
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        Log.v("Testing", "Scroll state changed " + newState);
    }


    //The implementing activity will implement this method
    public abstract void loadNextPage(int page);
}

package com.srikanth.sharkfeed.activity;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.srikanth.sharkfeed.R;
import com.srikanth.sharkfeed.adapter.EndlessScrollListener;
import com.srikanth.sharkfeed.adapter.PhotoAdapter;
import com.srikanth.sharkfeed.bus.RefreshCompleteEvent;
import com.srikanth.sharkfeed.data.SharkFeedContentProvider;
import com.srikanth.sharkfeed.model.Photo;
import com.srikanth.sharkfeed.service.ImageFeedService;
import com.srikanth.sharkfeed.util.StorageUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener {

    // Views
    private RecyclerView photoRecyclerView;
    private PhotoAdapter recyclerAdapter;
    private GridLayoutManager layoutManager = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    // Members
    private final EventBus bus = EventBus.getDefault();
    private final List<Photo> photos = new ArrayList<>();

    // Constants
    private final static String TAG = MainActivity.class.getSimpleName();
    private final static String EXTRA_PAGE_NUMBER = "extra_page_number";
    private static final String EXTRA_SEARCH_TAG = "extra_search_tag";
    private final int refresh_first_page = 1;

    // Variables
    private int page_number = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        photoRecyclerView = (RecyclerView) findViewById(R.id.photo_recycler);
        recyclerAdapter = new PhotoAdapter(photos, this);
        recyclerAdapter.setHasStableIds(true);
        layoutManager = new GridLayoutManager(this, 3);
        photoRecyclerView.setAdapter(recyclerAdapter);
        photoRecyclerView.setLayoutManager(layoutManager);
        photoRecyclerView.addOnScrollListener(scrollListener);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(this);
        }
        // We don't want to make a network call for data each time the user rotates the device
        // We just want to load the data from the DB to our UI
        if (savedInstanceState == null) {
            fetchDataFromNetwork(refresh_first_page);
        }
        populateUI();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    private void populateUI() {
        getLoaderManager().initLoader(100, null, this).forceLoad();
    }

    private void fetchDataFromNetwork(int page_number) {
        Intent intent = new Intent(this, ImageFeedService.class);
        intent.putExtra(EXTRA_PAGE_NUMBER, page_number);
        intent.putExtra(EXTRA_SEARCH_TAG, "shark");
        startService(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, // Parent activity context
                SharkFeedContentProvider.getTableUri(Photo.TABLE_NAME), // Table to query
                null, // projection
                null, // selection
                null, // selection arguments
                null // sort order)
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            recyclerAdapter.setData(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Todo loader reset needs to be handled properly
        // recyclerAdapter.clearData();
    }

    @Override
    public void onRefresh() {
        fetchDataFromNetwork(refresh_first_page);
        Log.v(TAG, "onRefresh is being called " + swipeRefreshLayout.isRefreshing());
    }

    private void onItemsLoadComplete() {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * Receives callback to stop animation the pull to refresh view
     *
     * @param event
     */
    public void onEvent(RefreshCompleteEvent event) {
        onItemsLoadComplete();
        if (scrollListener != null) {
            scrollListener.setLoading(false);
        }
    }

    EndlessScrollListener scrollListener = new EndlessScrollListener() {
        @Override
        public void loadNextPage() {
            int last_loaded = StorageUtils.readFromSharedPreferences(MainActivity.this);
            Log.v("Testing", "Last page loaded " + last_loaded);
            if (last_loaded > -1) {
                page_number = last_loaded + 1;
            }
            fetchDataFromNetwork(page_number);
            Log.v(TAG, "Loading the page " + page_number);
            StorageUtils.WriteToSharedPrefrences(MainActivity.this, page_number);
        }
    };
}

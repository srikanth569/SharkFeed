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
import android.view.View;
import android.widget.Button;

import com.facebook.stetho.Stetho;
import com.srikanth.sharkfeed.R;
import com.srikanth.sharkfeed.adapter.PhotoAdapter;
import com.srikanth.sharkfeed.bus.RefreshCompleteEvent;
import com.srikanth.sharkfeed.data.SharkFeedContentProvider;
import com.srikanth.sharkfeed.model.Photo;
import com.srikanth.sharkfeed.service.ImageFeedService;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView photoRecyclerView;
    private PhotoAdapter recyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Photo> photos = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private EventBus bus = EventBus.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        photoRecyclerView = (RecyclerView) findViewById(R.id.photo_recycler);
        recyclerAdapter = new PhotoAdapter(photos, this);
        mLayoutManager = new GridLayoutManager(this, 3);
        photoRecyclerView.setAdapter(recyclerAdapter);
        photoRecyclerView.setLayoutManager(mLayoutManager);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
        Button mButton = (Button) findViewById(R.id.dummy);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.getContentResolver().delete(SharkFeedContentProvider.getTableUri(Photo.TABLE_NAME), null, null);
            }
        });
        // We don't want to make a network call for data each time the user rotates the device
        // We just want to load the data from the DB to our UI
        if (savedInstanceState == null) {
            fetchDataFromNetwork();
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

    private void fetchDataFromNetwork() {
        Intent intent = new Intent(this, ImageFeedService.class);
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
            Log.v("Testing", " This is " + data.getCount());
            recyclerAdapter.setData(data);
        } else {
            Log.v("Testing", "Data is null");
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        Log.v("Testing", "Loader reset");
    }

    @Override
    public void onRefresh() {
        fetchDataFromNetwork();
        Log.v("Testing", "onrefresh is being called " + mSwipeRefreshLayout.isRefreshing());
    }

    void onItemsLoadComplete() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
            Log.v("Testing", "onrefresh is being called " + mSwipeRefreshLayout.isRefreshing());
        }
    }

    public void onEvent(RefreshCompleteEvent event) {
        onItemsLoadComplete();
    }
}

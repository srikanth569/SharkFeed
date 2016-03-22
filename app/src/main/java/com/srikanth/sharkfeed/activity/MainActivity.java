package com.srikanth.sharkfeed.activity;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.stetho.Stetho;
import com.srikanth.sharkfeed.R;
import com.srikanth.sharkfeed.adapter.PhotoAdapter;
import com.srikanth.sharkfeed.data.SharkFeedContentProvider;
import com.srikanth.sharkfeed.model.Photo;
import com.srikanth.sharkfeed.service.ImageFeedService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView photoRecyclerView;
    private PhotoAdapter recyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Photo> photos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        photoRecyclerView = (RecyclerView) findViewById(R.id.photo_recycler);
        recyclerAdapter = new PhotoAdapter(photos, this);
        mLayoutManager = new GridLayoutManager(this, 3);
        photoRecyclerView.setAdapter(recyclerAdapter);
        photoRecyclerView.setLayoutManager(mLayoutManager);
        Button mButton = (Button)findViewById(R.id.dummy);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.getContentResolver().delete(SharkFeedContentProvider.getTableUri(Photo.TABLE_NAME),null,null);
            }
        });
        fetchData();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());

    }

    private void fetchData() {
        Intent intent = new Intent(this, ImageFeedService.class);
        startService(intent);
        getLoaderManager().initLoader(1, null, this).forceLoad();
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
}

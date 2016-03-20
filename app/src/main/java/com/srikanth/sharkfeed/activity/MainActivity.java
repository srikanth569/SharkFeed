package com.srikanth.sharkfeed.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.srikanth.sharkfeed.R;
import com.srikanth.sharkfeed.adapter.PhotoAdapter;
import com.srikanth.sharkfeed.model.Photo;
import com.srikanth.sharkfeed.service.ImageFeedService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        fetchData();
    }

    private void fetchData() {
        Intent intent = new Intent(this, ImageFeedService.class);
        startService(intent);
    }

  /*  @Override
    public void onLoadFinished(Loader<FlickrPhotosFeed> loader, FlickrPhotosFeed data) {
        if (data != null) {
            photos = data.getPhotos().getPhoto();
            recyclerAdapter.setData(photos);
            Log.v("Testing", "The data has been set");
            recyclerAdapter.notifyDataSetChanged();
        } else {
            Log.v("Testing", "data is null");
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        Log.v("Testing", "Loader reloaded");
    }
    */
}

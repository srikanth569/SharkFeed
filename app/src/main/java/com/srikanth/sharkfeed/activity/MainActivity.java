package com.srikanth.sharkfeed.activity;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.srikanth.sharkfeed.R;
import com.srikanth.sharkfeed.loader.ImageFeedLoader;
import com.srikanth.sharkfeed.loader.LoaderId;
import com.srikanth.sharkfeed.model.FlickrPhotosFeed;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<FlickrPhotosFeed> {

    private Button dummyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dummyButton = (Button) findViewById(R.id.dummyButton);
        dummyButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(MainActivity.this, "Rustic shit", Toast.LENGTH_LONG).show();
        getLoaderManager().initLoader(LoaderId.LOAD_IMAGE_FEED, null, this).forceLoad();
    }

    @Override
    public Loader<FlickrPhotosFeed> onCreateLoader(int id, Bundle args) {
        Log.v("Testing", "creating image loader");
        return new ImageFeedLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<FlickrPhotosFeed> loader, FlickrPhotosFeed data) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}

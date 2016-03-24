package com.srikanth.sharkfeed.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.srikanth.sharkfeed.R;

/**
 * Created by srikanth on 3/22/16.
 */
public class FullScreenActivity extends Activity implements View.OnClickListener {

    ImageView imageView;
    Button downloadButton;
    Button openInFlickr;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_activity);
        imageView = (ImageView) findViewById(R.id.imagecrap);
        downloadButton = (Button) findViewById(R.id.download_image);
        openInFlickr = (Button) findViewById(R.id.open_in_flickr);
        openInFlickr.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("url");
        Picasso.with(this).load(url).into(imageView);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.open_in_flickr) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }
}

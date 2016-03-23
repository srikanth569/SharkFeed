package com.srikanth.sharkfeed.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.srikanth.sharkfeed.R;

/**
 * Created by srikanth on 3/22/16.
 */
public class FullScreenActivity extends Activity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_activity);
        imageView = (ImageView) findViewById(R.id.imagecrap);
        Bundle bundle = getIntent().getExtras();
        Picasso.with(this).load(bundle.getString("url")).placeholder(R.mipmap.ic_launcher).into(imageView);
    }
}

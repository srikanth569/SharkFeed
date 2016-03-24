package com.srikanth.sharkfeed.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.srikanth.sharkfeed.R;
import com.srikanth.sharkfeed.bus.DownloadComplete;
import com.srikanth.sharkfeed.service.DownloadImageService;
import com.srikanth.sharkfeed.util.PermissionUtil;

import de.greenrobot.event.EventBus;

/**
 * Handles displaying the Image in full screen
 */
public class FullScreenImageDisplayActivity extends Activity implements View.OnClickListener {

    // constants
    private static final String EXTRA_URL = "extra_url";
    private static final String EXTRA_TITLE = "extra_title";
    private static final String[] GALLERY_PERMISSION_STRING = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int GALLERY_PERMISSION_RESULT_CODE = 100;

    // Variables
    private String url;
    private String titleString;
    private final EventBus eventBus = EventBus.getDefault();

    // Views
    private ImageView imageView;
    private Button downloadButton;
    private Button openInFlickr;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        title = (TextView) findViewById(R.id.title);
        imageView = (ImageView) findViewById(R.id.action_image);
        downloadButton = (Button) findViewById(R.id.download_image);
        openInFlickr = (Button) findViewById(R.id.open_in_flickr);
        openInFlickr.setOnClickListener(this);
        downloadButton.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        url = bundle.getString(EXTRA_URL);
        titleString = bundle.getString(EXTRA_TITLE);
        populateData();
    }

    private void populateData() {
        Picasso.with(this).load(url).into(imageView);
        if (titleString != null) {
            title.setText(titleString);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventBus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        eventBus.unregister(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.open_in_flickr) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } else if (v.getId() == R.id.download_image) {
            checkForPermissionsBeforeSavingToDisk();
        }
    }

    private void checkForPermissionsBeforeSavingToDisk() {
        if (PermissionUtil.checkIfPermissionsAlreadyGranted(this, GALLERY_PERMISSION_STRING)) {
            saveImageToDisk();
        } else {
            for (String permission : GALLERY_PERMISSION_STRING) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        permission)) {
                    Toast.makeText(this, "Permission required to save image to disk", Toast.LENGTH_LONG).show();
                }
            }
            ActivityCompat.requestPermissions(this, GALLERY_PERMISSION_STRING, GALLERY_PERMISSION_RESULT_CODE);
        }
    }

    private void saveImageToDisk() {
        Intent intent = new Intent(this, DownloadImageService.class);
        intent.putExtra(EXTRA_URL, url);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean areAllPermissionsGranted = PermissionUtil.verifyPermissions(grantResults);
        if (areAllPermissionsGranted) {
            saveImageToDisk();
        }
    }

    public void onEvent(DownloadComplete downloadComplete) {

        if (downloadComplete.isSuccess()) {
            Toast.makeText(this, R.string.image_download_successful, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.image_download_failed, Toast.LENGTH_LONG).show();
        }
    }

}

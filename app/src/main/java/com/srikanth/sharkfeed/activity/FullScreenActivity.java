package com.srikanth.sharkfeed.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.stetho.server.http.HttpStatus;
import com.squareup.picasso.Picasso;
import com.srikanth.sharkfeed.R;
import com.srikanth.sharkfeed.util.PermissionUtil;
import com.srikanth.sharkfeed.util.StorageUtils;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by srikanth on 3/22/16.
 */
public class FullScreenActivity extends Activity implements View.OnClickListener {

    ImageView imageView;
    Button downloadButton;
    Button openInFlickr;
    String url;
    private static final String[] GALLERY_PERMISSION_STRING = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int GALLERY_PERMISSION_RESULT_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_activity);
        imageView = (ImageView) findViewById(R.id.imagecrap);
        downloadButton = (Button) findViewById(R.id.download_image);
        openInFlickr = (Button) findViewById(R.id.open_in_flickr);
        openInFlickr.setOnClickListener(this);
        downloadButton.setOnClickListener(this);
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
                    Toast.makeText(this, "Permission required to be able to save to disk", Toast.LENGTH_LONG).show();
                }
            }
            ActivityCompat.requestPermissions(this, GALLERY_PERMISSION_STRING, GALLERY_PERMISSION_RESULT_CODE);
        }
    }

    private void saveImageToDisk() {

        Log.v("Testing", "The External memory is readable " + StorageUtils.isExternalStorageWritable());
        if (StorageUtils.isExternalStorageWritable()) {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "SharkFeed");
            if (!file.mkdirs()) {
                Log.e("Testing", "Directory not created");
            }

            Bitmap bitmap = downloadBitmap(url);
            Log.v("Testing", "The size of the bitmap is " + bitmap.getHeight());

            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Test", "Testing");

          /*  FileOutputStream out = null;
            try {
                out = new FileOutputStream(file.getAbsolutePath()+"1234");
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                // PNG is a lossless format, the compression factor (100) is ignored
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            */
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean areAllPermissionsGranted = PermissionUtil.verifyPermissions(grantResults);
        if (areAllPermissionsGranted) {
            saveImageToDisk();
        }
    }

    private Bitmap downloadBitmap(String url) {
        HttpURLConnection urlConnection = null;
        try {
            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();

            int statusCode = urlConnection.getResponseCode();
            if (statusCode != HttpStatus.HTTP_OK) {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                return BitmapFactory.decodeStream(inputStream);
            }
        } catch (Exception e) {
            Log.d("URLCONNECTIONERROR", e.toString());
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            Log.w("ImageDownloader", "Error downloading image from " + url);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();

            }
        }
        return null;
    }


}

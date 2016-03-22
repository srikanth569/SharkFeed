package com.srikanth.sharkfeed.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.srikanth.sharkfeed.model.Photo;

/**
 * Created by srikanth on 3/20/16.
 */
public class SharkFeedContentProvider extends ContentProvider {

    private SQLiteOpenHelper mDatabaseHelper = null;

    private static final String AUTHORITY = "com.srikanth.sharkfeed.data.SharkFeedContentProvider";
    public static final String SCHEME = "content";

    // Table ids
    private static final int PHOTO_ID = 0;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, Photo.TABLE_NAME, PHOTO_ID);
    }

    /**
     * Get the table Uri
     *
     * @param tableName {@link String}
     * @return {@link android.net.Uri}
     */
    public static Uri getTableUri(String tableName) {
        return new Uri.Builder().scheme(SCHEME).authority(AUTHORITY).path(tableName).build();
    }


    @Override
    public boolean onCreate() {
        mDatabaseHelper = new ImageFeedDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        if (db == null) {
            return null;
        }
        final String tableName = getTableName(uri);
        Cursor cursor = null;
        try {
            cursor = db.query(tableName, projection, selection, selectionArgs,
                    null, null, sortOrder, null);
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            Log.v("Testing", "Setting notifcation for URI " + uri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final String type;
        switch (sUriMatcher.match(uri)) {
            case PHOTO_ID:
                type = "vnd.android.cursor.dir/vnd.sharkfeed.PhotoFeed";
                break;
            default:
                type = null;
        }
        return type;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.v("Testing", "Call to insert into a db");
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        if (db == null) {
            return null;
        }
        long rowId = 0;
        String tableName = getTableName(uri);
        try {
            rowId = db.insert(tableName, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rowId > 0) {
            Context context = getContext();
            if (context != null) {
                context.getContentResolver().notifyChange(uri, null);
            }
        }
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        if (db == null) {
            return 0;
        }
        int count = 0;
        String tableName = getTableName(uri);
        try {
            count = db.delete(tableName, selection, selectionArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Context context = getContext();
        if (context != null && count >= 1) {
            context.getContentResolver().notifyChange(uri, null);
            Log.v("Testing", "Deleting items " + count+" notifying uri "+uri);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private String getTableName(Uri uri) {
        final String tableName;
        switch (sUriMatcher.match(uri)) {
            case PHOTO_ID:
                tableName = Photo.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return tableName;
    }
}

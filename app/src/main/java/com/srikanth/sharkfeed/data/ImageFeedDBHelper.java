package com.srikanth.sharkfeed.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.srikanth.sharkfeed.model.Photo;

/**
 * Created by srikanth on 3/20/16.
 */
public class ImageFeedDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sharkfeed.db";
    private static final int DATABASE_VERSION = 1;

    public ImageFeedDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Photo.TABLE_STRUCTURE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // upgrade tasks here
    }
}

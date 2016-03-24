package com.srikanth.sharkfeed.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.srikanth.sharkfeed.R;
import com.srikanth.sharkfeed.activity.FullScreenImageDisplayActivity;
import com.srikanth.sharkfeed.model.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles RecyclerView loading and recycling of views
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private static final String EXTRA_URL = "extra_url";
    private static final String EXTRA_TITLE = "extra_title";
    private final Context context;
    private List<Photo> photos = new ArrayList<>();

    public PhotoAdapter(List<Photo> photoFeed, Context _context) {
        photos = photoFeed;
        context = _context;
    }

    public void setData(List<Photo> _photos) {
        photos = _photos;
        notifyDataSetChanged();
    }

    public Cursor setData(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        photos.clear();
        while (cursor.moveToNext()) {
            Photo photo = new Photo().inflateFromCursor(cursor);
            photos.add(photo);
        }
        notifyDataSetChanged();
        return cursor;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_row, parent, false);
        return new PhotoViewHolder(v, new PhotoViewHolder.HolderClickListener() {
            @Override
            public void onHolderClick(int position) {
                Bundle bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_from_right, R.anim.slide_out_from_left).toBundle();
                Intent intent = new Intent(context, FullScreenImageDisplayActivity.class);
                intent.putExtra(EXTRA_URL, photos.get(position).getUrlL());
                intent.putExtra(EXTRA_TITLE, photos.get(position).getTitle());
                context.startActivity(intent, bundle);
            }
        });
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        Picasso.with(context).load(photos.get(position).getUrlC()).
                stableKey(photos.get(position).getId()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(photos.get(position).getId());
    }

    public void clearData() {
        if (photos != null) {
            photos.clear();
        }
    }


    static class PhotoViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        public final ImageView image;
        public final HolderClickListener listener;

        public PhotoViewHolder(View itemView, HolderClickListener _listener) {
            super(itemView);
            listener = _listener;
            image = (ImageView) itemView.findViewById(R.id.image_individual);
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v instanceof ImageView) {
                if (listener != null) {
                    listener.onHolderClick(getAdapterPosition());
                }
            }
        }

        public interface HolderClickListener {
            void onHolderClick(int position);
        }

    }
}

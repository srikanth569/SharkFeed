package com.srikanth.sharkfeed.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.srikanth.sharkfeed.R;
import com.srikanth.sharkfeed.activity.FullScreenActivity;
import com.srikanth.sharkfeed.model.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srikanth on 3/19/16.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private Context context;
    public List<Photo> photos = new ArrayList<>();

    public PhotoAdapter(List<Photo> photoFeed, Context _context) {
        photos = photoFeed;
        context = _context;
    }

    public void setData(List<Photo> _photos) {
        photos = _photos;
        notifyDataSetChanged();
    }

    public void setData(Cursor cursor) {
        if (cursor == null) {
            return;
        }
        photos.clear();
        while (cursor.moveToNext()) {
            Photo photo = new Photo().inflateFromCursor(cursor);
            photos.add(photo);
        }
        notifyDataSetChanged();
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_row, parent, false);
        return new PhotoViewHolder(v, new PhotoViewHolder.HolderClickListener() {
            @Override
            public void onHolderClick(int position) {
                Log.v("Testing", "holy crap i clicked on " + position);
                Intent intent = new Intent(context, FullScreenActivity.class);
                intent.putExtra("url", photos.get(position).getUrlL());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        Picasso.with(context).load(photos.get(position).getUrlC()).into(holder.image);
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

        public ImageView image;
        public HolderClickListener listener;

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
                    Log.v("Testing", "Clicked on " + getAdapterPosition());
                    listener.onHolderClick(getAdapterPosition());
                }
            }
        }

        public interface HolderClickListener {
            void onHolderClick(int position);
        }

    }
}

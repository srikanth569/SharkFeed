package com.srikanth.sharkfeed.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.srikanth.sharkfeed.R;
import com.srikanth.sharkfeed.model.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srikanth on 3/19/16.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private Context context;
    private List<Photo> photos = new ArrayList<>();

    public PhotoAdapter(List<Photo> photoFeed, Context _context) {
        photos = photoFeed;
        context = _context;
    }

    public void setData(List<Photo> _photos) {
        photos = _photos;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_row, parent, false);
        return new PhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        Picasso.with(context).load(photos.get(position).getUrlL()).placeholder(R.mipmap.ic_launcher).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return photos.size();
    }


    class PhotoViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image_individual);
        }
    }
}

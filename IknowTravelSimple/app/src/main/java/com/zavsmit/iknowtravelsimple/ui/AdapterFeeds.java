package com.zavsmit.iknowtravelsimple.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.zavsmit.iknowtravelsimple.R;
import com.zavsmit.iknowtravelsimple.io.feeds.Feed;
import com.zavsmit.iknowtravelsimple.utils.Constants;

import java.util.ArrayList;

/**
 * Created by Sega on 14.02.2015.
 */
public class AdapterFeeds extends BaseAdapter {

    private ArrayList<Feed> data;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private LayoutInflater inflater;

    public AdapterFeeds(Activity context, ArrayList<Feed> data) {

        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {

        if (rowView == null) {

            ViewHolder holder = new ViewHolder();

            rowView = inflater.inflate(R.layout.item_feed, parent, false);
            holder.title = (TextView) rowView.findViewById(R.id.titleTextView);
            holder.image = (ImageView) rowView.findViewById(R.id.feedImageView);
            holder.progressBarImage = (ProgressBar) rowView.findViewById(R.id.progressBarFeedImage);

            rowView.setTag(holder);
        }

        final ViewHolder holder = (ViewHolder) rowView.getTag();
        Feed itemData = data.get(position);

        holder.title.setText(itemData.getTitle());

        String partImage;
        if (itemData.getArticleCover() != null) {
            partImage = itemData.getArticleCover().getFilename();
        } else {
            partImage = itemData.getAddressCover().getFilename();
        }
        String imageUrlNew = Constants.START_REQ_URL + Constants.PHOTO + partImage + "_500x200.jpg";

        //http://api.iknow.travel/photo/crop/_500x500.jpg   ПОЧЕМУ НЕ КАК В ДОКЕ!!!!!!

        imageLoader.displayImage(imageUrlNew, holder.image, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                holder.progressBarImage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                holder.progressBarImage.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.progressBarImage.setVisibility(View.GONE);
            }
        });

        return rowView;
    }

    class ViewHolder {
        ImageView image;
        TextView title;
        ProgressBar progressBarImage;
    }
}

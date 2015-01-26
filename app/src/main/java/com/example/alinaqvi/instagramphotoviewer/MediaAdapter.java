package com.example.alinaqvi.instagramphotoviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by alinaqvi on 1/20/15.
 */
public class MediaAdapter extends ArrayAdapter<MediaRecord> {
    List<MediaRecord> mediaRecords;

    public MediaAdapter(Context context) {
        super(context, R.layout.item_media_record);

    }
    public void setMediaRecords(List<MediaRecord> mediaRecords) {
        this.clear();
        this.addAll(mediaRecords);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MediaRecord mediaRecord = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_media_record, parent, false);
        }
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        ImageView ivUrl = (ImageView) convertView.findViewById(R.id.ivUrl);
        ImageView ivUser = (ImageView) convertView.findViewById(R.id.ivUser);

        Picasso.with(convertView.getContext()).load(mediaRecord.getUrl()).into(ivUrl);
        Picasso.with(convertView.getContext()).load(mediaRecord.getUserImageUrl()).transform(new CircleTransform()).into(ivUser);
        tvCaption.setText(mediaRecord.getCaption().length() > 150 ? mediaRecord.getCaption().substring(0, 150) + "..." : mediaRecord.getCaption());
        tvUsername.setText(mediaRecord.getUsername());
        tvLikes.setText("" + mediaRecord.getLikeCount());
        return convertView;
    }
}

package com.artamonov.youtubeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.artamonov.youtubeapp.R;
import com.artamonov.youtubeapp.model.Video;
import com.artamonov.youtubeapp.ui.PlayerActivity;
import com.squareup.picasso.Picasso;

import java.util.List;



public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeAdapter.MyViewHolder> {

    private Context mContext;
    private List<Video> mVideoList;

    YoutubeAdapter(Context mContext, List<Video> mVideoList) {
        this.mContext = mContext;
        this.mVideoList = mVideoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        final Video singleVideo = mVideoList.get(position);
        holder.video_title.setText(singleVideo.getTitle());

        Picasso.get()
                .load(singleVideo.getThumbnailURL())
                .resize(480,270)
                .centerCrop()
                .into(holder.thumbnail);

        holder.video_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra("VIDEO_ID", singleVideo.getId());
                intent.putExtra("VIDEO_TITLE",singleVideo.getTitle());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView thumbnail;
        public TextView video_title;
        public RelativeLayout video_view;

        MyViewHolder(View view) {

            super(view);
            thumbnail = view.findViewById(R.id.video_thumbnail);
            video_title = view.findViewById(R.id.video_title);
            video_view = view.findViewById(R.id.video_view);
        }
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }
}
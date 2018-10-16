package com.artamonov.youtubeapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artamonov.youtubeapp.R;
import com.artamonov.youtubeapp.model.Video;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private final List<Video> commentsList;
    private final Context context;


    public CommentsAdapter(Context context, List<Video> reviewsList) {
        this.context = context;
        this.commentsList = reviewsList;
    }

    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder holder, int position) {
        Video video = commentsList.get(position);

        if (!video.getComment().equals(context.getResources().getString((R.string.no_comments)))) {
            holder.comment.setText(video.getComment());
            holder.commentAuthor.setText(video.getCommentAuthor());
        } else {
            holder.comment.setText(video.getComment());
            holder.commentAuthor.setText(null);
        }
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView comment;
        TextView commentAuthor;

        public ViewHolder(View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.comment);
            commentAuthor = itemView.findViewById(R.id.comment_author);

        }
    }
}

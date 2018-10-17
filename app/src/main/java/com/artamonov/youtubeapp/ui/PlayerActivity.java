package com.artamonov.youtubeapp.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.artamonov.youtubeapp.R;
import com.artamonov.youtubeapp.adapter.CommentsAdapter;
import com.artamonov.youtubeapp.contract.PlayerContract;
import com.artamonov.youtubeapp.model.Video;
import com.artamonov.youtubeapp.network.CommentsParser;
import com.artamonov.youtubeapp.network.YoutubeConnector;
import com.artamonov.youtubeapp.presenter.PlayerPresenter;
import com.artamonov.youtubeapp.utils.NetworkUtils;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends YouTubeBaseActivity implements OnInitializedListener, PlayerContract.PlayerView {

    private static List<Video> videoComments = new ArrayList<>();
    private RecyclerView rvComments;

    private PlayerContract.PlayerPresenter presenter;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_detail);
        presenter = new PlayerPresenter(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvComments = findViewById(R.id.rv_comments);
        rvComments.setLayoutManager(layoutManager);

        YouTubePlayerView playerView = findViewById(R.id.youtubePlayerDetail);
        playerView.initialize(YoutubeConnector.API_KEY, this);

        TextView video_title = findViewById(R.id.player_title);
        String videoId = getIntent().getStringExtra("VIDEO_ID");
        video_title.setText(getIntent().getStringExtra("VIDEO_TITLE"));
        if (NetworkUtils.isNetworkAvailable(getApplicationContext())) {
            presenter.searchComments(videoId);
        } else {
            System.out.println("Response is not successful");
        }
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.cueVideo(getIntent().getStringExtra("VIDEO_ID"));
        }
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, getString(R.string.failure), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void parseJSONComments(String response) {
        videoComments = CommentsParser.parseCommentsJSON(getApplicationContext(), response);
        CommentsAdapter commentsAdapter = new CommentsAdapter
                (PlayerActivity.this, videoComments);
        rvComments.setAdapter(commentsAdapter);
    }
}
package com.artamonov.youtubeapp.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.artamonov.youtubeapp.CommentsParser;
import com.artamonov.youtubeapp.utils.NetworkUtils;
import com.artamonov.youtubeapp.R;
import com.artamonov.youtubeapp.YoutubeConnector;
import com.artamonov.youtubeapp.adapter.CommentsAdapter;
import com.artamonov.youtubeapp.model.Video;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlayerActivity extends YouTubeBaseActivity implements OnInitializedListener {


    public static Request request;
    public static String responseJSON;
    private static List<Video> videoComments = new ArrayList<>();
    RecyclerView rvComments;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_detail);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvComments = findViewById(R.id.rv_comments);
        rvComments.setLayoutManager(layoutManager);

        YouTubePlayerView playerView = findViewById(R.id.youtubePlayerDetail);
        playerView.initialize(YoutubeConnector.API_KEY, this);

        TextView video_title = findViewById(R.id.player_title);

        String videoId = getIntent().getStringExtra("VIDEO_ID");
        video_title.setText(getIntent().getStringExtra("VIDEO_TITLE"));
        String url = "https://www.googleapis.com/youtube/v3/commentThreads?" +
                "key=" + YoutubeConnector.API_KEY + "&textFormat=plainText&part=snippet&videoId=" +
                videoId + "&maxResults=50";
        getJSONVideoComments(url);
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


    private void getJSONVideoComments(String url) {

        if (NetworkUtils.isNetworkAvailable(getApplicationContext())) {
            OkHttpClient okHttpClient = new OkHttpClient();
            request = new Request.Builder()
                    .url(url)
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Check your Internet connection or try later",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        responseJSON = response.body().string();
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              videoComments = CommentsParser.parseCommentsJSON(getApplicationContext(), responseJSON);
                                              CommentsAdapter commentsAdapter = new CommentsAdapter
                                                      (PlayerActivity.this, videoComments);
                                              rvComments.setAdapter(commentsAdapter);
                                          }
                                      }
                        );

                    } else {
                        System.out.println("Response is not successful");

                    }
                }
            });

        } else {
            System.out.println("No Internet Connection");
        }

    }

}
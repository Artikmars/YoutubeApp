package com.artamonov.youtubeapp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.artamonov.youtubeapp.R;
import com.artamonov.youtubeapp.adapter.YoutubeAdapter;
import com.artamonov.youtubeapp.contract.MainContract;
import com.artamonov.youtubeapp.model.Video;
import com.artamonov.youtubeapp.presenter.MainPresenter;
import com.google.android.youtube.player.YouTubeBaseActivity;

import java.util.List;

public class MainActivity extends YouTubeBaseActivity implements MainContract.MainView {

    private String keyword;
    private RecyclerView mRecyclerView;
    private ProgressDialog mProgressDialog;
    private MainPresenter mainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityPresenter = new MainPresenter(this);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Searching...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        EditText searchInput = findViewById(R.id.search_input);
        mRecyclerView = findViewById(R.id.videos_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    keyword = v.getText().toString();
                    mainActivityPresenter.searchOnYoutube(keyword);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    return false;
                }
                return true;
            }
        });

    }

    @Override
    public void inflateVideoList(List<Video> videoList) {
        YoutubeAdapter youtubeAdapter = new YoutubeAdapter(getApplicationContext(), videoList);
        mRecyclerView.setAdapter(youtubeAdapter);
        youtubeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog.setMessage("Searching ...");
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        mProgressDialog.dismiss();
    }

}

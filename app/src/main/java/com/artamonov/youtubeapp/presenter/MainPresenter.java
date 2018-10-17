package com.artamonov.youtubeapp.presenter;

import android.app.Activity;

import com.artamonov.youtubeapp.contract.MainContract;
import com.artamonov.youtubeapp.model.Video;
import com.artamonov.youtubeapp.network.YoutubeConnector;
import com.artamonov.youtubeapp.utils.KeyboardUtils;

import java.util.List;

public class MainPresenter implements MainContract.MainPresenter {

    private final MainContract.MainView view;

    public MainPresenter(MainContract.MainView view) {
        this.view = view;
    }

    public void searchOnYoutube(final String keywords) {
        view.showProgressDialog();
        new YoutubeConnector(this).execute(keywords);
    }

    public void getVideoList(List<Video> videoList) {
        view.inflateVideoList(videoList);
        view.dismissProgressDialog();
    }

    public void hideKeyboard(Activity activity){
        KeyboardUtils.hideKeyboard(activity);
    }
}

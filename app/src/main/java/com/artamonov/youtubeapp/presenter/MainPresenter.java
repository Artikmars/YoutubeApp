package com.artamonov.youtubeapp.presenter;

import com.artamonov.youtubeapp.contract.MainContract;
import com.artamonov.youtubeapp.model.Video;
import com.artamonov.youtubeapp.network.YoutubeConnector;

import java.util.List;

public class MainPresenter implements MainContract.MainPresenter {

    private MainContract.MainView view;

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
}

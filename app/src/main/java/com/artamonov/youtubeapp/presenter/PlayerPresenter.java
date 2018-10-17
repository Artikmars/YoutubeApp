package com.artamonov.youtubeapp.presenter;

import com.artamonov.youtubeapp.contract.PlayerContract;
import com.artamonov.youtubeapp.network.OkHtttpHandler;

public class PlayerPresenter implements PlayerContract.PlayerPresenter {
    private final PlayerContract.PlayerView view;

    public PlayerPresenter(PlayerContract.PlayerView view) {
        this.view = view;
    }

    @Override
    public void getComments(String results) {
        view.parseJSONComments(results);
    }

    @Override
    public void searchComments(String videoId) {
        new OkHtttpHandler(this).execute(videoId);
    }

    public void inflateComments(String response) {
        view.parseJSONComments(response);
    }

}
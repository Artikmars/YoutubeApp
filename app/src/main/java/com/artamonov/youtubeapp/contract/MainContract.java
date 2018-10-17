package com.artamonov.youtubeapp.contract;

import android.app.Activity;

import com.artamonov.youtubeapp.model.Video;

import java.util.List;

public interface MainContract {

    interface MainPresenter {
        void searchOnYoutube(String keywords);
        void hideKeyboard(Activity activity);
        void getVideoList(List<Video> videoList);
    }

    interface MainView {

        void showProgressDialog();

        void dismissProgressDialog();

        void inflateVideoList(List<Video> videoList);
    }

}

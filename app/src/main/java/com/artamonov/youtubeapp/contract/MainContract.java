package com.artamonov.youtubeapp.contract;

import com.artamonov.youtubeapp.model.Video;

import java.util.List;

public interface MainContract {

    interface MainPresenter {
        public void searchOnYoutube(String keywords);

        public void getVideoList(List<Video> videoList);
    }

    interface MainView {

        public void showProgressDialog();

        public void dismissProgressDialog();

        public void inflateVideoList(List<Video> videoList);
    }

}

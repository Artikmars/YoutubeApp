package com.artamonov.youtubeapp.contract;

public interface PlayerContract {

    interface PlayerPresenter {
        void getComments(String results);

        void searchComments(String videoId);
    }

    interface PlayerView {
        void parseJSONComments(String response);

    }
}

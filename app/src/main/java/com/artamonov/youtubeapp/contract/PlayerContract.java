package com.artamonov.youtubeapp.contract;

public interface PlayerContract {

    interface PlayerPresenter {
        void getComments(String results);

        void searchComments(String videoId);

        void inflateComments(String response);
    }

    interface PlayerView {

        void parseJSONComments(String response);

        void inflateVideoList();
    }

    interface ResponseListener {
        void onSuccess(String response);
        // void onError(Response<TopMoviesResponse> response);
        // void onFailure(Throwable t);
    }
}

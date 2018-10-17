package com.artamonov.youtubeapp.contract;

import android.app.Activity;

public interface PlayerContract {

    interface PlayerPresenter {
        void getComments(String results);
        void hideKeyboard(Activity activity);
        void searchComments(String videoId);
    }

    interface PlayerView {
        void parseJSONComments(String response);

    }
}

package com.artamonov.youtubeapp.network;

import android.os.AsyncTask;
import android.util.Log;

import com.artamonov.youtubeapp.contract.PlayerContract;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHtttpHandler extends AsyncTask<String, Void, String> {

    private static final String TAG = "myLogs";

    PlayerContract.PlayerView view;
    private PlayerContract.PlayerPresenter presenter;

    public OkHtttpHandler(PlayerContract.PlayerPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        presenter.getComments(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = "https://www.googleapis.com/youtube/v3/commentThreads?" +
                "key=" + YoutubeConnector.API_KEY + "&textFormat=plainText&part=snippet&videoId=" +
                strings[0] + "&maxResults=50";
        Log.i(TAG, "url: " + url);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}

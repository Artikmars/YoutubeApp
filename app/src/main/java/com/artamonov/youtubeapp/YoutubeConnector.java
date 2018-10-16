package com.artamonov.youtubeapp;

import android.util.Log;

import com.artamonov.youtubeapp.model.Video;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class YoutubeConnector {

    public static final String API_KEY = "AIzaSyCwbGziupadfpxuaTv72BsKZxOAQQDDj9A";
    private static final String PACKAGE_NAME = "com.artamonov.youtubeapp";
    private static final long MAX_RESULTS = 25;
    private YouTube.Search.List query;

    YoutubeConnector() {
        YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {

            @Override
            public void initialize(HttpRequest request) throws IOException {
                request.getHeaders().set("X-Android-Package", PACKAGE_NAME);
            }
        }).setApplicationName("SearchYoutube").build();

        try {

            query = youtube.search().list("id,snippet");
            query.setKey(API_KEY);
            query.setType("video");
            query.setFields("items(id/kind,id/videoId,snippet/title,snippet/description,snippet/thumbnails/high/url)");

        } catch (IOException e) {
            Log.d("YC", "Could not initialize: " + e);
        }
    }

    private static List<Video> setItemsList(Iterator<SearchResult> iteratorSearchResults) {
        List<Video> tempSetItems = new ArrayList<>();
        if (!iteratorSearchResults.hasNext()) {
            System.out.println(" There aren't any results for your query.");
        }
        while (iteratorSearchResults.hasNext()) {
            SearchResult singleVideo = iteratorSearchResults.next();
            ResourceId rId = singleVideo.getId();

            if (rId.getKind().equals("youtube#video")) {

                Video item = new Video();

                Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getHigh();
                item.setId(singleVideo.getId().getVideoId());
                item.setTitle(singleVideo.getSnippet().getTitle());
                item.setDescription(singleVideo.getSnippet().getDescription());
                item.setThumbnailURL(thumbnail.getUrl());

                tempSetItems.add(item);
            }
        }
        return tempSetItems;
    }

    public List<Video> search(String keywords) {

        query.setQ(keywords);
        query.setMaxResults(MAX_RESULTS);

        try {

            SearchListResponse response = query.execute();
            List<SearchResult> results = response.getItems();
            List<Video> items = new ArrayList<Video>();
            if (results != null) {
                items = setItemsList(results.iterator());
            }
            return items;

        } catch (IOException e) {
            Log.d("YC", "Could not search: " + e);
            return null;
        }
    }
}
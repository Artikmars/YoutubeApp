package com.artamonov.youtubeapp.network;

import android.content.Context;

import com.artamonov.youtubeapp.R;
import com.artamonov.youtubeapp.model.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommentsParser {

    public static List<Video> parseCommentsJSON(Context context, String json) {

        List<Video> results = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray resultsJsonArray = jsonObject.getJSONArray(context.getResources().getString((R.string.items)));

            if (resultsJsonArray.length() != 0) {
                for (int i = 0; i < resultsJsonArray.length(); i++) {
                    JSONObject jsonCommentObject = resultsJsonArray.getJSONObject(i);
                    JSONObject jsonSnippetObject = jsonCommentObject
                            .getJSONObject(context.getResources().getString((R.string.snippet)));
                    JSONObject jsonTopLevelCommentObject = jsonSnippetObject
                            .getJSONObject(context.getResources().getString((R.string.topLevelComment)));
                    JSONObject jsonTopLevelSnippetObject = jsonTopLevelCommentObject
                            .getJSONObject(context.getResources().getString((R.string.snippet)));
                    String comment = jsonTopLevelSnippetObject
                            .optString(context.getResources().getString((R.string.textDisplay)));
                    String author = jsonTopLevelSnippetObject
                            .optString(context.getResources().getString((R.string.authorDisplayName)));

                    Video video = new Video();
                    video.setComment(comment);
                    video.setCommentAuthor(author);
                    results.add(video);
                }
            } else {
                Video video = new Video();
                video.setComment(context.getResources().getString(R.string.no_comments));
                results.add(video);
            }
        } catch (JSONException e) {
        }
        return results;

    }


}

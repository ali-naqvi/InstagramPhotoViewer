package com.example.alinaqvi.instagramphotoviewer;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PhotoViewerActivity extends ActionBarActivity {
    MediaAdapter moviesAdapter;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync(0);
            }
        });

        moviesAdapter = new MediaAdapter(this);
        ListView lvMediaRecord = (ListView) findViewById(R.id.lvMediaRecord);
        lvMediaRecord.setAdapter(moviesAdapter);
    }

    private void fetchTimelineAsync (int page) {
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get("https://api.instagram.com/v1/media/popular?client_id=41c33ae307af4517b5d4628793bbe6f9", new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, statusCode + " -- " + responseString, throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                List<MediaRecord> mediaRecords = new ArrayList<MediaRecord>();

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = (JSONObject) jsonArray.get(i);
                        MediaRecord mediaRecord = new MediaRecord();
                        mediaRecord.setUrl(data.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
                        JSONObject caption = null;
                        if(!data.isNull("caption")) {
                            caption = data.getJSONObject("caption");
                        }
                        mediaRecord.setCaption(caption != null ? caption.getString("text") : "");
                        mediaRecord.setUsername(data.getJSONObject("user").getString("username"));
                        mediaRecord.setUserImageUrl(data.getJSONObject("user").getString("profile_picture"));
                        mediaRecord.setLikeCount(data.getJSONObject("likes").getLong("count"));
                        mediaRecords.add(mediaRecord);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                swipeContainer.setRefreshing(false);
                moviesAdapter.setMediaRecords(mediaRecords);
                moviesAdapter.notifyDataSetChanged();
                super.onSuccess(statusCode, headers, response);
            }
        });
    }

    @Override
    protected void onResume() {
        fetchTimelineAsync(0);
        super.onResume();
    }
}

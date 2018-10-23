package com.yleaf.stas.testapplication.activities.items;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yleaf.stas.testapplication.R;
import com.yleaf.stas.testapplication.data.items.GetPodcastItem;
import com.yleaf.stas.testapplication.models.items.PodcastItem;
import com.yleaf.stas.testapplication.models.items_response.JSONResponsePodcast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PodcastActivity extends AppCompatActivity {

    private static final String EXTRA_DATA_ID = "data_id";
    private static final String TAG = PodcastActivity.class.getSimpleName();
    private int dataId;
    private PodcastItem podcastItem;


    private ProgressBar progressBar;
    private TextView artistName;
    private TextView trackName;
    private ImageView imageView;
    private TextView releaseDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_podcasts);

        dataId = getIntent().getIntExtra(EXTRA_DATA_ID, 0);

        initWidgets();

        Call<JSONResponsePodcast> response = new GetPodcastItem().getPodcastItem(dataId);

        response.enqueue(new Callback<JSONResponsePodcast>() {
            @Override
            public void onResponse(Call<JSONResponsePodcast> call, Response<JSONResponsePodcast> response) {
                Log.i(TAG, "onResponse: Server Response: " + response.toString());

                if(response.isSuccessful()) {
                    ArrayList<PodcastItem> results = response.body().getResults();
                    podcastItem = results.get(0);

                    setWidgets();
                }

            }

            @Override
            public void onFailure(Call<JSONResponsePodcast> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initWidgets() {
        progressBar = findViewById(R.id.item_podcasts_progress_bar);
        artistName = findViewById(R.id.item_podcasts_artist_name);
        trackName = findViewById(R.id.item_podcasts_track_name);
        imageView = findViewById(R.id.item_podcasts_image_view);
        releaseDate = findViewById(R.id.item_podcasts_release_date);
    }

    private void setWidgets() {
        progressBar.setVisibility(View.INVISIBLE);
        artistName.setText(podcastItem.getArtistName());
        trackName.setText(podcastItem.getTrackName());
        Glide.with(this).load(podcastItem.getArtworkUrl600()).into(imageView);
        releaseDate.setText(podcastItem.getReleaseDate().substring(0, 10));
    }

    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, PodcastActivity.class);
        intent.putExtra(EXTRA_DATA_ID, id);
        return intent;
    }
}

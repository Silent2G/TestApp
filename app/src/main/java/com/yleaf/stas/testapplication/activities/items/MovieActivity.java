package com.yleaf.stas.testapplication.activities.items;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.yleaf.stas.testapplication.R;
import com.yleaf.stas.testapplication.models.items.MovieItem;
import com.yleaf.stas.testapplication.models.items_response.JSONResponseMovie;
import com.yleaf.stas.testapplication.services.APIServiceItem;
import com.yleaf.stas.testapplication.services.RetrofitClient;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieActivity extends AppCompatActivity {

    private static final String EXTRA_DATA_ID = "data_id";
    private static final String TAG = MovieActivity.class.getSimpleName();
    private int dataId;
    private MovieItem movieItem;
    private Retrofit retrofit;

    private ProgressBar progressBar;
    private TextView artistName;
    private TextView trackName;
    private VideoView videoView;
    private TextView releaseDate;
    private TextView description;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_movies);

        dataId = getIntent().getIntExtra(EXTRA_DATA_ID, 0);

        initWidgets();

        File cacheFile = new File(this.getCacheDir(), "HttpCache");
        cacheFile.mkdirs();

        retrofit = RetrofitClient.getClient(cacheFile, "http://itunes.apple.com/");
        APIServiceItem apiServiceItem = retrofit.create(APIServiceItem.class);

        Call<JSONResponseMovie> response = apiServiceItem.getItemMovie(dataId);
        response.enqueue(new Callback<JSONResponseMovie>() {
            @Override
            public void onResponse(Call<JSONResponseMovie> call, Response<JSONResponseMovie> response) {
                Log.i(TAG, "onResponse: Server Response: " + response.toString());

                if(response.isSuccessful()) {
                    ArrayList<MovieItem> results = response.body().getResults();
                    movieItem = results.get(0);

                    setWidgets();
                }
            }

            @Override
            public void onFailure(Call<JSONResponseMovie> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initWidgets() {
        progressBar = findViewById(R.id.item_movies_progress_bar);
        artistName = findViewById(R.id.item_movies_artist_name);
        trackName = findViewById(R.id.item_movies_track_name);
        videoView = findViewById(R.id.item_movies_video_view);
        releaseDate = findViewById(R.id.item_movies_release_date);
        description = findViewById(R.id.item_movies_description);
    }

    private void setWidgets() {
        progressBar.setVisibility(View.INVISIBLE);

        artistName.setText(movieItem.getArtistName());
        trackName.setText(movieItem.getTrackName());

        Uri uri = Uri.parse(movieItem.getPreviewUrl());
        videoView.setVideoURI(uri);
        videoView.start();

        releaseDate.setText(movieItem.getReleaseDate().substring(0, 10));
        description.setText(movieItem.getLongDescription());
    }

    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, MovieActivity.class);
        intent.putExtra(EXTRA_DATA_ID, id);
        return intent;
    }
}

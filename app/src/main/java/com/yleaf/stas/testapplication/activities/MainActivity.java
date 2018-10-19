package com.yleaf.stas.testapplication.activities;

import android.content.Context;
import android.os.PersistableBundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yleaf.stas.testapplication.R;
import com.yleaf.stas.testapplication.db.service.BookService;
import com.yleaf.stas.testapplication.db.service.FavoriteService;
import com.yleaf.stas.testapplication.db.service.MovieService;
import com.yleaf.stas.testapplication.db.service.PodcastService;
import com.yleaf.stas.testapplication.fragments.AudioBooksFragment;
import com.yleaf.stas.testapplication.fragments.FavoriteFragment;
import com.yleaf.stas.testapplication.fragments.MoviesFragment;
import com.yleaf.stas.testapplication.fragments.PodcastsFragment;
import com.yleaf.stas.testapplication.models.Data;
import com.yleaf.stas.testapplication.models.JSONResponse;
import com.yleaf.stas.testapplication.services.APIService;
import com.yleaf.stas.testapplication.services.RetrofitClient;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private Retrofit retrofit;
    private static final String TAG = MainActivity.class.getSimpleName();

    private AtomicInteger counter = new AtomicInteger();
    private Context appContext;
    private ProgressBar progressBar;

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appContext = getApplicationContext();

        initViews();

        if(savedInstanceState == null) {
            if(isTablesEmpty()) {
                getData();
            } else {
                startFirstFragment();
            }
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {

                case R.id.navigation_audio_books:
                    selectedFragment = AudioBooksFragment.newInstance();
                    break;

                case R.id.navigation_movies:
                    selectedFragment = MoviesFragment.newInstance();
                    break;

                case R.id.navigation_podcasts:
                    selectedFragment = PodcastsFragment.newInstance();
                    break;

                case R.id.navigation_favorite:
                    selectedFragment = FavoriteFragment.newInstance();
                    break;
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            return true;
        });

    }

    private void startFirstFragment() {
        progressBar.setVisibility(View.INVISIBLE);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, AudioBooksFragment.newInstance());
        transaction.commit();
    }

    private boolean isTablesEmpty() {
        return new BookService(appContext).isEmpty() && new FavoriteService(appContext).isEmpty()
                && new MovieService(appContext).isEmpty() && new PodcastService(appContext).isEmpty();
    }

    private void getData() {
        retrofit = RetrofitClient.getClient("https://rss.itunes.apple.com/api/v1/us/");

        APIService apiService = retrofit.create(APIService.class);

        Call<JSONResponse> audioBooksResponse = apiService.getAudioBooks();
        parseAndStoreData(audioBooksResponse);

        Call<JSONResponse> moviesResponse = apiService.getMovies();
        parseAndStoreData(moviesResponse);

        Call<JSONResponse> podcastsResponse = apiService.getPodcasts();
        parseAndStoreData(podcastsResponse);
    }

    private void parseAndStoreData(Call<JSONResponse> jsonResponseCall) {

        jsonResponseCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                Log.i(TAG, "onResponse: Server Response: " + response.toString());

                if(response.isSuccessful()) {
                    ArrayList<Data> results = response.body().getFeed().getResults();

                    for (int i = 0; i < results.size(); i++) {
                        int id = results.get(i).getId();
                        String kind = results.get(i).getKind();
                        String artistName = results.get(i).getArtistName();
                        String name = results.get(i).getName();
                        String artworkUrl100 = results.get(i).getArtworkUrl100();

                        switch (kind) {
                            case "book":
                                new BookService(getBaseContext()).save(new Data(id, kind, artistName, name, artworkUrl100));
                                break;

                            case "movie":
                                new MovieService(getBaseContext()).save(new Data(id, kind, artistName, name, artworkUrl100));
                                break;

                            case "podcast":
                                new PodcastService(getBaseContext()).save(new Data(id, kind, artistName, name, artworkUrl100));
                                break;
                        }
                    }

                    if(counter.incrementAndGet() == 3) {
                        startFirstFragment();
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong " + t.getMessage());
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        progressBar = findViewById(R.id.progress_bar);
    }
}

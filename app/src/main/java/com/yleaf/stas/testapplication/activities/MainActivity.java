package com.yleaf.stas.testapplication.activities;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.yleaf.stas.testapplication.R;
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

    private ArrayList<Data> audioBooks = new ArrayList<>();
    private ArrayList<Data> movies = new ArrayList<>();
    private ArrayList<Data> podcasts = new ArrayList<>();

    private AtomicInteger counter = new AtomicInteger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getData();

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

    private void getData() {
        retrofit = RetrofitClient.getClient("https://rss.itunes.apple.com/api/v1/us/");

        APIService apiService = retrofit.create(APIService.class);

        Call<JSONResponse> audioBooksResponse = apiService.getAudioBooks();
        parseData(audioBooksResponse);

        Call<JSONResponse> moviesResponse = apiService.getMovies();
        parseData(moviesResponse);

        Call<JSONResponse> podcastsResponse = apiService.getPodcasts();
        parseData(podcastsResponse);
    }

    private void parseData(Call<JSONResponse> jsonResponseCall) {

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
                            case "book": audioBooks.add(new Data(id, kind, artistName, name, artworkUrl100));
                                break;

                            case "movie": movies.add(new Data(id, kind, artistName, name, artworkUrl100));
                                break;

                            case "podcast": podcasts.add(new Data(id, kind, artistName, name, artworkUrl100));
                                break;
                        }
                    }

                    if(counter.incrementAndGet() == 3) {
                        Log.i("SIZES", audioBooks.size() + " " + movies.size() + " " + podcasts.size());
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
    }
}

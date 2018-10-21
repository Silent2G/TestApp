package com.yleaf.stas.testapplication.data;

import android.app.Activity;

import com.yleaf.stas.testapplication.models.JSONResponse;
import com.yleaf.stas.testapplication.services.APIService;
import com.yleaf.stas.testapplication.services.RetrofitClient;

import retrofit2.Call;
import retrofit2.Retrofit;

public class GetData {

    private Retrofit retrofit;
    private Activity activity;

    public GetData(Activity activity) {
        this.activity = activity;
    }

    public void getData() {
        retrofit = RetrofitClient.getClient("https://rss.itunes.apple.com/api/v1/us/");

        APIService apiService = retrofit.create(APIService.class);

        Call<JSONResponse> audioBooksResponse = apiService.getAudioBooks();
        new ParseAndStoreData(audioBooksResponse, activity).parseAndStoreData();

        Call<JSONResponse> moviesResponse = apiService.getMovies();
        new ParseAndStoreData(moviesResponse, activity).parseAndStoreData();

        Call<JSONResponse> podcastsResponse = apiService.getPodcasts();
        new ParseAndStoreData(podcastsResponse, activity).parseAndStoreData();
    }
}

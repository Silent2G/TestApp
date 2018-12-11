package com.yleaf.stas.testapplication.services;

import com.yleaf.stas.testapplication.models.JSONResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("audiobooks/top-audiobooks/all/25/explicit.json")
    Call<JSONResponse> getAudioBooks();

    @GET("movies/top-movies/all/25/explicit.json")
    Call<JSONResponse> getMovies();

    @GET("podcasts/top-podcasts/all/25/explicit.json")
    Call<JSONResponse> getPodcasts();
}
package com.yleaf.stas.testapplication.services;

import com.yleaf.stas.testapplication.models.items_response.JSONResponseAudioBook;
import com.yleaf.stas.testapplication.models.items_response.JSONResponseMovie;
import com.yleaf.stas.testapplication.models.items_response.JSONResponsePodcast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServiceItem {

    @GET("lookup")
    Call<JSONResponseAudioBook> getItemAudioBook(@Query("id") int id);

    @GET("lookup")
    Call<JSONResponseMovie> getItemMovie(@Query("id") int id);

    @GET("lookup")
    Call<JSONResponsePodcast> getItemPodcast(@Query("id") int id);
}
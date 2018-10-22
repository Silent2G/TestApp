package com.yleaf.stas.testapplication.data.items;

import com.yleaf.stas.testapplication.models.items_response.JSONResponsePodcast;
import com.yleaf.stas.testapplication.services.APIServiceItem;
import com.yleaf.stas.testapplication.services.RetrofitClient;

import retrofit2.Call;
import retrofit2.Retrofit;

public class GetPodcastItem {

    private Retrofit retrofit;

    public Call<JSONResponsePodcast> getPodcastItem(int id) {
        retrofit = RetrofitClient.getClient("http://itunes.apple.com/");
        APIServiceItem apiServiceItem = retrofit.create(APIServiceItem.class);

        return apiServiceItem.getItemPodcast(id);
    }
}

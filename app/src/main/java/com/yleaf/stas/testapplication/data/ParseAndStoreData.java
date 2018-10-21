package com.yleaf.stas.testapplication.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
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
import com.yleaf.stas.testapplication.models.Data;
import com.yleaf.stas.testapplication.models.JSONResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ParseAndStoreData {

    private Call<JSONResponse> jsonResponseCall;
    private Activity activity;
    private static final String TAG = ParseAndStoreData.class.getSimpleName();
    private static AtomicInteger counter;
    private ProgressBar progressBar;

    public ParseAndStoreData(Call<JSONResponse> jsonResponseCall, Activity activity) {
        this.jsonResponseCall = jsonResponseCall;
        this.activity = activity;
    }

    public void parseAndStoreData() {
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

                        Data data = new Data(id, kind, artistName, name, artworkUrl100);

                        checkIfObjectInFavorite(data);
                    }

                    if(counter == null) {
                        counter = new AtomicInteger();
                    }

                    if(counter.incrementAndGet() == 3) {
                        startFirstFragment();
                        counter = null;

                        saveUpdateTime();
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong " + t.getMessage());
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startFirstFragment() {
        progressBar = activity.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, AudioBooksFragment.newInstance());
        transaction.commit();
    }

    private void checkIfObjectInFavorite(Data data) {
        if(!new FavoriteService(activity).isObjectStored(data.getId())) {
            switch (data.getKind()) {
                case "book":
                    new BookService(activity).save(data);
                    break;

                case "movie":
                    new MovieService(activity).save(data);
                    break;

                case "podcast":
                    new PodcastService(activity).save(data);
                    break;
            }
        }
    }

    private void saveUpdateTime() {
        SharedPreferences.Editor editor = activity.getSharedPreferences("update", MODE_PRIVATE).edit();
        Date date = new Date();
        editor.putLong("time", date.getTime());
        editor.apply();
    }
}

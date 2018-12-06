package com.yleaf.stas.testapplication.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.yleaf.stas.testapplication.activities.MainActivity;
import com.yleaf.stas.testapplication.db.room.App;
import com.yleaf.stas.testapplication.db.room.AppDatabase;
import com.yleaf.stas.testapplication.db.room.DataDao;
import com.yleaf.stas.testapplication.db.service.BookService;
import com.yleaf.stas.testapplication.db.service.FavoriteService;
import com.yleaf.stas.testapplication.db.service.MovieService;
import com.yleaf.stas.testapplication.db.service.PodcastService;
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


    public Context context;

    private static final String TAG = ParseAndStoreData.class.getSimpleName();

    private Call<JSONResponse> jsonResponseCall;
    private static AtomicInteger counter;

    public ParseAndStoreData(Call<JSONResponse> jsonResponseCall) {
        this.jsonResponseCall = jsonResponseCall;
        context = App.getInstance().getAppContextComponent().getContext();
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

                        Log.i(TAG, name);

                        Data data = new Data(id, kind, artistName, name, artworkUrl100);

                        checkIfObjectInFavorite(data);

//                        if(checkIsObjectStored(id)) {
//                            AppDatabase db = App.getInstance().getDatabase();
//                            DataDao dataDao = db.dataDao();
//                            dataDao.insert(data);
//                            Log.i(TAG, "ROOM INSERTED");
//                        }
                    }

                    if(counter == null) {
                        counter = new AtomicInteger();
                    }

                    if(counter.incrementAndGet() == 3) {
                        MainActivity.startFirstFragment();
                        counter = null;

                        saveUpdateTime();
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong " + t.getMessage());
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean checkIsObjectStored(int id) {
        AppDatabase db = App.getInstance().getDatabase();
        DataDao dataDao = db.dataDao();
        Data data = dataDao.getById(id);
        return data != null;
    }

    private void checkIfObjectInFavorite(Data data) {
        if(!new FavoriteService(context).isObjectStored(data.getId())) {
            switch (data.getKind()) {
                case "book":
                    new BookService(context).save(data);
                    break;

                case "movie":
                    new MovieService(context).save(data);
                    break;

                case "podcast":
                    new PodcastService(context).save(data);
                    break;
            }
        }
    }

    private void saveUpdateTime() {
        SharedPreferences.Editor editor = context.getSharedPreferences("update", MODE_PRIVATE).edit();
        Date date = new Date();
        editor.putLong("time", date.getTime());
        editor.apply();
    }
}

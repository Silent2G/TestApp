package com.yleaf.stas.testapplication.data;

import android.content.Context;

import com.yleaf.stas.testapplication.db.room.App;
import com.yleaf.stas.testapplication.di.components.DaggerMainComponent;
import com.yleaf.stas.testapplication.di.components.MainComponent;
import com.yleaf.stas.testapplication.di.modules.ApplicationContextModule;
import com.yleaf.stas.testapplication.models.JSONResponse;
import com.yleaf.stas.testapplication.services.APIServiceData;

import retrofit2.Call;

public class GetData {

    public Context context;

    public GetData() {
        context = App.getInstance().getAppContextComponent().getContext();
    }

    public void getData() {

        MainComponent daggerMainComponent = DaggerMainComponent.builder()
                .applicationContextModule(new ApplicationContextModule(context))
                .build();

        APIServiceData apiServiceData = daggerMainComponent.getApiServiceData();

//        apiServiceData.getAudioBooks()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Call<JSONResponse>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.i("TAGAGAGAG", "onSubscribe()");
//                    }
//
//                    @Override
//                    public void onNext(Call<JSONResponse> jsonResponseCall) {
//                        new ParseAndStoreData(jsonResponseCall , context).parseAndStoreData();
//                        Log.i("TAGAGAGAG", "onNext()");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.i("TAGAGAGAG", "onComplete()");
//                    }
//                });
//
        Call<JSONResponse> audioBooksResponse = apiServiceData.getAudioBooks();
        new ParseAndStoreData(audioBooksResponse).parseAndStoreData();

        Call<JSONResponse> moviesResponse = apiServiceData.getMovies();
        new ParseAndStoreData(moviesResponse).parseAndStoreData();

        Call<JSONResponse> podcastsResponse = apiServiceData.getPodcasts();
        new ParseAndStoreData(podcastsResponse).parseAndStoreData();
    }
}

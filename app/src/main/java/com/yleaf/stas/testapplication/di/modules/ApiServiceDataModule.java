package com.yleaf.stas.testapplication.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yleaf.stas.testapplication.di.annotations.MainComponentScope;
import com.yleaf.stas.testapplication.services.APIServiceData;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = OkHttpClientModule.class)
public class ApiServiceDataModule {

    @Provides
    public APIServiceData getAPIServiceData(Retrofit retrofit) {
        return retrofit.create(APIServiceData.class);
    }

    @MainComponentScope
    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient,
                             GsonConverterFactory gsonConverterFactory) {

        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://rss.itunes.apple.com/api/v1/us/")
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Provides
    public Gson gson() {
        return new GsonBuilder().create();
    }

    @Provides
    public GsonConverterFactory gsonConverterFactory(Gson gson){
        return GsonConverterFactory.create(gson);
    }
}

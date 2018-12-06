package com.yleaf.stas.testapplication.di.modules;

import android.content.Context;

import com.yleaf.stas.testapplication.di.annotations.ApplicationContext;
import com.yleaf.stas.testapplication.di.annotations.MainComponentScope;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import okhttp3.Request;
import okhttp3.Response;

@Module(includes = ApplicationContextModule.class)
public class OkHttpClientModule {

    @MainComponentScope
    @Provides
    public OkHttpClient okHttpClient(Cache cache, Interceptor interceptor) {

        return new OkHttpClient().newBuilder()
                .cache(cache)
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    public Cache cache(File cacheFile) {
        return new Cache(cacheFile, 10 * 1024 * 1024 );
    }

    @Provides
    public File file(@ApplicationContext Context context) {
        File file = new File(context.getCacheDir(), "HttpCache");
        file.mkdirs();
        return file;
    }

    @Provides
    public Interceptor interceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request origin = chain.request();
                Request request = origin.newBuilder()
                        .header("Content-type", "application/json")
                        .header("Cache-Control", "max-age=432000")
                        .removeHeader("Pragma")
                        .build();

                Response response = chain.proceed(request);
                response.cacheResponse();
                return response;
            }
        };
    }
}

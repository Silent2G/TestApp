package com.yleaf.stas.testapplication.di.modules;

import android.content.Context;

import com.yleaf.stas.testapplication.di.annotations.ApplicationContext;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationContextModule {

    Context context;

    public ApplicationContextModule(Context context) {
        this.context = context;
    }

    @ApplicationContext
    @Provides
    public Context context() {
        return context.getApplicationContext();
    }
}

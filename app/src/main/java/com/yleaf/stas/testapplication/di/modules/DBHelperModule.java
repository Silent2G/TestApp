package com.yleaf.stas.testapplication.di.modules;

import com.yleaf.stas.testapplication.db.Resource;
import com.yleaf.stas.testapplication.di.annotations.DatabaseInfo;

import dagger.Module;
import dagger.Provides;

@Module(includes = ApplicationContextModule.class)
public class DBHelperModule {

    @Provides
    @DatabaseInfo
    public String provideDatabaseName() {
        return Resource.DB_NAME;
    }

    @Provides
    @DatabaseInfo
    public Integer provideDatabaseVersion() {
        return Resource.DB_VERSION;
    }
}

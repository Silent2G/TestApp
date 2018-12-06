package com.yleaf.stas.testapplication.db.room;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.yleaf.stas.testapplication.di.components.AppContextComponent;
import com.yleaf.stas.testapplication.di.components.DBHelperComponent;
import com.yleaf.stas.testapplication.di.components.DaggerAppContextComponent;
import com.yleaf.stas.testapplication.di.components.DaggerDBHelperComponent;
import com.yleaf.stas.testapplication.di.modules.ApplicationContextModule;


public class App extends Application {

    public static App instance;
    private AppContextComponent appContextComponent;
    private AppDatabase database;
    private DBHelperComponent dbHelperComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        database = Room.databaseBuilder(this, AppDatabase.class,
                "com.yleaf.stas.testapplication.database")
                .build();

        appContextComponent = DaggerAppContextComponent.builder()
                .applicationContextModule(new ApplicationContextModule(this))
                .build();

        dbHelperComponent = DaggerDBHelperComponent.builder()
                .applicationContextModule(new ApplicationContextModule(this))
                .build();

        dbHelperComponent.inject(this);

    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public AppContextComponent getAppContextComponent() {
        return appContextComponent;
    }
}

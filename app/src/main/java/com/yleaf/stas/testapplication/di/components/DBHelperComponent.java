package com.yleaf.stas.testapplication.di.components;

import com.yleaf.stas.testapplication.db.room.App;
import com.yleaf.stas.testapplication.di.modules.DBHelperModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DBHelperModule.class})
public interface DBHelperComponent {
    void inject(App app);
}

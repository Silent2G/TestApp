package com.yleaf.stas.testapplication.di.components;

import android.content.Context;

import com.yleaf.stas.testapplication.data.ClearData;
import com.yleaf.stas.testapplication.data.GetData;
import com.yleaf.stas.testapplication.data.ParseAndStoreData;
import com.yleaf.stas.testapplication.di.annotations.ApplicationContext;
import com.yleaf.stas.testapplication.di.modules.ApplicationContextModule;
import com.yleaf.stas.testapplication.update.CheckDate;
import com.yleaf.stas.testapplication.update.CheckIsOnline;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationContextModule.class})
public interface AppContextComponent {
    void inject(GetData getData);
    void inject(CheckDate checkDate);
    void inject(CheckIsOnline checkIsOnline);
    void inject(ClearData clearData);
    void inject(ParseAndStoreData parseAndStoreData);
    @ApplicationContext
    Context getContext();
}

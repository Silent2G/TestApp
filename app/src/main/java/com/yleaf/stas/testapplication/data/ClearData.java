package com.yleaf.stas.testapplication.data;

import android.content.Context;

import com.yleaf.stas.testapplication.db.room.App;
import com.yleaf.stas.testapplication.db.service.BookService;
import com.yleaf.stas.testapplication.db.service.MovieService;
import com.yleaf.stas.testapplication.db.service.PodcastService;
import com.yleaf.stas.testapplication.di.annotations.ApplicationContext;

import javax.inject.Inject;

public class ClearData {

    @ApplicationContext
    public Context context;

    public ClearData() {

        context = App.getInstance().getAppContextComponent().getContext();
    }

    public void clearData() {
        new BookService(context).deleteAll();
        new MovieService(context).deleteAll();
        new PodcastService(context).deleteAll();
    }
}

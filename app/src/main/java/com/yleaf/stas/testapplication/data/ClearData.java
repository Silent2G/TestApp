package com.yleaf.stas.testapplication.data;

import android.content.Context;

import com.yleaf.stas.testapplication.db.service.BookService;
import com.yleaf.stas.testapplication.db.service.MovieService;
import com.yleaf.stas.testapplication.db.service.PodcastService;

public class ClearData {

    private Context context;

    public ClearData(Context context) {
        this.context = context;
    }

    public void clearData() {
        new BookService(context).deleteAll();
        new MovieService(context).deleteAll();
        new PodcastService(context).deleteAll();
    }
}

package com.yleaf.stas.testapplication.update;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yleaf.stas.testapplication.db.room.App;
import com.yleaf.stas.testapplication.di.annotations.ApplicationContext;

import javax.inject.Inject;

public class CheckIsOnline {

    @ApplicationContext
    @Inject
    public Context context;

    public CheckIsOnline() {
        App.getInstance().getAppContextComponent().inject(this);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
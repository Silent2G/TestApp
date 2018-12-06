package com.yleaf.stas.testapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.yleaf.stas.testapplication.di.annotations.ApplicationContext;
import com.yleaf.stas.testapplication.di.annotations.DatabaseInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DBHelper extends SQLiteOpenHelper {

    @Inject
    public DBHelper(@Nullable @ApplicationContext Context context,
                    @DatabaseInfo String dbName,
                    @DatabaseInfo Integer version) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Resource.Book.CREATE_TABLE);
        sqLiteDatabase.execSQL(Resource.Movie.CREATE_TABLE);
        sqLiteDatabase.execSQL(Resource.Podcast.CREATE_TABLE);
        sqLiteDatabase.execSQL(Resource.Favorite.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
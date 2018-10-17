package com.yleaf.stas.testapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, Resource.DB_NAME, null, Resource.DB_VERSION);
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

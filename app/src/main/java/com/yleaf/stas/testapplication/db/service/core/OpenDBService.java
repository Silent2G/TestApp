package com.yleaf.stas.testapplication.db.service.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yleaf.stas.testapplication.db.DBHelper;
import com.yleaf.stas.testapplication.db.Resource;
import com.yleaf.stas.testapplication.di.annotations.ApplicationContext;
import com.yleaf.stas.testapplication.di.annotations.DatabaseInfo;

import javax.inject.Inject;

public abstract class OpenDBService {

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public OpenDBService(Context context) {
        this.context = context;
    }

    public SQLiteDatabase getSqLiteDatabase() {
        return sqLiteDatabase;
    }

    protected boolean isOpen() {
        return sqLiteDatabase != null
                && dbHelper != null
                && sqLiteDatabase.isOpen();
    }

    protected void open() {
        if(sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper = new DBHelper(context, Resource.DB_NAME, Resource.DB_VERSION);
            sqLiteDatabase = dbHelper.getWritableDatabase();
        }
    }

    protected void close() {
        if(dbHelper != null) {
            dbHelper.close();
        }
    }
}

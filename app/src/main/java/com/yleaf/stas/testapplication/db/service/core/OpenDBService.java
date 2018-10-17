package com.yleaf.stas.testapplication.db.service.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yleaf.stas.testapplication.db.DBHelper;

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
            dbHelper = new DBHelper(context);
            sqLiteDatabase = dbHelper.getWritableDatabase();
        }
    }

    protected void close() {
        if(dbHelper != null) {
            dbHelper.close();
        }
    }
}

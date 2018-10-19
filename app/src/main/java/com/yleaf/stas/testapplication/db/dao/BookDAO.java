package com.yleaf.stas.testapplication.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yleaf.stas.testapplication.db.Resource;
import com.yleaf.stas.testapplication.db.dao.core.DAO;
import com.yleaf.stas.testapplication.models.Data;

import java.util.ArrayList;
import java.util.List;

public class BookDAO implements DAO<Data> {
    private SQLiteDatabase sqLiteDatabase;
    private static final String TAG = BookDAO.class.getSimpleName();

    public BookDAO(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @Override
    public void save(Data data) {
        sqLiteDatabase.insert(Resource.Book.TABLE_NAME, null, bookContentValues(data));

        Log.i(TAG, data.getId() + " saved");
    }

    private ContentValues bookContentValues(Data data) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Resource.Book.ID, data.getId());
        contentValues.put(Resource.Book.KIND, data.getKind());
        contentValues.put(Resource.Book.ARTIST_NAME, data.getArtistName());
        contentValues.put(Resource.Book.NAME, data.getName());
        contentValues.put(Resource.Book.ART_WORK_URL, data.getArtworkUrl100());

        return contentValues;
    }

    @Override
    public void deleteById(int id) {
        sqLiteDatabase.execSQL("delete from " + Resource.Book.TABLE_NAME + " where "
                + Resource.Book.ID + " = " + String.valueOf(id));
    }

    @Override
    public void deleteAll() {
        sqLiteDatabase.execSQL("delete from " + Resource.Book.TABLE_NAME);
    }

    @Override
    public List<Data> getAll() {
        Cursor cursor = sqLiteDatabase.rawQuery(
                "select * from " + Resource.Book.TABLE_NAME,
                null);

        return parseCursor(cursor);
    }

    @Override
    public List<Data> parseCursor(Cursor cursor) {
        List<Data> books = new ArrayList<>();
        if(cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(Resource.Book.ID));
                String kind = cursor.getString(cursor.getColumnIndex(Resource.Book.KIND));
                String artistName = cursor.getString(cursor.getColumnIndex(Resource.Book.ARTIST_NAME));
                String name = cursor.getString(cursor.getColumnIndex(Resource.Book.NAME));
                String artWorkUrl = cursor.getString(cursor.getColumnIndex(Resource.Book.ART_WORK_URL));

                books.add(new Data(id, kind, artistName, name, artWorkUrl));

            } while (cursor.moveToNext());
        }

        if(cursor != null) {
            cursor.close();
        }
        return books;
    }

    @Override
    public boolean isEmpty() {
        boolean empty = true;
        Cursor cursor = sqLiteDatabase.rawQuery(
                "select count(*) from " + Resource.Book.TABLE_NAME,
                null);
        if(cursor != null && cursor.moveToFirst()) {
            empty = (cursor.getInt(0) == 0);
        }

        if(cursor != null) {
            cursor.close();
        }
        return empty;
    }

    @Override
    public List<Data> getAllByKind(String kind) {
        return null;
    }
}

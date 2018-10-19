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

public class FavoriteDAO implements DAO<Data> {

    private SQLiteDatabase sqLiteDatabase;
    private static final String TAG = FavoriteDAO.class.getSimpleName();

    public FavoriteDAO(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @Override
    public void save(Data data) {
        sqLiteDatabase.insert(Resource.Favorite.TABLE_NAME, null, favoriteContentValues(data));

        Log.i(TAG, data.getId() + " saved");
    }

    private ContentValues favoriteContentValues(Data data) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Resource.Favorite.ID, data.getId());
        contentValues.put(Resource.Favorite.KIND, data.getKind());
        contentValues.put(Resource.Favorite.ARTIST_NAME, data.getArtistName());
        contentValues.put(Resource.Favorite.NAME, data.getName());
        contentValues.put(Resource.Favorite.ART_WORK_URL, data.getArtworkUrl100());

        return contentValues;
    }

    @Override
    public void deleteById(int id) {
        sqLiteDatabase.execSQL("delete from " + Resource.Favorite.TABLE_NAME + " where "
                + Resource.Favorite.ID + " = " + String.valueOf(id));
    }

    @Override
    public void deleteAll() {
        sqLiteDatabase.execSQL("delete from " + Resource.Favorite.TABLE_NAME);
    }

    @Override
    public List<Data> getAll() {
        Cursor cursor = sqLiteDatabase.rawQuery(
                "select * from "
                        + Resource.Favorite.TABLE_NAME,
                null);

        return parseCursor(cursor);
    }

    @Override
    public List<Data> parseCursor(Cursor cursor) {
        List<Data> favorite = new ArrayList<>();
        if(cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(Resource.Favorite.ID));
                String kind = cursor.getString(cursor.getColumnIndex(Resource.Favorite.KIND));
                String artistName = cursor.getString(cursor.getColumnIndex(Resource.Favorite.ARTIST_NAME));
                String name = cursor.getString(cursor.getColumnIndex(Resource.Favorite.NAME));
                String artWorkUrl = cursor.getString(cursor.getColumnIndex(Resource.Favorite.ART_WORK_URL));

                favorite.add(new Data(id, kind, artistName, name, artWorkUrl));

            } while (cursor.moveToNext());
        }

        if(cursor != null) {
            cursor.close();
        }
        return favorite;
    }

    @Override
    public boolean isEmpty() {
        boolean empty = true;
        Cursor cursor = sqLiteDatabase.rawQuery(
                "select count(*) from " + Resource.Favorite.TABLE_NAME,
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
        Cursor cursor = sqLiteDatabase.rawQuery(
                "select * from "
                        + Resource.Favorite.TABLE_NAME + " where " + Resource.Favorite.KIND + " = " + kind,
                null);

        return parseCursor(cursor);
    }
}

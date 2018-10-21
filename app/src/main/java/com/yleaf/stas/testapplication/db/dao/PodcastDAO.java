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

public class PodcastDAO implements DAO<Data> {

    private SQLiteDatabase sqLiteDatabase;
    private static final String TAG = PodcastDAO.class.getSimpleName();

    public PodcastDAO(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @Override
    public void save(Data data) {
        sqLiteDatabase.insert(Resource.Podcast.TABLE_NAME, null, podcastContentValues(data));

        Log.i(TAG, data.getId() + " saved");
    }

    private ContentValues podcastContentValues(Data data) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Resource.Podcast.ID, data.getId());
        contentValues.put(Resource.Podcast.KIND, data.getKind());
        contentValues.put(Resource.Podcast.ARTIST_NAME, data.getArtistName());
        contentValues.put(Resource.Podcast.NAME, data.getName());
        contentValues.put(Resource.Podcast.ART_WORK_URL, data.getArtworkUrl100());

        return contentValues;
    }

    @Override
    public void deleteById(int id) {
        sqLiteDatabase.execSQL("delete from " + Resource.Podcast.TABLE_NAME + " where "
                + Resource.Podcast.ID + " = " + String.valueOf(id));
    }

    @Override
    public void deleteAll() {
        sqLiteDatabase.execSQL("delete from " + Resource.Podcast.TABLE_NAME);
    }

    @Override
    public ArrayList<Data> getAll() {
        Cursor cursor = sqLiteDatabase.rawQuery(
                "select * from "
                        + Resource.Podcast.TABLE_NAME,
                null);

        return parseCursor(cursor);
    }

    @Override
    public ArrayList<Data> parseCursor(Cursor cursor) {
        ArrayList<Data> podcasts = new ArrayList<>();
        if(cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(Resource.Podcast.ID));
                String kind = cursor.getString(cursor.getColumnIndex(Resource.Podcast.KIND));
                String artistName = cursor.getString(cursor.getColumnIndex(Resource.Podcast.ARTIST_NAME));
                String name = cursor.getString(cursor.getColumnIndex(Resource.Podcast.NAME));
                String artWorkUrl = cursor.getString(cursor.getColumnIndex(Resource.Podcast.ART_WORK_URL));

                podcasts.add(new Data(id, kind, artistName, name, artWorkUrl));

            } while (cursor.moveToNext());
        }

        if(cursor != null) {
            cursor.close();
        }
        return podcasts;
    }

    @Override
    public boolean isEmpty() {
        boolean empty = true;
        Cursor cursor = sqLiteDatabase.rawQuery(
                "select count(*) from " + Resource.Podcast.TABLE_NAME,
                null);
        if(cursor != null && cursor.moveToFirst()) {
            empty = (cursor.getInt(0) == 0);
        }

        if(cursor != null) {
            cursor.close();
        }
        return empty;
    }
}

package com.yleaf.stas.testapplication.db.dao;

import android.database.Cursor;

import com.yleaf.stas.testapplication.db.dao.core.DAO;
import com.yleaf.stas.testapplication.models.Data;

import java.util.List;

public class MovieDAO implements DAO<Data> {

    @Override
    public void save(Data data) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Data> getAll() {
        return null;
    }

    @Override
    public List<Data> parseCursor(Cursor cursor) {
        return null;
    }
}

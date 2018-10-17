package com.yleaf.stas.testapplication.db.service;

import android.content.Context;

import com.yleaf.stas.testapplication.db.service.core.OpenDBService;
import com.yleaf.stas.testapplication.db.service.core.Service;
import com.yleaf.stas.testapplication.models.Data;

import java.util.List;

public class FavoriteService extends OpenDBService implements Service<Data> {

    public FavoriteService(Context context) {
        super(context);
    }

    @Override
    public void save(Data data) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public List<Data> getAll() {
        return null;
    }
}

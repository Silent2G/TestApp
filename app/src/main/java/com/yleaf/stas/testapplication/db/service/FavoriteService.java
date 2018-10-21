package com.yleaf.stas.testapplication.db.service;

import android.content.Context;

import com.yleaf.stas.testapplication.db.dao.FavoriteDAO;
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
        try {
            if(!isOpen()) {
                open();
            }

            // save
            new FavoriteDAO(getSqLiteDatabase()).save(data);

        } finally {
            if(isOpen()) {
                close();
            }
        }
    }

    @Override
    public void deleteAll() {
        try {
            if(!isOpen()) {
                open();
            }

            // delete all records
            new FavoriteDAO(getSqLiteDatabase()).deleteAll();

        } finally {
            if(isOpen()) {
                close();
            }
        }
    }

    @Override
    public void deleteById(int id) {
        try {
            if(!isOpen()) {
                open();
            }

            // delete record by id
            new FavoriteDAO(getSqLiteDatabase()).deleteById(id);

        } finally {
            if(isOpen()) {
                close();
            }
        }
    }

    @Override
    public List<Data> getAll() {
        try {
            if(!isOpen()) {
                open();
            }

            // get all
            return new FavoriteDAO(getSqLiteDatabase()).getAll();

        } finally {
            if(isOpen()) {
                close();
            }
        }
    }

    @Override
    public boolean isEmpty() {
        try {
            if(!isOpen()) {
                open();
            }

            // is table empty
            return new FavoriteDAO(getSqLiteDatabase()).isEmpty();

        } finally {
            if(isOpen()) {
                close();
            }
        }
    }

    public List<Data> getAllByKind(String kind) {
        try {
            if(!isOpen()) {
                open();
            }

            // get all records with current kind
            return new FavoriteDAO(getSqLiteDatabase()).getAllByKind(kind);

        } finally {
            if(isOpen()) {
                close();
            }
        }
    }

    public boolean isEmptyCurrentKind(String kind) {
        try {
            if(!isOpen()) {
                open();
            }

            // is table include current kind objects
            return new FavoriteDAO(getSqLiteDatabase()).isEmptyCurrentKind(kind);

        } finally {
            if(isOpen()) {
                close();
            }
        }
    }

    public boolean isObjectStored(int id) {
        try {
            if(!isOpen()) {
                open();
            }

            // is object stored
            return new FavoriteDAO(getSqLiteDatabase()).isObjectStored(id);

        } finally {
            if(isOpen()) {
                close();
            }
        }
    }
}

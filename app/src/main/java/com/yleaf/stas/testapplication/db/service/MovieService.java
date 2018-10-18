package com.yleaf.stas.testapplication.db.service;

import android.content.Context;

import com.yleaf.stas.testapplication.db.dao.MovieDAO;
import com.yleaf.stas.testapplication.db.service.core.OpenDBService;
import com.yleaf.stas.testapplication.db.service.core.Service;
import com.yleaf.stas.testapplication.models.Data;

import java.util.List;

public class MovieService extends OpenDBService implements Service<Data> {

    public MovieService(Context context) {
        super(context);
    }

    @Override
    public void save(Data data) {
        try {
            if(!isOpen()) {
                open();
            }

            // save
            new MovieDAO(getSqLiteDatabase()).save(data);

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
            new MovieDAO(getSqLiteDatabase()).deleteAll();

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
            new MovieDAO(getSqLiteDatabase()).deleteById(id);

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
            return new MovieDAO(getSqLiteDatabase()).getAll();

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
            return new MovieDAO(getSqLiteDatabase()).isEmpty();

        } finally {
            if(isOpen()) {
                close();
            }
        }
    }
}

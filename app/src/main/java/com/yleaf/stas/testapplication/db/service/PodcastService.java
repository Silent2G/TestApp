package com.yleaf.stas.testapplication.db.service;

import android.content.Context;

import com.yleaf.stas.testapplication.db.dao.PodcastDAO;
import com.yleaf.stas.testapplication.db.service.core.OpenDBService;
import com.yleaf.stas.testapplication.db.service.core.Service;
import com.yleaf.stas.testapplication.models.Data;

import java.util.ArrayList;
import java.util.List;

public class PodcastService extends OpenDBService implements Service<Data> {

    public PodcastService(Context context) {
        super(context);
    }

    @Override
    public void save(Data data) {
        try {
            if(!isOpen()) {
                open();
            }

            // save
            new PodcastDAO(getSqLiteDatabase()).save(data);

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
            new PodcastDAO(getSqLiteDatabase()).deleteAll();

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
            new PodcastDAO(getSqLiteDatabase()).deleteById(id);

        } finally {
            if(isOpen()) {
                close();
            }
        }
    }

    @Override
    public ArrayList<Data> getAll() {
        try {
            if(!isOpen()) {
                open();
            }

            // get all
            return new PodcastDAO(getSqLiteDatabase()).getAll();

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
            return new PodcastDAO(getSqLiteDatabase()).isEmpty();

        } finally {
            if(isOpen()) {
                close();
            }
        }
    }
}

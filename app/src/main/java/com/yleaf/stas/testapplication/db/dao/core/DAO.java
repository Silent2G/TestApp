package com.yleaf.stas.testapplication.db.dao.core;

import android.database.Cursor;

import java.util.List;

public interface DAO<T> {
    void save(T t);
    void deleteById(int id);
    void deleteAll();
    boolean isEmpty();
    List<T> getAll();
    List<T> parseCursor(Cursor cursor);
}

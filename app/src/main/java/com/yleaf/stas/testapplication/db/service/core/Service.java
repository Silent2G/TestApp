package com.yleaf.stas.testapplication.db.service.core;

import java.util.List;

public interface Service<T> {
    void save(T t);
    void deleteAll();
    void deleteById(int id);
    List<T> getAll();
}

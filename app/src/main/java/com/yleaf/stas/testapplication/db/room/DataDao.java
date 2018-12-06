package com.yleaf.stas.testapplication.db.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.yleaf.stas.testapplication.models.Data;

import java.util.List;

@Dao
public interface DataDao {

    @Query("SELECT * FROM data")
    List<Data> getAll();

    @Query("SELECT * FROM data WHERE id = :id")
    Data getById(int id);

    @Insert
    void insert(Data data);

    @Update
    void  update(Data data);

    @Delete
    void delete(Data data);
}

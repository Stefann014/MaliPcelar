package com.example.malipcelar.activity.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.malipcelar.activity.domen.Pasa;

import java.util.List;

@Dao
public interface PasaDAO {

    @Insert
    void insert(Pasa pasa);

    @Update
    void update(Pasa pasa);

    @Delete
    void delete(Pasa pasa);

    @Query("SELECT * FROM pasa_table ORDER BY date(datum_od) DESC")
    LiveData<List<Pasa>> getAllPase();


}

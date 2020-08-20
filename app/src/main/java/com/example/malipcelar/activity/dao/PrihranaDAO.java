package com.example.malipcelar.activity.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.malipcelar.activity.domen.Prihrana;

import java.util.List;

@Dao
public interface PrihranaDAO {
    @Insert
    void insert(Prihrana prihrana);

    @Update
    void update(Prihrana prihrana);

    @Delete
    void delete(Prihrana prihrana);

    @Query("SELECT * FROM prihrana_table WHERE kosnica_id = :kosnicaID AND pcelinjak_id = :pcelinjakID ORDER BY date(datum_prihrane) DESC")
    LiveData<List<Prihrana>> getAllPrihranaZaKosnicu(int kosnicaID, int pcelinjakID);
}

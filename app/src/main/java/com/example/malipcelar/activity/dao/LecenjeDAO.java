package com.example.malipcelar.activity.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.malipcelar.activity.domen.Lecenje;

import java.util.List;

@Dao
public interface LecenjeDAO {

    @Insert
    void insert(Lecenje lecenje);

    @Update
    void update(Lecenje lecenje);

    @Delete
    void delete(Lecenje lecenje);

    @Query("DELETE FROM lecenje_table")
    void deleteAllLecenje();

    @Query("SELECT * FROM lecenje_table WHERE kosnica_id = :kosnicaID AND pcelinjak_id = :pcelinjakID ORDER BY date(datum_lecenja) DESC")
    LiveData<List<Lecenje>> getAllLecenjaZaKosnicu(int kosnicaID, int pcelinjakID);

    @Query("SELECT max(date(datum_lecenja)) FROM lecenje_table WHERE kosnica_id = :kosnicaID AND pcelinjak_id = :pcelinjakID")
    LiveData<String> getMaxDatumLecenjaZaKosnicu(int kosnicaID, int pcelinjakID);

}

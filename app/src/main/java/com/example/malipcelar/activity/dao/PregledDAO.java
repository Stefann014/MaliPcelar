package com.example.malipcelar.activity.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.malipcelar.activity.domen.Pregled;

import java.util.List;

@Dao
public interface PregledDAO {

    @Insert
    void insert(Pregled pregled);

    @Update
    void update(Pregled pregled);

    @Delete
    void delete(Pregled pregled);

    @Query("DELETE FROM pregled_table")
    void deleteAllPregledi();


    @Query("SELECT * FROM pregled_table WHERE kosnica_id = :kosnicaID AND pcelinjak_id = :pcelinjakID ORDER BY date(datum_pregleda) DESC")
    LiveData<List<Pregled>> getAllPreglediZaKosnicu(int kosnicaID, int pcelinjakID);

    @Query("SELECT max(date(datum_pregleda)) FROM pregled_table WHERE kosnica_id = :kosnicaID AND pcelinjak_id = :pcelinjakID")
    LiveData<String> getMaxDatumPregledaZaKosnicu(int kosnicaID, int pcelinjakID);
}

package com.example.malipcelar.activity.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.malipcelar.activity.domen.Kosnica;

import java.util.List;

@Dao
public interface KosnicaDAO {
    @Insert
    void insert(Kosnica kosnica);

    @Update
    void update(Kosnica kosnica);

    @Delete
    void delete(Kosnica kosnica);

    @Query("DELETE FROM kosnica_table")
    void deleteAllKosnice();

    @Query("SELECT * FROM kosnica_table WHERE rb_kosnice = :rb AND pcelinjak = :kosnica")
    LiveData<Kosnica> getKosnicaByRB(int kosnica,int rb);

    @Query("SELECT * FROM kosnica_table WHERE pcelinjak = :pcelinjak ORDER BY rb_kosnice ASC")
    LiveData<List<Kosnica>> getAllKosnice(int pcelinjak);

    @Query("SELECT rb_kosnice FROM kosnica_table WHERE pcelinjak = :pcelinjak")
    LiveData<List<Integer>> getAllRBKosniceZaPcelinjak(int pcelinjak);

    @Query("UPDATE kosnica_table SET rb_kosnice=:noviRb WHERE rb_kosnice = :stariRb")
    void updateRb(int stariRb,int noviRb);
}

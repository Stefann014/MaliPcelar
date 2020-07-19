package com.example.malipcelar.activity.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.malipcelar.activity.domen.OpstaNapomena;

import java.util.List;

@Dao
public interface OpsteNapomeneDAO {

    @Insert
    void insert(OpstaNapomena opstaNapomena);

    @Update
    void update(OpstaNapomena opstaNapomena);

    @Delete
    void delete(OpstaNapomena opstaNapomena);

    @Query("DELETE FROM opsta_napomena_table")
    void deleteAllOpsteNapomene();


    @Query("SELECT * FROM opsta_napomena_table ORDER BY date(datumNapomene) DESC")
    LiveData<List<OpstaNapomena>> getAllOpsteNapomene();

}

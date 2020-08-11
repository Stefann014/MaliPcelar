package com.example.malipcelar.activity.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.malipcelar.activity.domen.Kosnica;
import com.example.malipcelar.activity.pomocneKlase.KosnicaIDatumi;

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
    LiveData<Kosnica> getKosnicaByRB(int kosnica, int rb);

    @Query("SELECT * FROM kosnica_table WHERE pcelinjak = :pcelinjak ORDER BY rb_kosnice ASC")
    LiveData<List<Kosnica>> getAllKosnice(int pcelinjak);

    @Query("SELECT k.rb_kosnice AS redniBrojKosnice, k.pcelinjak AS rednibrojPcelinjaka, k.naziv_kosnice AS nazivKosnice, k.godina_proizvodnje_matice AS godinaProizvodnjeMatice, k.selekcionisana AS selekcionisana, k.prirodna AS prirodna, k.bolesti AS bolesti, k.napomena AS napomena, max(date(p.datum_pregleda)) AS maxDatumPregleda, max(date(l.datum_lecenja)) AS maxDatumLecenja, max(date(pr.datum_prihrane)) AS maxDatumPrihrane FROM kosnica_table k LEFT OUTER JOIN pregled_table p ON (k.rb_kosnice = p.kosnica_id AND k.pcelinjak = p.pcelinjak_id) LEFT OUTER JOIN lecenje_table l ON (k.rb_kosnice = l.kosnica_id AND k.pcelinjak = l.pcelinjak_id) LEFT OUTER JOIN prihrana_table pr ON (k.rb_kosnice = pr.kosnica_id AND k.pcelinjak = pr.pcelinjak_id) WHERE k.pcelinjak = :pcelinjakID GROUP BY k.rb_kosnice,k.pcelinjak")
    LiveData<List<KosnicaIDatumi>> getAllKosniceIDatumeZaPcelinjak(int pcelinjakID);

    @Query("SELECT rb_kosnice FROM kosnica_table WHERE pcelinjak = :pcelinjak")
    LiveData<List<Integer>> getAllRBKosniceZaPcelinjak(int pcelinjak);

    @Query("UPDATE kosnica_table SET rb_kosnice=:noviRb WHERE rb_kosnice = :stariRb")
    void updateRb(int stariRb, int noviRb);
}

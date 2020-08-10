package com.example.malipcelar.activity.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.pomocneKlase.KlasaBilans;
import com.example.malipcelar.activity.pomocneKlase.PcelinjakIDatumi;

import java.util.List;

@Dao
public interface PcelinjakDAO {
    @Insert
    void insert(Pcelinjak pcelinjak);

    @Update
    void update(Pcelinjak pcelinjak);

    @Delete
    void delete(Pcelinjak pcelinjak);

    @Query("DELETE FROM pcelinjak_table")
    void deleteAllPcelinjaci();

    @Query("SELECT * FROM pcelinjak_table WHERE rb_pcelinjaka = :rb")
    LiveData<Pcelinjak> getPcelinjakByRB(int rb);

    @Query("SELECT * FROM pcelinjak_table ORDER BY rb_pcelinjaka ASC")
    LiveData<List<Pcelinjak>> getAllPcelinjaci();

    @Query("SELECT rb_pcelinjaka FROM pcelinjak_table")
    LiveData<List<Integer>> getAllPcelinjakRB();

    @Query("UPDATE pcelinjak_table SET rb_pcelinjaka=:noviRb WHERE rb_pcelinjaka = :stariRb")
    void updateRb(int stariRb, int noviRb);

    @Query("SELECT (p.rb_pcelinjaka || '. ' || p.naziv_pcelinjaka) AS pcelinjak, p.slika as slika, max(date(pr.datum_pregleda)) AS maxDatumPregled, max(date(l.datum_lecenja)) AS maxDatumLecenja, max(date(prih.datum_prihrane)) AS maxDatumPrihrane FROM pcelinjak_table p LEFT OUTER JOIN kosnica_table k ON (p.rb_pcelinjaka = k.pcelinjak) LEFT OUTER JOIN pregled_table pr ON (k.rb_kosnice = pr.kosnica_id AND p.rb_pcelinjaka = pr.pcelinjak_id) LEFT OUTER JOIN lecenje_table l ON (k.rb_kosnice = l.kosnica_id AND p.rb_pcelinjaka = l.pcelinjak_id) LEFT OUTER JOIN prihrana_table prih ON (k.rb_kosnice = prih.kosnica_id AND p.rb_pcelinjaka = prih.pcelinjak_id) GROUP BY p.rb_pcelinjaka")
    LiveData<List<PcelinjakIDatumi>> getPcelinjakIDatumi();

    @Query("SELECT (p.rb_pcelinjaka || '. ' || p.naziv_pcelinjaka) AS rbINazivPcelinjaka, p.lokacija AS lokacija, p.slika AS slikaPcelinjaka, SUM(prikupljeno_meda) AS ukupnoMeda, SUM(prikupljeno_polena) AS ukupnoPolena, SUM(prikupljeno_propolisa) AS ukupnoPropolisa, SUM(prikupljeno_maticnog_mleca) AS ukupnoMaticnogMleca, SUM(prikupljena_perga) AS ukupnoPrikupljenePerge FROM pcelinjak_table p LEFT OUTER JOIN pasa_table pa ON (p.rb_pcelinjaka  = pa.pcelinjak_id) GROUP BY pcelinjak_id ORDER BY p.rb_pcelinjaka")
    LiveData<List<KlasaBilans>> getAllBilans();
}

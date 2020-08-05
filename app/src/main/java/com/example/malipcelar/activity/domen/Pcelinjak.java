package com.example.malipcelar.activity.domen;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "pcelinjak_table")
public class Pcelinjak implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "rb_pcelinjaka")
    private int redniBrojPcelinjaka;

    @ColumnInfo(name = "naziv_pcelinjaka")
    private String nazivPcelinjaka;
    @ColumnInfo(name = "lokacija")
    private String lokacija;
    @ColumnInfo(name = "nadmorska_visina")
    private String nadmorskaVisina;
    @ColumnInfo(name = "slika")
    private String slika;
    @ColumnInfo(name = "ukuono_meda")
    private double ukupnoMeda;
    @ColumnInfo(name = "ukupno_polena")
    private double ukupnoPolena;
    @ColumnInfo(name = "ukupno_propolisa")
    private double ukupnoPropolisa;
    @ColumnInfo(name = "ukupno_maticnog_mleca")
    private double ukupnoMaticnogMleca;
    @ColumnInfo(name = "ukupno_prikupljene_perge")
    private double ukupnoPrikupljenePerge;


    public Pcelinjak() {
    }

    public Pcelinjak(int redniBrojPcelinjaka, String nazivPcelinjaka, String lokacija, String nadmorskaVisina, String slika) {
        this.redniBrojPcelinjaka = redniBrojPcelinjaka;
        this.nazivPcelinjaka = nazivPcelinjaka;
        this.lokacija = lokacija;
        this.nadmorskaVisina = nadmorskaVisina;
        this.slika = slika;
        ukupnoMeda = 0;
        ukupnoPolena = 0;
        ukupnoPropolisa = 0;
        ukupnoMaticnogMleca = 0;
        ukupnoPrikupljenePerge = 0;
    }

    public double getUkupnoMeda() {
        return ukupnoMeda;
    }

    public void setUkupnoMeda(double ukupnoMeda) {
        this.ukupnoMeda = ukupnoMeda;
    }

    public double getUkupnoPolena() {
        return ukupnoPolena;
    }

    public void setUkupnoPolena(double ukupnoPolena) {
        this.ukupnoPolena = ukupnoPolena;
    }

    public double getUkupnoPropolisa() {
        return ukupnoPropolisa;
    }

    public void setUkupnoPropolisa(double ukupnoPropolisa) {
        this.ukupnoPropolisa = ukupnoPropolisa;
    }

    public double getUkupnoMaticnogMleca() {
        return ukupnoMaticnogMleca;
    }

    public void setUkupnoMaticnogMleca(double ukupnoMaticnogMleca) {
        this.ukupnoMaticnogMleca = ukupnoMaticnogMleca;
    }

    public double getUkupnoPrikupljenePerge() {
        return ukupnoPrikupljenePerge;
    }

    public void setUkupnoPrikupljenePerge(double ukupnoPrikupljenePerge) {
        this.ukupnoPrikupljenePerge = ukupnoPrikupljenePerge;
    }

    public int getRedniBrojPcelinjaka() {
        return redniBrojPcelinjaka;
    }

    public void setRedniBrojPcelinjaka(int redniBrojPcelinjaka) {
        this.redniBrojPcelinjaka = redniBrojPcelinjaka;
    }

    public String getNazivPcelinjaka() {
        return nazivPcelinjaka;
    }

    public void setNazivPcelinjaka(String nazivPcelinjaka) {
        this.nazivPcelinjaka = nazivPcelinjaka;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    public String getNadmorskaVisina() {
        return nadmorskaVisina;
    }

    public void setNadmorskaVisina(String nadmorskaVisina) {
        this.nadmorskaVisina = nadmorskaVisina;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    @Override
    public String toString() {
        return redniBrojPcelinjaka + ". " +
                nazivPcelinjaka;
    }
}

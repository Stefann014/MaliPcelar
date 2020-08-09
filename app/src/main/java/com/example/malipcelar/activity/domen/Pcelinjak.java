package com.example.malipcelar.activity.domen;


import androidx.annotation.NonNull;
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


    public Pcelinjak() {
    }

    public Pcelinjak(int redniBrojPcelinjaka, String nazivPcelinjaka, String lokacija, String nadmorskaVisina, String slika) {
        this.redniBrojPcelinjaka = redniBrojPcelinjaka;
        this.nazivPcelinjaka = nazivPcelinjaka;
        this.lokacija = lokacija;
        this.nadmorskaVisina = nadmorskaVisina;
        this.slika = slika;

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

    @NonNull
    @Override
    public String toString() {
        return redniBrojPcelinjaka + ". " +
                nazivPcelinjaka;
    }
}

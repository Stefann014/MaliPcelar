package com.example.malipcelar.activity.domen;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.io.Serializable;
import java.sql.Timestamp;

import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName = "kosnica_table", primaryKeys = {"rb_kosnice", "pcelinjak"},foreignKeys = @ForeignKey(entity = Pcelinjak.class,
        parentColumns = "rb_pcelinjaka",
        childColumns = "pcelinjak", onUpdate = CASCADE, onDelete = CASCADE))
public class Kosnica implements Serializable {

    @ColumnInfo(name = "rb_kosnice")
    int redniBrojKosnice;

    @ColumnInfo(name = "pcelinjak")
    int rednibrojPcelinjaka;

    @ColumnInfo(name = "naziv_kosnice")
    String nazivKosnice;
    @ColumnInfo(name = "godina_proizvodnje_matice")
    String godinaProizvodnjeMatice;
    @ColumnInfo(name = "selekciona")
    Boolean selekciona;
    @ColumnInfo(name = "prirodna")
    Boolean prirodna;
    @ColumnInfo(name = "bolesti")
    String bolesti;
    @ColumnInfo(name = "napomena")
    String napomena;


    public Kosnica() {
    }

    public Kosnica(int redniBrojKosnice, int rednibrojPcelinjaka, String naziv, String godinaProizvodnjeMatice, Boolean selekciona, Boolean prirodna, String bolesti, String napomena) {
        this.redniBrojKosnice = redniBrojKosnice;
        this.rednibrojPcelinjaka = rednibrojPcelinjaka;
        this.nazivKosnice = naziv;
        this.godinaProizvodnjeMatice = godinaProizvodnjeMatice;
        this.selekciona = selekciona;
        this.prirodna = prirodna;
        this.bolesti = bolesti;
        this.napomena = napomena;
    }

    public int getRedniBrojKosnice() {
        return redniBrojKosnice;
    }

    public void setRedniBrojKosnice(int redniBrojKosnice) {
        this.redniBrojKosnice = redniBrojKosnice;
    }

    public String getNazivKosnice() {
        return nazivKosnice;
    }

    public void setNazivKosnice(String nazivKosnice) {
        this.nazivKosnice = nazivKosnice;
    }

    public String getGodinaProizvodnjeMatice() {
        return godinaProizvodnjeMatice;
    }

    public void setGodinaProizvodnjeMatice(String godinaProizvodnjeMatice) {
        this.godinaProizvodnjeMatice = godinaProizvodnjeMatice;
    }

    public Boolean getSelekciona() {
        return selekciona;
    }

    public void setSelekciona(Boolean selekciona) {
        this.selekciona = selekciona;
    }

    public Boolean getPrirodna() {
        return prirodna;
    }

    public void setPrirodna(Boolean prirodna) {
        this.prirodna = prirodna;
    }

    public String getBolesti() {
        return bolesti;
    }

    public void setBolesti(String bolesti) {
        this.bolesti = bolesti;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public int getRednibrojPcelinjaka() {
        return rednibrojPcelinjaka;
    }

    public void setRednibrojPcelinjaka(int rednibrojPcelinjaka) {
        this.rednibrojPcelinjaka = rednibrojPcelinjaka;
    }

    @Override
    public String toString() {
        if (nazivKosnice == null || nazivKosnice.equals("")) {
            return redniBrojKosnice + ". Kosnica";
        }
        return redniBrojKosnice + ". " + nazivKosnice;
    }
}

package com.example.malipcelar.activity.domen;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "pasa_table", foreignKeys = {
        @ForeignKey(entity = Pcelinjak.class, parentColumns = {"rb_pcelinjaka"},
                childColumns = {"pcelinjak_id"}, onUpdate = CASCADE, onDelete = CASCADE)})
public class Pasa implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pasa_id")
    int pasaID;
    @ColumnInfo(name = "pcelinjak_id")
    int pcelinjakID;
    @ColumnInfo(name = "datum_od")
    String datumOd;
    @ColumnInfo(name = "datum_do")
    String datumDo;
    @ColumnInfo(name = "prikupljeno_meda")
    double prikupljenoMeda;
    @ColumnInfo(name = "prikupljeno_polena")
    double prikupljenoPolena;
    @ColumnInfo(name = "prikupljeno_propolisa")
    double prikupljenoPropolisa;
    @ColumnInfo(name = "prikupljeno_maticnog_mleca")
    double prikupljenoMaticnogMleca;
    @ColumnInfo(name = "prikupljena_perga")
    double prikupljenaPerga;
    @ColumnInfo(name = "napomena_pasa")
    String napomena;

    public Pasa() {
    }

    public Pasa(int pcelinjakID, String datumOd, String datumDo, double prikupljenoMeda, double prikupljenoPolena, double prikupljenoPropolisa, double prikupljenoMaticnogMleca, double prikupljenaPerga, String napomena) {
        this.pcelinjakID = pcelinjakID;
        this.datumOd = datumOd;
        this.datumDo = datumDo;
        this.prikupljenoMeda = prikupljenoMeda;
        this.prikupljenoPolena = prikupljenoPolena;
        this.prikupljenoPropolisa = prikupljenoPropolisa;
        this.prikupljenoMaticnogMleca = prikupljenoMaticnogMleca;
        this.prikupljenaPerga = prikupljenaPerga;
        this.napomena = napomena;
    }

    public int getPasaID() {
        return pasaID;
    }

    public void setPasaID(int pasaID) {
        this.pasaID = pasaID;
    }

    public int getPcelinjakID() {
        return pcelinjakID;
    }

    public void setPcelinjakID(int pcelinjakID) {
        this.pcelinjakID = pcelinjakID;
    }

    public String getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(String datumOd) {
        this.datumOd = datumOd;
    }

    public String getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(String datumDo) {
        this.datumDo = datumDo;
    }

    public double getPrikupljenoMeda() {
        return prikupljenoMeda;
    }

    public void setPrikupljenoMeda(double prikupljenoMeda) {
        this.prikupljenoMeda = prikupljenoMeda;
    }

    public double getPrikupljenoPolena() {
        return prikupljenoPolena;
    }

    public void setPrikupljenoPolena(double prikupljenoPolena) {
        this.prikupljenoPolena = prikupljenoPolena;
    }

    public double getPrikupljenoPropolisa() {
        return prikupljenoPropolisa;
    }

    public void setPrikupljenoPropolisa(double prikupljenoPropolisa) {
        this.prikupljenoPropolisa = prikupljenoPropolisa;
    }

    public double getPrikupljenoMaticnogMleca() {
        return prikupljenoMaticnogMleca;
    }

    public void setPrikupljenoMaticnogMleca(double prikupljenoMaticnogMleca) {
        this.prikupljenoMaticnogMleca = prikupljenoMaticnogMleca;
    }

    public double getPrikupljenaPerga() {
        return prikupljenaPerga;
    }

    public void setPrikupljenaPerga(double prikupljenaPerga) {
        this.prikupljenaPerga = prikupljenaPerga;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    @NonNull
    @Override
    public String toString() {
        return datumOd + " " + datumDo;
    }
}

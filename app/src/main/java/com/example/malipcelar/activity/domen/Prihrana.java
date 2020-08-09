package com.example.malipcelar.activity.domen;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "prihrana_table", foreignKeys = {
        @ForeignKey(entity = Kosnica.class, parentColumns = {"rb_kosnice", "pcelinjak"},
                childColumns = {"kosnica_id", "pcelinjak_id"}, onUpdate = CASCADE, onDelete = CASCADE)})
public class Prihrana implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "prihrana_id")
    int prihranaID;
    @ColumnInfo(name = "kosnica_id")
    int kosnicaID;
    @ColumnInfo(name = "pcelinjak_id")
    int pcelinjakID;
    @ColumnInfo(name = "datum_prihrane")
    String datumPrihrane;
    @ColumnInfo(name = "vrsta_prihrane")
    String vrstaPrihrane;
    @ColumnInfo(name = "kolicina_prihrane")
    double kolicinaPrihrane;

    public Prihrana() {
    }

    public Prihrana(int kosnicaID, int pcelinjakID, String datumPrihrane, String vrstaPrihrane, double kolicinaPrihrane) {
        this.kosnicaID = kosnicaID;
        this.pcelinjakID = pcelinjakID;
        this.datumPrihrane = datumPrihrane;
        this.vrstaPrihrane = vrstaPrihrane;
        this.kolicinaPrihrane = kolicinaPrihrane;
    }

    public int getPrihranaID() {
        return prihranaID;
    }

    public void setPrihranaID(int prihranaID) {
        this.prihranaID = prihranaID;
    }

    public int getKosnicaID() {
        return kosnicaID;
    }

    public void setKosnicaID(int kosnicaID) {
        this.kosnicaID = kosnicaID;
    }

    public int getPcelinjakID() {
        return pcelinjakID;
    }

    public void setPcelinjakID(int pcelinjakID) {
        this.pcelinjakID = pcelinjakID;
    }

    public String getDatumPrihrane() {
        return datumPrihrane;
    }

    public void setDatumPrihrane(String datumPrihrane) {
        this.datumPrihrane = datumPrihrane;
    }

    public String getVrstaPrihrane() {
        return vrstaPrihrane;
    }

    public void setVrstaPrihrane(String vrstaPrihrane) {
        this.vrstaPrihrane = vrstaPrihrane;
    }

    public double getKolicinaPrihrane() {
        return kolicinaPrihrane;
    }

    public void setKolicinaPrihrane(double kolicinaPrihrane) {
        this.kolicinaPrihrane = kolicinaPrihrane;
    }

    @NonNull
    @Override
    public String toString() {
        return kosnicaID + " " + pcelinjakID + " " + datumPrihrane + " " + vrstaPrihrane + " " + kolicinaPrihrane;
    }
}

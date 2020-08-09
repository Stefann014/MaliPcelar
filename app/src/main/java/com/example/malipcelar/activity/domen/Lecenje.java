package com.example.malipcelar.activity.domen;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "lecenje_table", foreignKeys = {
        @ForeignKey(entity = Kosnica.class, parentColumns = {"rb_kosnice", "pcelinjak"},
                childColumns = {"kosnica_id", "pcelinjak_id"}, onUpdate = CASCADE, onDelete = CASCADE)})
public class Lecenje implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "lecenje_id")
    int lecenjeID;
    @ColumnInfo(name = "kosnica_id")
    int kosnicaID;
    @ColumnInfo(name = "pcelinjak_id")
    int pcelinjakID;
    @ColumnInfo(name = "datum_lecenja")
    String datumLecenja;
    @ColumnInfo(name = "bolest")
    String bolest;

    public Lecenje() {
    }

    public Lecenje(int kosnicaID, int pcelinjakID, String datumLecenja, String bolest) {
        this.kosnicaID = kosnicaID;
        this.pcelinjakID = pcelinjakID;
        this.datumLecenja = datumLecenja;
        this.bolest = bolest;
    }

    public int getLecenjeID() {
        return lecenjeID;
    }

    public void setLecenjeID(int lecenjeID) {
        this.lecenjeID = lecenjeID;
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

    public String getDatumLecenja() {
        return datumLecenja;
    }

    public void setDatumLecenja(String datumLecenja) {
        this.datumLecenja = datumLecenja;
    }

    public String getBolest() {
        return bolest;
    }

    public void setBolest(String bolest) {
        this.bolest = bolest;
    }

    @NonNull
    public String toString() {
        return datumLecenja + ", kosnica RB: " + kosnicaID +
                ", lecenje od '" + bolest;
    }
}

package com.example.malipcelar.activity.domen;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "opsta_napomena_table")
public class OpstaNapomena implements Serializable {

    // autoincrement...
    @PrimaryKey(autoGenerate = true)
    private int opstaNapomenaID;


    private String datumNapomene;
    private String tipOpsteNapomene;
    private String opstaNapomena;

    public OpstaNapomena() {
    }

    public OpstaNapomena(String tipOpsteNapomene, String opstaNapomena, String datumNapomene) {
        this.tipOpsteNapomene = tipOpsteNapomene;
        this.opstaNapomena = opstaNapomena;
        this.datumNapomene = datumNapomene;
    }

    public int getOpstaNapomenaID() {
        return opstaNapomenaID;
    }

    public void setOpstaNapomenaID(int opstaNapomenaID) {
        this.opstaNapomenaID = opstaNapomenaID;
    }

    public String getDatumNapomene() {
        return datumNapomene;
    }

    public void setDatumNapomene(String datumNapomene) {
        this.datumNapomene = datumNapomene;
    }

    public String getTipOpsteNapomene() {
        return tipOpsteNapomene;
    }

    public void setTipOpsteNapomene(String tipOpsteNapomene) {
        this.tipOpsteNapomene = tipOpsteNapomene;
    }

    public String getOpstaNapomena() {
        return opstaNapomena;
    }

    public void setOpstaNapomena(String opstaNapomena) {
        this.opstaNapomena = opstaNapomena;
    }

    @NonNull
    @Override
    public String toString() {
        return "Tip:" + tipOpsteNapomene + "Napmena: " + opstaNapomena + "Datum: " + datumNapomene;
    }
}

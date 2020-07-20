package com.example.malipcelar.activity.domen;

import java.io.Serializable;
import java.util.Date;

public class Pasa implements Serializable {
    Date datumOd;
    Date datumDo;
    double ukupnoMeda;
    double ukupnoPolena;
    double ukupnoPropolisa;
    double ukupnoMaticnogMleca;
    double ukupnoPrikupljenePerge;
    String napomena;

    public Pasa() {
    }

    public Pasa(Date datumOd, Date datumDo, double ukupnoMeda, double ukupnoPolena, double ukupnoPropolisa, double ukupnoMaticnogMleca, double ukupnoPrikupljenePerge, String napomena) {
        this.datumOd = datumOd;
        this.datumDo = datumDo;
        this.ukupnoMeda = ukupnoMeda;
        this.ukupnoPolena = ukupnoPolena;
        this.ukupnoPropolisa = ukupnoPropolisa;
        this.ukupnoMaticnogMleca = ukupnoMaticnogMleca;
        this.ukupnoPrikupljenePerge = ukupnoPrikupljenePerge;
        this.napomena = napomena;
    }

    public Date getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(Date datumOd) {
        this.datumOd = datumOd;
    }

    public Date getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(Date datumDo) {
        this.datumDo = datumDo;
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

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    @Override
    public String toString() {
        return  datumOd+" "+datumDo;
    }
}

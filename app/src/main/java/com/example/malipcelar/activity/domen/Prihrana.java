package com.example.malipcelar.activity.domen;

import java.io.Serializable;
import java.util.Date;

public class Prihrana implements Serializable {
    Date datumPrihrane;
    Kosnica kosnica;
    String vrstaPrihrane;
    double kolicinaPrihrane;

    public Prihrana() {
    }

    public Prihrana(Date datum, Kosnica kosnica, String vrstaPrihrane, double kolicinaPrihrane) {
        this.datumPrihrane = datum;
        this.kosnica = kosnica;
        this.vrstaPrihrane = vrstaPrihrane;
        this.kolicinaPrihrane = kolicinaPrihrane;
    }

    public Date getDatumPrihrane() {
        return datumPrihrane;
    }

    public void setDatumPrihrane(Date datumPrihrane) {
        this.datumPrihrane = datumPrihrane;
    }

    public Kosnica getKosnica() {
        return kosnica;
    }

    public void setKosnica(Kosnica kosnica) {
        this.kosnica = kosnica;
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

    @Override
    public String toString() {
        return datumPrihrane +"";
    }
}

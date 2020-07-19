package com.example.malipcelar.activity.domen;

import java.util.Date;

public class Lecenje {
    Date datumLecenja;
    Kosnica kosnica;
    String lecenjeOd;

    public Lecenje() {
    }

    public Lecenje(Date datumLecenja, Kosnica kosnica, String lecenjeOd) {
        this.datumLecenja = datumLecenja;
        this.kosnica = kosnica;
        this.lecenjeOd = lecenjeOd;
    }

    public Date getDatumLecenja() {
        return datumLecenja;
    }

    public void setDatumLecenja(Date datumLecenja) {
        this.datumLecenja = datumLecenja;
    }

    public Kosnica getKosnica() {
        return kosnica;
    }

    public void setKosnica(Kosnica kosnica) {
        this.kosnica = kosnica;
    }

    public String getLecenjeOd() {
        return lecenjeOd;
    }

    public void setLecenjeOd(String lecenjeOd) {
        this.lecenjeOd = lecenjeOd;
    }

    @Override
    public String toString() {
        return datumLecenja +", kosnica RB: " + kosnica.getRedniBrojKosnice() +
                ", lecenje od '" + lecenjeOd;
    }
}

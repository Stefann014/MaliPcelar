package com.example.malipcelar.activity.domen;

import java.sql.Timestamp;

public class Kosnica {
    int redniBrojKosnice;
    String nazivKosnice;
    Timestamp godinaProizvodnjeMatice;
    Boolean selekciona;
    Boolean prirodna;
    String bolesti;
    String napomena;
    Pcelinjak pcelinjak;

    public Kosnica() {
    }

    public Kosnica(int redniBrojKosnice, String naziv, Timestamp godinaProizvodnjeMatice, Boolean selekciona, Boolean prirodna, String bolesti, String napomena, Pcelinjak pcelinjak) {
        this.redniBrojKosnice = redniBrojKosnice;
        this.nazivKosnice = naziv;
        this.godinaProizvodnjeMatice = godinaProizvodnjeMatice;
        this.selekciona = selekciona;
        this.prirodna = prirodna;
        this.bolesti = bolesti;
        this.napomena = napomena;
        this.pcelinjak = pcelinjak;
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

    public Timestamp getGodinaProizvodnjeMatice() {
        return godinaProizvodnjeMatice;
    }

    public void setGodinaProizvodnjeMatice(Timestamp godinaProizvodnjeMatice) {
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

    public Pcelinjak getPcelinjak() {
        return pcelinjak;
    }

    public void setPcelinjak(Pcelinjak pcelinjak) {
        this.pcelinjak = pcelinjak;
    }

    @Override
    public String toString() {
        if (nazivKosnice == null || nazivKosnice.equals("")) {
            return redniBrojKosnice + ". Kosnica";
        }
        return redniBrojKosnice + ". " + nazivKosnice;
    }
}

package com.example.malipcelar.activity.domen;

import androidx.annotation.NonNull;

import java.util.Date;

public class Pregled {
    Date datumPregleda;
    Kosnica kosnica;
    boolean matica;
    boolean mladoLeglo;
    boolean maticnjak;
    boolean konstantovanoRojenje;
    int brojUlicaPopunjenihPcelom;
    int brojRamovaSaLeglom;
    int brojRamovaSaVencomHraneUPlodistu;
    int brojRamovaSaPolenom;
    int brojRamovaSaLeglomPodignutihUMediste;
    int brojOduzetihRamovaSaLeglom;
    int brojRamovaSaMedomZaVadjenje;
    int brojIzvadjenihRamovaSaMedom;
    int brojUbacenihOsnova;
    int brojUbacenihPraznihRamova;
    String napomena;

    public Pregled() {
    }

    public Pregled(Date datumPregleda, Kosnica kosnica, boolean matica, boolean mladoLeglo, boolean maticnjak, boolean konstantovanoRojenje, int brojUlicaPopunjenihPcelom, int brojRamovaSaLeglom, int brojRamovaSaVencomHraneUPlodistu, int brojRamovaSaPolenom, int brojRamovaSaLeglomPodignutihUMediste, int brojOduzetihRamovaSaLeglom, int brojRamovaSaMedomZaVadjenje, int brojIzvadjenihRamovaSaMedom, int brojUbacenihOsnova, int brojUbacenihPraznihRamova, String napomena) {
        this.datumPregleda = datumPregleda;
        this.kosnica = kosnica;
        this.matica = matica;
        this.mladoLeglo = mladoLeglo;
        this.maticnjak = maticnjak;
        this.konstantovanoRojenje = konstantovanoRojenje;
        this.brojUlicaPopunjenihPcelom = brojUlicaPopunjenihPcelom;
        this.brojRamovaSaLeglom = brojRamovaSaLeglom;
        this.brojRamovaSaVencomHraneUPlodistu = brojRamovaSaVencomHraneUPlodistu;
        this.brojRamovaSaPolenom = brojRamovaSaPolenom;
        this.brojRamovaSaLeglomPodignutihUMediste = brojRamovaSaLeglomPodignutihUMediste;
        this.brojOduzetihRamovaSaLeglom = brojOduzetihRamovaSaLeglom;
        this.brojRamovaSaMedomZaVadjenje = brojRamovaSaMedomZaVadjenje;
        this.brojIzvadjenihRamovaSaMedom = brojIzvadjenihRamovaSaMedom;
        this.brojUbacenihOsnova = brojUbacenihOsnova;
        this.brojUbacenihPraznihRamova = brojUbacenihPraznihRamova;
        this.napomena = napomena;
    }

    public Date getDatumPregleda() {
        return datumPregleda;
    }

    public void setDatumPregleda(Date datumPregleda) {
        this.datumPregleda = datumPregleda;
    }

    public Kosnica getKosnica() {
        return kosnica;
    }

    public void setKosnica(Kosnica kosnica) {
        this.kosnica = kosnica;
    }

    public boolean isMatica() {
        return matica;
    }

    public void setMatica(boolean matica) {
        this.matica = matica;
    }

    public boolean isMladoLeglo() {
        return mladoLeglo;
    }

    public void setMladoLeglo(boolean mladoLeglo) {
        this.mladoLeglo = mladoLeglo;
    }

    public boolean isMaticnjak() {
        return maticnjak;
    }

    public void setMaticnjak(boolean maticnjak) {
        this.maticnjak = maticnjak;
    }

    public boolean isKonstantovanoRojenje() {
        return konstantovanoRojenje;
    }

    public void setKonstantovanoRojenje(boolean konstantovanoRojenje) {
        this.konstantovanoRojenje = konstantovanoRojenje;
    }

    public int getBrojUlicaPopunjenihPcelom() {
        return brojUlicaPopunjenihPcelom;
    }

    public void setBrojUlicaPopunjenihPcelom(int brojUlicaPopunjenihPcelom) {
        this.brojUlicaPopunjenihPcelom = brojUlicaPopunjenihPcelom;
    }

    public int getBrojRamovaSaLeglom() {
        return brojRamovaSaLeglom;
    }

    public void setBrojRamovaSaLeglom(int brojRamovaSaLeglom) {
        this.brojRamovaSaLeglom = brojRamovaSaLeglom;
    }

    public int getBrojRamovaSaVencomHraneUPlodistu() {
        return brojRamovaSaVencomHraneUPlodistu;
    }

    public void setBrojRamovaSaVencomHraneUPlodistu(int brojRamovaSaVencomHraneUPlodistu) {
        this.brojRamovaSaVencomHraneUPlodistu = brojRamovaSaVencomHraneUPlodistu;
    }

    public int getBrojRamovaSaPolenom() {
        return brojRamovaSaPolenom;
    }

    public void setBrojRamovaSaPolenom(int brojRamovaSaPolenom) {
        this.brojRamovaSaPolenom = brojRamovaSaPolenom;
    }

    public int getBrojRamovaSaLeglomPodignutihUMediste() {
        return brojRamovaSaLeglomPodignutihUMediste;
    }

    public void setBrojRamovaSaLeglomPodignutihUMediste(int brojRamovaSaLeglomPodignutihUMediste) {
        this.brojRamovaSaLeglomPodignutihUMediste = brojRamovaSaLeglomPodignutihUMediste;
    }

    public int getBrojOduzetihRamovaSaLeglom() {
        return brojOduzetihRamovaSaLeglom;
    }

    public void setBrojOduzetihRamovaSaLeglom(int brojOduzetihRamovaSaLeglom) {
        this.brojOduzetihRamovaSaLeglom = brojOduzetihRamovaSaLeglom;
    }

    public int getBrojRamovaSaMedomZaVadjenje() {
        return brojRamovaSaMedomZaVadjenje;
    }

    public void setBrojRamovaSaMedomZaVadjenje(int brojRamovaSaMedomZaVadjenje) {
        this.brojRamovaSaMedomZaVadjenje = brojRamovaSaMedomZaVadjenje;
    }

    public int getBrojIzvadjenihRamovaSaMedom() {
        return brojIzvadjenihRamovaSaMedom;
    }

    public void setBrojIzvadjenihRamovaSaMedom(int brojIzvadjenihRamovaSaMedom) {
        this.brojIzvadjenihRamovaSaMedom = brojIzvadjenihRamovaSaMedom;
    }

    public int getBrojUbacenihOsnova() {
        return brojUbacenihOsnova;
    }

    public void setBrojUbacenihOsnova(int brojUbacenihOsnova) {
        this.brojUbacenihOsnova = brojUbacenihOsnova;
    }

    public int getBrojUbacenihPraznihRamova() {
        return brojUbacenihPraznihRamova;
    }

    public void setBrojUbacenihPraznihRamova(int brojUbacenihPraznihRamova) {
        this.brojUbacenihPraznihRamova = brojUbacenihPraznihRamova;
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
        return datumPregleda+"";
    }
}


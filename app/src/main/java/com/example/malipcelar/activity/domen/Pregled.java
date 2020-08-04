package com.example.malipcelar.activity.domen;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "pregled_table", foreignKeys = {
        @ForeignKey(entity = Kosnica.class, parentColumns = {"rb_kosnice", "pcelinjak"},
                childColumns = {"kosnica_id", "pcelinjak_id"}, onUpdate = CASCADE, onDelete = CASCADE)})
public class Pregled implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int pregledID;

    @ColumnInfo(name = "datum_pregleda")
    String datumPregleda;

    @ColumnInfo(name = "kosnica_id")
    int kosnicaID;

    @ColumnInfo(name = "pcelinjak_id")
    int pcelinjakID;

    @ColumnInfo(name = "matica")
    boolean matica;
    @ColumnInfo(name = "mlado_leglo")
    boolean mladoLeglo;
    @ColumnInfo(name = "maticnjak")
    boolean maticnjak;
    @ColumnInfo(name = "konstantovano_rojenje")
    boolean konstantovanoRojenje;
    @ColumnInfo(name = "broj_ulica_popunjenih_pcelom")
    int brojUlicaPopunjenihPcelom;
    @ColumnInfo(name = "broj_ramova_sa_leglom")
    int brojRamovaSaLeglom;
    @ColumnInfo(name = "broj_ramova_sa_vencom_hrane_u_plodistu")
    int brojRamovaSaVencomHraneUPlodistu;
    @ColumnInfo(name = "broj_ramova_sa_polenom")
    int brojRamovaSaPolenom;
    @ColumnInfo(name = "broj_ramova_sa_leglom_podignutih_u_mediste")
    int brojRamovaSaLeglomPodignutihUMediste;
    @ColumnInfo(name = "broj_oduzetih_ramova_sa_leglom")
    int brojOduzetihRamovaSaLeglom;
    @ColumnInfo(name = "broj_ramova_sa_medom_za_vadjenje")
    int brojRamovaSaMedomZaVadjenje;
    @ColumnInfo(name = "broj_izvadjenih_ramova_sa_medom")
    int brojIzvadjenihRamovaSaMedom;
    @ColumnInfo(name = "broj_ubacenih_osnova")
    int brojUbacenihOsnova;
    @ColumnInfo(name = "broj_ubacenih_praznih_ramova")
    int brojUbacenihPraznihRamova;
    @ColumnInfo(name = "napomena")
    String napomena;


    public Pregled() {
    }

    public Pregled(String datumPregleda, int kosnicaID, int pcelinjakID, boolean matica, boolean mladoLeglo, boolean maticnjak, boolean konstantovanoRojenje, int brojUlicaPopunjenihPcelom, int brojRamovaSaLeglom, int brojRamovaSaVencomHraneUPlodistu, int brojRamovaSaPolenom, int brojRamovaSaLeglomPodignutihUMediste, int brojOduzetihRamovaSaLeglom, int brojRamovaSaMedomZaVadjenje, int brojIzvadjenihRamovaSaMedom, int brojUbacenihOsnova, int brojUbacenihPraznihRamova, String napomena) {
        this.datumPregleda = datumPregleda;
        this.kosnicaID = kosnicaID;
        this.pcelinjakID = pcelinjakID;
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

    public int getPregledID() {
        return pregledID;
    }

    public void setPregledID(int pregledID) {
        this.pregledID = pregledID;
    }

    public String getDatumPregleda() {
        return datumPregleda;
    }

    public void setDatumPregleda(String datumPregleda) {
        this.datumPregleda = datumPregleda;
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
        return datumPregleda + "";
    }
}


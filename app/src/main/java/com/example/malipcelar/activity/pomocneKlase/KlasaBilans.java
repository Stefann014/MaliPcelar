package com.example.malipcelar.activity.pomocneKlase;

public class KlasaBilans {

    private String rbINazivPcelinjaka;
    private String lokacija;
    private String slikaPcelinjaka;
    private double ukupnoMeda;
    private double ukupnoPolena;
    private double ukupnoPropolisa;
    private double ukupnoMaticnogMleca;
    private double ukupnoPrikupljenePerge;

    public String getRbINazivPcelinjaka() {
        return rbINazivPcelinjaka;
    }

    public void setRbINazivPcelinjaka(String rbINazivPcelinjaka) {
        this.rbINazivPcelinjaka = rbINazivPcelinjaka;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    public String getSlikaPcelinjaka() {
        return slikaPcelinjaka;
    }

    public void setSlikaPcelinjaka(String slikaPcelinjaka) {
        this.slikaPcelinjaka = slikaPcelinjaka;
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

    @Override
    public String toString() {
        return "KlasaBilans{" +
                "rbINazivPcelinjaka='" + rbINazivPcelinjaka + '\'' +
                ", lokacija='" + lokacija + '\'' +
                ", slikaPcelinjaka='" + slikaPcelinjaka + '\'' +
                ", ukupnoMeda=" + ukupnoMeda +
                ", ukupnoPolena=" + ukupnoPolena +
                ", ukupnoPropolisa=" + ukupnoPropolisa +
                ", ukupnoMaticnogMleca=" + ukupnoMaticnogMleca +
                ", ukupnoPrikupljenePerge=" + ukupnoPrikupljenePerge +
                '}';
    }
}

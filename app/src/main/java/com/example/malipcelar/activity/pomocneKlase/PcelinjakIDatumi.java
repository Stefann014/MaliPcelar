package com.example.malipcelar.activity.pomocneKlase;

import androidx.annotation.NonNull;

public class PcelinjakIDatumi {

    String pcelinjak;
    String maxDatumPregled;
    String maxDatumPrihrane;
    String maxDatumLecenja;

    public String getPcelinjak() {
        return pcelinjak;
    }

    public void setPcelinjak(String pcelinjak) {
        this.pcelinjak = pcelinjak;
    }

    public String getMaxDatumPregled() {
        return maxDatumPregled;
    }

    public void setMaxDatumPregled(String maxDatumPregled) {
        this.maxDatumPregled = maxDatumPregled;
    }

    public String getMaxDatumPrihrane() {
        return maxDatumPrihrane;
    }

    public void setMaxDatumPrihrane(String maxDatumPrihrane) {
        this.maxDatumPrihrane = maxDatumPrihrane;
    }

    public String getMaxDatumLecenja() {
        return maxDatumLecenja;
    }

    public void setMaxDatumLecenja(String maxDatumLecenja) {
        this.maxDatumLecenja = maxDatumLecenja;
    }

    @NonNull
    @Override
    public String toString() {
        return "PcelinjakIDatumi{" +
                "pcelinjak='" + pcelinjak + '\'' +
                ", maxDatumPregled='" + maxDatumPregled + '\'' +
                ", maxDatumPrihrane='" + maxDatumPrihrane + '\'' +
                ", maxDatumLecenja='" + maxDatumLecenja + '\'' +
                '}';
    }
}

package com.example.malipcelar.activity.pomocneKlase;

import androidx.annotation.NonNull;

public class TipNapomeneZaSpinner {
    private String vrstaTipaNapomene;
    private int slikaTipaNapomene;

    public TipNapomeneZaSpinner(String obavestenje, int slika) {
        vrstaTipaNapomene = obavestenje;
        slikaTipaNapomene = slika;
    }
    public String getVrstaTipaNapomene() {
        return vrstaTipaNapomene;
    }
    public int getSlikaTipaNapomene() {
        return slikaTipaNapomene;
    }

    @NonNull
    @Override
    public String toString() {
        return vrstaTipaNapomene;
    }
}

package com.example.malipcelar.activity.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.repozitorijum.PcelinjakRepository;

import java.util.ArrayList;
import java.util.List;

public class PcelinjakViewModel extends AndroidViewModel {

    private PcelinjakRepository pcelinjakRepository;
    private LiveData<List<Pcelinjak>> allPcelinjaci;
    private LiveData<List<Integer>> zauzetiRBovi;

    public PcelinjakViewModel(@NonNull Application application) {
        super(application);
        pcelinjakRepository = new PcelinjakRepository(application);
        zauzetiRBovi = pcelinjakRepository.getAllPcelinjakRB();
        allPcelinjaci = pcelinjakRepository.getAllPcelinjaci();
    }

    public void insert(Pcelinjak pcelinjak) {
        pcelinjakRepository.insert(pcelinjak);
    }

    public void update(Pcelinjak pcelinjak) {
        pcelinjakRepository.update(pcelinjak);
    }

    public void delete(Pcelinjak pcelinjak) {
        pcelinjakRepository.delete(pcelinjak);
    }

    public void deleteAllPcelinjaci() {
        pcelinjakRepository.deleteAllPcelinjaci();
    }

    public LiveData<List<Pcelinjak >> getAllPcelinjaci() {
        return allPcelinjaci;
    }

    public LiveData<Pcelinjak> getPcelinjakByID(int id) {
        return pcelinjakRepository.getPcelinjakByID(id);
    }

    public LiveData<List<Integer>> getAllPcelinjakRB() {
        return zauzetiRBovi;
    }

    public void updateRb(int stariRb, int noviRb) {
        pcelinjakRepository.updateRb(stariRb,noviRb);
    }
}

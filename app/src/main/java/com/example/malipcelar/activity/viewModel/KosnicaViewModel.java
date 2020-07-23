package com.example.malipcelar.activity.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.malipcelar.activity.domen.Kosnica;
import com.example.malipcelar.activity.repozitorijum.KosnicaRepository;

import java.util.List;


public class KosnicaViewModel extends AndroidViewModel {

    private KosnicaRepository kosnicaRepository;


    public KosnicaViewModel(@NonNull Application application) {
        super(application);
        kosnicaRepository = new KosnicaRepository(application);

    }

    public void insert(Kosnica kosnica) {
        kosnicaRepository.insert(kosnica);
    }

    public void update(Kosnica kosnica) {
        kosnicaRepository.update(kosnica);
    }

    public void delete(Kosnica kosnica) {
        kosnicaRepository.delete(kosnica);
    }

    public void deleteAllKosnice() {
        kosnicaRepository.deleteAllKosnice();
    }

    public LiveData<List<Kosnica>> getAllKosniceByRbPcelinjaka(int pcelinjak) {
        return kosnicaRepository.getKosniceByRbPcelinjaka(pcelinjak);
    }

    public LiveData<Kosnica> getKosnicaByRBKosnice(int rb_kosnice, int pcelinjak) {
        return kosnicaRepository.getKosnicaByRBKosnice(rb_kosnice,pcelinjak);
    }

    public LiveData<List<Integer>> getAllRBKosniceZaPcelinjak(int pcelinjak){
        return  kosnicaRepository.getAllRBKosniceZaPcelinjak(pcelinjak);
    }

    public void updateRb(int stariRb, int noviRb) {
        kosnicaRepository.updateRb(stariRb,noviRb);
    }

}

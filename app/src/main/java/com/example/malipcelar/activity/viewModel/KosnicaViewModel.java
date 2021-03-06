package com.example.malipcelar.activity.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.malipcelar.activity.domen.Kosnica;
import com.example.malipcelar.activity.pomocneKlase.KosnicaIDatumi;
import com.example.malipcelar.activity.repository.KosnicaRepository;

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

    public LiveData<List<Kosnica>> getAllKosniceByRbPcelinjaka(int pcelinjak) {
        return kosnicaRepository.getAllKosniceByRbPcelinjaka(pcelinjak);
    }

    public LiveData<List<KosnicaIDatumi>> getAllKosniceIDatumeZaPcelinjak(int pcelinjak) {
        return kosnicaRepository.getAllKosniceIDatumeZaPcelinjak(pcelinjak);
    }

    public LiveData<List<Integer>> getAllRBKosniceZaPcelinjak(int pcelinjak) {
        return kosnicaRepository.getAllRBKosniceZaPcelinjak(pcelinjak);
    }

    public void updateRb(int stariRb, int noviRb) {
        kosnicaRepository.updateRb(stariRb, noviRb);
    }
}

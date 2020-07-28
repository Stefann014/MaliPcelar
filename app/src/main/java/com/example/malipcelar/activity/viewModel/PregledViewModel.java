package com.example.malipcelar.activity.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.malipcelar.activity.domen.Pregled;
import com.example.malipcelar.activity.repository.PregledRepository;

import java.util.List;

public class PregledViewModel extends AndroidViewModel {

    private PregledRepository pregledRepository;

    public PregledViewModel(@NonNull Application application) {
        super(application);
        pregledRepository = new PregledRepository(application);
    }

    public void insert(Pregled pregled) {
        pregledRepository.insert(pregled);
    }

    public void update(Pregled pregled) {
        pregledRepository.update(pregled);
    }

    public void delete(Pregled pregled) {
        pregledRepository.delete(pregled);
    }

    public LiveData<List<Pregled>> getAllPreglediZaKosnicu(int kosnicaID, int pcelinjakID) {
        return pregledRepository.getAllPreglediZaKosnicu(kosnicaID, pcelinjakID);
    }

}

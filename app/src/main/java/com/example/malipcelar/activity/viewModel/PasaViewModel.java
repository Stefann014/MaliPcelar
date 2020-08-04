package com.example.malipcelar.activity.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.malipcelar.activity.domen.Pasa;
import com.example.malipcelar.activity.repository.PasaRepository;

import java.util.List;

public class PasaViewModel extends AndroidViewModel {

    private PasaRepository pasaRepository;

    public PasaViewModel(@NonNull Application application) {
        super(application);
        pasaRepository = new PasaRepository(application);
    }

    public void insert(Pasa pasa) {
        pasaRepository.insert(pasa);
    }

    public void update(Pasa pasa) {
        pasaRepository.update(pasa);
    }

    public void delete(Pasa pasa) {
        pasaRepository.delete(pasa);
    }

    public LiveData<List<Pasa>> getAllPase() {
        return pasaRepository.getAllPase();
    }

}

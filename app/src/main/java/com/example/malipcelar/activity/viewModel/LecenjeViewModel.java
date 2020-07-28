package com.example.malipcelar.activity.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.malipcelar.activity.domen.Lecenje;
import com.example.malipcelar.activity.repository.LecenjeRepository;

import java.util.List;

public class LecenjeViewModel extends AndroidViewModel {

    private LecenjeRepository lecenjeRepository;

    public LecenjeViewModel(@NonNull Application application) {
        super(application);
        lecenjeRepository = new LecenjeRepository(application);
    }

    public void insert(Lecenje lecenje) {
        lecenjeRepository.insert(lecenje);
    }

    public void update(Lecenje lecenje) {
        lecenjeRepository.update(lecenje);
    }

    public void delete(Lecenje lecenje) {
        lecenjeRepository.delete(lecenje);
    }

    public LiveData<List<Lecenje>> getAllLecenjaZaKosnicu(int kosnicaID, int pcelinjakID) {
        return lecenjeRepository.getAllLecenjaZaKosnicu(kosnicaID, pcelinjakID);
    }

}

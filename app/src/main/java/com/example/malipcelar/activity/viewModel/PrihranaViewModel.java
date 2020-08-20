package com.example.malipcelar.activity.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.malipcelar.activity.domen.Prihrana;
import com.example.malipcelar.activity.repository.PrihranaRepository;

import java.util.List;

public class PrihranaViewModel extends AndroidViewModel {

    private PrihranaRepository prihranaRepository;

    public PrihranaViewModel(@NonNull Application application) {
        super(application);
        prihranaRepository = new PrihranaRepository(application);
    }

    public void insert(Prihrana prihrana) {
        prihranaRepository.insert(prihrana);
    }

    public void update(Prihrana prihrana) {
        prihranaRepository.update(prihrana);
    }

    public void delete(Prihrana prihrana) {
        prihranaRepository.delete(prihrana);
    }

    public LiveData<List<Prihrana>> getAllPrihranaZaKosnicu(int kosnicaID, int pcelinjakID) {
        return prihranaRepository.getAllPrihranaZaKosnicu(kosnicaID, pcelinjakID);
    }
}

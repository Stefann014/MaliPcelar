package com.example.malipcelar.activity.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.malipcelar.activity.domen.OpstaNapomena;
import com.example.malipcelar.activity.repozitorijum.OpstaNapomenaRepository;

import java.util.List;

public class OpstaNapomenaViewModel extends AndroidViewModel {

    private OpstaNapomenaRepository opstaNapomenaRepository;
    private LiveData<List<OpstaNapomena>> allOpsteNapomene;

    public OpstaNapomenaViewModel(@NonNull Application application) {
        super(application);
        opstaNapomenaRepository = new OpstaNapomenaRepository(application);
        allOpsteNapomene = opstaNapomenaRepository.getAllOpsteNapomene();
    }

    public void insert(OpstaNapomena opstaNapomena) {
        opstaNapomenaRepository.insert(opstaNapomena);
    }

    public void update(OpstaNapomena opstaNapomena) {
        opstaNapomenaRepository.update(opstaNapomena);
    }

    public void delete(OpstaNapomena opstaNapomena) {
        opstaNapomenaRepository.delete(opstaNapomena);
    }

    public void deleteAllOpsteNapomene() {
        opstaNapomenaRepository.deleteAllOpsteNapomene();
    }

    public LiveData<List<OpstaNapomena >> getAllOpsteNapomene() {
        return allOpsteNapomene;
    }
}


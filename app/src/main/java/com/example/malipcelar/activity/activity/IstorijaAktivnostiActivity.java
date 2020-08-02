package com.example.malipcelar.activity.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.adapteri.IstorijaAktivnostiAdapter;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.pomocneKlase.PcelinjakIDatumi;
import com.example.malipcelar.activity.viewModel.KosnicaViewModel;
import com.example.malipcelar.activity.viewModel.PcelinjakViewModel;

import java.util.List;

public class IstorijaAktivnostiActivity extends AppCompatActivity {

    RecyclerView rvIstorijaAktivnosti;
    List<Pcelinjak> sviPcelinjaci;
    List<PcelinjakIDatumi> pcelinjaciIDatumi;
    PcelinjakViewModel pcelinjakViewModel;
    KosnicaViewModel kosnicaViewModel;
    final IstorijaAktivnostiAdapter adapter = new IstorijaAktivnostiAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.istorija_aktivnosti_activity);

        srediAtribute();
        srediKomunikacijuSaViewModel();

    }

    private void srediRecycleView() {
        rvIstorijaAktivnosti.setLayoutManager(new LinearLayoutManager(this));
        rvIstorijaAktivnosti.setHasFixedSize(true);
        rvIstorijaAktivnosti.setAdapter(adapter);
    }

    private void srediAtribute() {
        rvIstorijaAktivnosti = findViewById(R.id.rvIstorijaAktivnosti);
        sviPcelinjaci = null;
        pcelinjaciIDatumi = null;
        srediRecycleView();
    }

    private void srediKomunikacijuSaViewModel() {
        pcelinjakViewModel = ViewModelProviders.of(this).get(PcelinjakViewModel.class);
        kosnicaViewModel = ViewModelProviders.of(this).get(KosnicaViewModel.class);
        srediObservere();
    }

    private void srediObservere() {

        pcelinjakViewModel.getPcelinjakIDatumi().observe(this, new Observer<List<PcelinjakIDatumi>>() {
            @Override
            public void onChanged(List<PcelinjakIDatumi> pcelIDat) {
                pcelinjaciIDatumi = pcelIDat;
                adapter.submitList(pcelinjaciIDatumi);
            }
        });
    }
}
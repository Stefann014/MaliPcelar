package com.example.malipcelar.activity.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.adapteri.IstorijaAktivnostiAdapter;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.pomocneKlase.PcelinjakIDatumi;
import com.example.malipcelar.activity.viewModel.KosnicaViewModel;
import com.example.malipcelar.activity.viewModel.PcelinjakViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class IstorijaAktivnostiActivity extends AppCompatActivity {

    RecyclerView rvIstorijaAktivnosti;
    List<Pcelinjak> sviPcelinjaci;
    List<PcelinjakIDatumi> pcelinjaciIDatumi;
    PcelinjakViewModel pcelinjakViewModel;
    KosnicaViewModel kosnicaViewModel;
    final IstorijaAktivnostiAdapter adapter = new IstorijaAktivnostiAdapter();
    FloatingActionButton btnPomoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.istorija_aktivnosti_activity);

        srediAtribute();
        srediListenere();
        srediKomunikacijuSaViewModel();

    }

    private void srediListenere() {
        btnPomoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poruka();
            }
        });
    }

    private void poruka() {
        String buffer = "\tIstorija aktivnosti služi za prikazivanje poslednjih pregleda, prihrana i lečenja.\n\n\tU slučaju da Vam se ništa ne prikazuje,\nto znači da nema unesenih stavki.";
        prikaziPoruku("Istorija aktivnosti ", buffer);

    }


    public void prikaziPoruku(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    private void srediRecycleView() {
        rvIstorijaAktivnosti.setLayoutManager(new LinearLayoutManager(this));
        rvIstorijaAktivnosti.setHasFixedSize(true);
        rvIstorijaAktivnosti.setAdapter(adapter);
    }

    private void srediAtribute() {
        btnPomoc = findViewById(R.id.btnPomoc);
        rvIstorijaAktivnosti = findViewById(R.id.rvIstorijaAktivnosti);
        sviPcelinjaci = null;
        pcelinjaciIDatumi = null;
        srediRecycleView();
    }

    private void srediKomunikacijuSaViewModel() {
        pcelinjakViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(PcelinjakViewModel.class);
        kosnicaViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(KosnicaViewModel.class);
        srediObservere();
    }

    private void srediObservere() {

        pcelinjakViewModel.getPcelinjakIDatumi().observe(this, new Observer<List<PcelinjakIDatumi>>() {
            @Override
            public void onChanged(List<PcelinjakIDatumi> pcelIDat) {
                pcelinjaciIDatumi = pcelIDat;
                if (pcelinjaciIDatumi.size() == 0) {
                    poruka();

                }
                adapter.submitList(pcelinjaciIDatumi);
            }
        });
    }
}
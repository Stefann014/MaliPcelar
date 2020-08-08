package com.example.malipcelar.activity.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.adapteri.BilansProizvodaAdapter;
import com.example.malipcelar.activity.domen.Pasa;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.pomocneKlase.KlasaBilans;
import com.example.malipcelar.activity.viewModel.PasaViewModel;
import com.example.malipcelar.activity.viewModel.PcelinjakViewModel;

import java.util.List;

public class BilansProizvodaActivity extends AppCompatActivity {

    public static final int DODAJ_NOVU_PASU = 1;

    Button btnPrikaziIstorijuPasa;
    Button btnDodajNovuPasu;

    PasaViewModel pasaViewModel;
    PcelinjakViewModel pcelinjakViewModel;
    RecyclerView recyclerView;

    List<KlasaBilans> bilansi;
    List<Pcelinjak> pcelinjaci;

    int pauza = 0;

    final BilansProizvodaAdapter adapter = new BilansProizvodaAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bilans_proizvoda_activity);
        setTitle("Bilans proizvoda");
        srediAtribute();
        srediRecycleView();
        srediListenere();
        srediViewModel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauza = 1;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pauza == 1) {
            recreate();
        }
        pauza = 0;
    }

    private void srediRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    private void srediViewModel() {
        pasaViewModel = ViewModelProviders.of(this).get(PasaViewModel.class);
        pcelinjakViewModel = ViewModelProviders.of(this).get(PcelinjakViewModel.class);


        srediObservere();
    }

    private void srediObservere() {

        pcelinjakViewModel.getAllPcelinjaci().observe(this, new Observer<List<Pcelinjak>>() {
            @Override
            public void onChanged(@Nullable List<Pcelinjak> pcelinjaks) {
                pcelinjaci = pcelinjaks;
            }
        });
        pcelinjakViewModel.getAllBilans().observe(this, new Observer<List<KlasaBilans>>() {
            @Override
            public void onChanged(List<KlasaBilans> klasaBilans) {
                bilansi = klasaBilans;
                if (bilansi.size() == 0) {
                    poruka();
                }
                adapter.submitList(bilansi);
            }
        });
    }

    private void srediListenere() {
        btnDodajNovuPasu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BilansProizvodaActivity.this, Dodaj_IzmeniPasuActivity.class);
                startActivityForResult(intent, DODAJ_NOVU_PASU);
            }
        });
        btnPrikaziIstorijuPasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BilansProizvodaActivity.this, IstorijaPasaActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DODAJ_NOVU_PASU && resultCode == RESULT_OK) {

            adapter.notifyDataSetChanged();
            String datumOd = data.getStringExtra(Dodaj_IzmeniPasuActivity.EXTRA_DATUM_OD_PASE);
            String datumDo = data.getStringExtra(Dodaj_IzmeniPasuActivity.EXTRA_DATUM_DO_PASE);

            double prikupljenoMeda = data.getDoubleExtra(Dodaj_IzmeniPasuActivity.EXTRA_PRIKUPLJENO_MEDA, 0);
            double prikupljenoPolena = data.getDoubleExtra(Dodaj_IzmeniPasuActivity.EXTRA_PRIKUPLJENO_POLENA, 0);
            double prikupljenoPropolisa = data.getDoubleExtra(Dodaj_IzmeniPasuActivity.EXTRA_PRIKUPLJENO_PROPOLISA, 0);
            double prikupljenoMaticnogMleca = data.getDoubleExtra(Dodaj_IzmeniPasuActivity.EXTRA_PRIKUPLJENO_MATICNOG_MLECA, 0);
            double prikupljenoPerge = data.getDoubleExtra(Dodaj_IzmeniPasuActivity.EXTRA_PRIKUPLJENO_PERGE, 0);

            String napomena = data.getStringExtra(Dodaj_IzmeniPasuActivity.EXTRA_NAPOMENA_PASA);

            Pcelinjak pcelinjak = (Pcelinjak) data.getSerializableExtra(Dodaj_IzmeniPasuActivity.EXTRA_PCELINJAK_ID);
            assert pcelinjak != null;
            Pasa pasa = new Pasa(pcelinjak.getRedniBrojPcelinjaka(), datumOd, datumDo, prikupljenoMeda, prikupljenoPolena, prikupljenoPropolisa, prikupljenoMaticnogMleca, prikupljenoPerge, napomena);

            pasaViewModel.insert(pasa);

            Toast.makeText(this, "Pasa je sacuvana", Toast.LENGTH_SHORT).show();
        }
    }

    private void srediAtribute() {
        btnDodajNovuPasu = findViewById(R.id.btnDodajNovuPasu);
        btnPrikaziIstorijuPasa = findViewById(R.id.btnIstorijaPasa);
        recyclerView = findViewById(R.id.rvBilansProizvoda);
        pcelinjaci = null;
        bilansi = null;
    }

    private void poruka() {
        String buffer = "\tBilans proizvoda služi za prikazivanje koliko je do sada prikupljeno meda, polena, propolisa, matičnog mleča i perge u svakom pčelinjaku.\n\n\tU slučaju da Vam se ništa ne prikazuje,\nto znači da nema unesenih stavki.";
        prikaziPoruku("Bilans proizvoda ", buffer);

    }

    public void prikaziPoruku(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
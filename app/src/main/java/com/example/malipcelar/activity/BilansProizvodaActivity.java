package com.example.malipcelar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.domen.Pasa;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.viewModel.PasaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BilansProizvodaActivity extends AppCompatActivity {

    public static final int DODAJ_NOVU_PASU = 1;

    FloatingActionButton btnPrikaziIstorijuPasa;
    FloatingActionButton btnDodajNovuPasu;

    PasaViewModel pasaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bilans_proizvoda_activity);

        srediAtribute();
        srediListenere();
        srediViewModel();
    }

    private void srediViewModel() {
        pasaViewModel = ViewModelProviders.of(this).get(PasaViewModel.class);
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
            String datumOd = data.getStringExtra(Dodaj_IzmeniPasuActivity.EXTRA_DATUM_OD_PASE);
            String datumDo = data.getStringExtra(Dodaj_IzmeniPasuActivity.EXTRA_DATUM_DO_PASE);

            Double prikupljenoMeda = data.getDoubleExtra(Dodaj_IzmeniPasuActivity.EXTRA_PRIKUPLJENO_MEDA, 0);
            Double prikupljenoPolena = data.getDoubleExtra(Dodaj_IzmeniPasuActivity.EXTRA_PRIKUPLJENO_POLENA, 0);
            Double prikupljenoPropolisa = data.getDoubleExtra(Dodaj_IzmeniPasuActivity.EXTRA_PRIKUPLJENO_PROPOLISA, 0);
            Double prikupljenoMaticnogMleca = data.getDoubleExtra(Dodaj_IzmeniPasuActivity.EXTRA_PRIKUPLJENO_MATICNOG_MLECA, 0);
            Double prikupljenoPerge = data.getDoubleExtra(Dodaj_IzmeniPasuActivity.EXTRA_PRIKUPLJENO_PERGE, 0);

            String napomena = data.getStringExtra(Dodaj_IzmeniPasuActivity.EXTRA_NAPOMENA_PASA);

            Pcelinjak pcelinjak = (Pcelinjak) data.getSerializableExtra(Dodaj_IzmeniPasuActivity.EXTRA_PCELINJAK_ID);

            Pasa pasa = new Pasa(pcelinjak.getRedniBrojPcelinjaka(), datumOd, datumDo, prikupljenoMeda, prikupljenoPolena, prikupljenoPropolisa, prikupljenoMaticnogMleca, prikupljenoPerge, napomena);

            pasaViewModel.insert(pasa);

            Toast.makeText(this, "Lecenje je sacuvano", Toast.LENGTH_SHORT).show();
        }

        /*else if (requestCode == IZMENI_LECENJE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(Dodaj_IzmeniLecenjeActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Lecenje ne moze biti izmenjeno", Toast.LENGTH_SHORT).show();
                return;
            }

            String datumLecenja = data.getStringExtra(Dodaj_IzmeniLecenjeActivity.EXTRA_DATUM_LECENJA);
            String bolest = data.getStringExtra(Dodaj_IzmeniLecenjeActivity.EXTRA_BOLEST);


            Lecenje lecenje = new Lecenje(kosnica.getRedniBrojKosnice(), pcelinjak.getRedniBrojPcelinjaka(), datumLecenja, bolest);
            lecenje.setLecenjeID(id);
            lecenjeViewModel.update(lecenje);

            Toast.makeText(this, "Lecenje je izmenjeno", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Lecenje nije izmenjeno", Toast.LENGTH_SHORT).show();
        }*/
    }

    private void srediAtribute() {
        btnDodajNovuPasu = findViewById(R.id.btnDodajNovuPasu);
        btnPrikaziIstorijuPasa = findViewById(R.id.btnIstorijaPasa);
    }
}
package com.example.malipcelar.activity.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.adapteri.IstorijaPasaAdapter;
import com.example.malipcelar.activity.domen.Pasa;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.fragmenti.DaLiZelisDaIzbrisesDialog;
import com.example.malipcelar.activity.viewModel.PasaViewModel;

import java.util.List;

public class IstorijaPasaActivity extends AppCompatActivity implements DaLiZelisDaIzbrisesDialog.ExampleDialogListener {

    public static final int IZMENI_PASU = 2;

    RecyclerView recyclerView;
    PasaViewModel pasaViewModel;

    final IstorijaPasaAdapter adapter = new IstorijaPasaAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.istorija_pasa_activity);
        setTitle("Istorija paša");
        srediAtribute();
        srediViewModel();
        srediIzmeniPasuNaKlik();
    }

    private void srediAtribute() {
        recyclerView = findViewById(R.id.rvPase);
        srediRecycleView();
    }

    private void srediViewModel() {
        pasaViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(PasaViewModel.class);
        srediObservere();
    }

    private void srediObservere() {
        pasaViewModel.getAllPase().observe(this, new Observer<List<Pasa>>() {
            @Override
            public void onChanged(@Nullable List<Pasa> pase) {
                adapter.submitList(pase);
            }
        });
    }

    private void srediRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void srediIzmeniPasuNaKlik() {
        adapter.setOnItemClickListener(new IstorijaPasaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Pasa pasa) {

                String poruka = datumZaPrikaz(pasa.getDatumOd()) + " - " + datumZaPrikaz(pasa.getDatumDo()) +
                        "\n\n" + "Prikupljeno meda: " + pasa.getPrikupljenoMeda() + " kg \n" + "Prikupljeno polena: " + pasa.getPrikupljenoPolena() + " kg \n"
                        + "Prikupljeno propolisa: " + pasa.getPrikupljenoPropolisa() + " kg \n" + "Prikupljeno matičnog mleča: " + pasa.getPrikupljenoMaticnogMleca() + " kg \n"
                        + "Prikupljeno perge: " + pasa.getPrikupljenaPerga() + " kg \n\n" + "Napomena: " + pasa.getNapomena() + "\n";
                prikaziPoruku("Paša", poruka);
            }

            @Override
            public void onLongItemClick(Pasa pasa) {

                Intent intent = new Intent(IstorijaPasaActivity.this, Dodaj_IzmeniPasuActivity.class);

                intent.putExtra(Dodaj_IzmeniPasuActivity.EXTRA_ID, pasa.getPasaID());
                intent.putExtra(Dodaj_IzmeniPasuActivity.EXTRA_DATUM_OD_PASE, pasa.getDatumOd());
                intent.putExtra(Dodaj_IzmeniPasuActivity.EXTRA_DATUM_DO_PASE, pasa.getDatumDo());
                Pcelinjak pcelinjak = new Pcelinjak();
                pcelinjak.setRedniBrojPcelinjaka(pasa.getPcelinjakID());
                intent.putExtra(Dodaj_IzmeniPasuActivity.EXTRA_PCELINJAK_ID, pcelinjak);
                intent.putExtra(Dodaj_IzmeniPasuActivity.EXTRA_PRIKUPLJENO_MEDA, pasa.getPrikupljenoMeda());
                intent.putExtra(Dodaj_IzmeniPasuActivity.EXTRA_PRIKUPLJENO_POLENA, pasa.getPrikupljenoPolena());
                intent.putExtra(Dodaj_IzmeniPasuActivity.EXTRA_PRIKUPLJENO_PROPOLISA, pasa.getPrikupljenoPropolisa());
                intent.putExtra(Dodaj_IzmeniPasuActivity.EXTRA_PRIKUPLJENO_MATICNOG_MLECA, pasa.getPrikupljenoMaticnogMleca());
                intent.putExtra(Dodaj_IzmeniPasuActivity.EXTRA_PRIKUPLJENO_PERGE, pasa.getPrikupljenaPerga());
                intent.putExtra(Dodaj_IzmeniPasuActivity.EXTRA_NAPOMENA_PASA, pasa.getNapomena());
                startActivityForResult(intent, IZMENI_PASU);
            }

            @Override
            public void izbrisiPasu(Pasa pasa) {
                daLiZelisDaIzbrises(pasa);
            }
        });
    }

    private void daLiZelisDaIzbrises(Pasa pasa) {
        DaLiZelisDaIzbrisesDialog dialog = new DaLiZelisDaIzbrisesDialog(pasa);
        dialog.show(getSupportFragmentManager(), "Dialog");
    }

    @Override
    public void kliknutoDa(Pasa pasa) {
        pasaViewModel.delete(pasa);
        Toast.makeText(this, "Paša je izbrisana", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void kliknutoDa(Pcelinjak pcelinjak) {

    }

    @NonNull
    private String datumZaPrikaz(@NonNull String datum) {
        String[] datumi = datum.split("-");
        return datumi[2] + "." + datumi[1] + "." + datumi[0];
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IZMENI_PASU && resultCode == RESULT_OK) {
            int id = data.getIntExtra(Dodaj_IzmeniPasuActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Paša ne može biti izmenjena", Toast.LENGTH_SHORT).show();
                return;
            }

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

            pasa.setPasaID(id);
            pasaViewModel.update(pasa);

            Toast.makeText(this, "Paša je izmenjena", Toast.LENGTH_SHORT).show();
        }
    }

    public void prikaziPoruku(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


}
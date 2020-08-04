package com.example.malipcelar.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.adapteri.PasaAdapter;
import com.example.malipcelar.activity.domen.Pasa;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.viewModel.PasaViewModel;

import java.util.List;

public class IstorijaPasaActivity extends AppCompatActivity {

    public static final int IZMENI_PASU = 2;

    RecyclerView recyclerView;
    PasaViewModel pasaViewModel;

    List<Pasa> pase;

    final PasaAdapter adapter = new PasaAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.istorija_pasa_activity);

        srediAtribute();
        srediViewModel();
        srediBrisanje();
        srediIzmeniPasuNaKlik();
    }

    private void srediAtribute() {
        recyclerView = findViewById(R.id.rvPase);
        srediRecycleView();
    }

    private void srediViewModel() {
        pasaViewModel = ViewModelProviders.of(this).get(PasaViewModel.class);
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

    private void srediBrisanje() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                pasaViewModel.delete(adapter.getPasaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(IstorijaPasaActivity.this, "Lecenje je izbrisano", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }

    private void srediIzmeniPasuNaKlik() {
        adapter.setOnItemClickListener(new PasaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Pasa pasa) {
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
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IZMENI_PASU && resultCode == RESULT_OK) {
            int id = data.getIntExtra(Dodaj_IzmeniPasuActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Pasa ne moze biti izmenjena", Toast.LENGTH_SHORT).show();
                return;
            }

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

            pasa.setPasaID(id);
            pasaViewModel.update(pasa);

            Toast.makeText(this, "Lecenje je izmenjeno", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Lecenje nije izmenjeno", Toast.LENGTH_SHORT).show();
        }
    }

}
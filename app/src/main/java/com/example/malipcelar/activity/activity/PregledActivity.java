package com.example.malipcelar.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.adapteri.PregledAdapter;
import com.example.malipcelar.activity.domen.Kosnica;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.domen.Pregled;
import com.example.malipcelar.activity.viewModel.KosnicaViewModel;
import com.example.malipcelar.activity.viewModel.PregledViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PregledActivity extends AppCompatActivity {

    public static final String EXTRA_KOSNICA =
            "com.example.malipcelar.activity.activity.KOSNICA";
    public static final String EXTRA_PCELINJAK =
            "com.example.malipcelar.activity.activity.PCELINJAK";

    public static final int DODAJ_NOVI_PREGLED = 1;
    public static final int IZMENI_PREGLED = 2;

    FloatingActionButton btnDodajPregled;
    RecyclerView recyclerView;
    PregledViewModel pregledViewModel;
    KosnicaViewModel kosnicaViewModel;

    Kosnica kosnica;
    Pcelinjak pcelinjak;
    List<Pregled> pregledi;

    String maxDatum;

    final PregledAdapter adapter = new PregledAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pregled_activity);

        srediAtribute();
        srediListenere();
        srediViewModel();
        srediBrisanje();
        srediIzmeniPregledNaKlik();
    }

    private void srediAtribute() {
        Intent intent = getIntent();
        kosnica = (Kosnica) intent.getSerializableExtra(EXTRA_KOSNICA);
        pcelinjak = (Pcelinjak) intent.getSerializableExtra(EXTRA_PCELINJAK);
        maxDatum = null;

        recyclerView = findViewById(R.id.rvPregledi);
        btnDodajPregled = findViewById(R.id.btnDodajNoviPregled);
        pregledi = null;
        srediRecycleView();
    }

    private void srediListenere() {
        btnDodajPregled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PregledActivity.this, Dodaj_IzmeniPregledActivity.class);
                startActivityForResult(intent, DODAJ_NOVI_PREGLED);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DODAJ_NOVI_PREGLED && resultCode == RESULT_OK) {
            String datumPregleda = data.getStringExtra(Dodaj_IzmeniPregledActivity.EXTRA_DATUM_PREGLEDA);
            boolean matica = data.getBooleanExtra(Dodaj_IzmeniPregledActivity.EXTRA_MATICA, false);
            boolean mladoLeglo = data.getBooleanExtra(Dodaj_IzmeniPregledActivity.EXTRA_MLADO_LEGLO, false);
            boolean maticnjak = data.getBooleanExtra(Dodaj_IzmeniPregledActivity.EXTRA_MATICNJAK, false);
            boolean konstantovanoRojenje = data.getBooleanExtra(Dodaj_IzmeniPregledActivity.EXTRA_KONSTANTOVANO_ROJENJE, false);
            int brojUlicaPopunjenihPcelom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_ULICA_POPUNJENIH_PCELOM, -1);
            int brojRamovaSaLeglom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_LEGLOM, -1);
            int brojRamovaSaVencomHraneUPlodistu = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_VENCOM_HRANE_U_PLODISTU, -1);
            int brojRamovaSaPolenom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_POLENOM, -1);
            int brojRamovaSaLeglomPodignutihUMediste = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_LEGLOM_PODIGNUTIH_U_MEDISTE, -1);
            int brojOduzetihRamovaSaLeglom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_ODUZETIH_RAMOVA_SA_LEGLOM, -1);
            int brojRamovaSaMedomZaVadjenje = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_MEDOM_ZA_VADJENJE, -1);
            int brojIzvadjenihRamovaSaMedom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_IZVADJENIH_RAMOVA_SA_MEDOM, -1);
            int brojUbacenihOsnova = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_UBACENIH_OSNOVA, -1);
            int brojUbacenihPraznihRamova = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_UBACENIH_PRAZNIH_RAMOVA, -1);
            String napomena = data.getStringExtra(Dodaj_IzmeniPregledActivity.EXTRA_NAPOMENA);


            Pregled pregled = new Pregled(datumPregleda, kosnica.getRedniBrojKosnice(), pcelinjak.getRedniBrojPcelinjaka(), matica, mladoLeglo, maticnjak, konstantovanoRojenje,
                    brojUlicaPopunjenihPcelom, brojRamovaSaLeglom, brojRamovaSaVencomHraneUPlodistu, brojRamovaSaPolenom,
                    brojRamovaSaLeglomPodignutihUMediste, brojOduzetihRamovaSaLeglom, brojRamovaSaMedomZaVadjenje, brojIzvadjenihRamovaSaMedom,
                    brojUbacenihOsnova, brojUbacenihPraznihRamova, napomena);
            pregledViewModel.insert(pregled);

            Toast.makeText(this, "Pregled je sacuvan", Toast.LENGTH_SHORT).show();
        } else if (requestCode == IZMENI_PREGLED && resultCode == RESULT_OK) {
            int id = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Napomena ne moze biti izmenjena", Toast.LENGTH_SHORT).show();
                return;
            }

            String datumPregleda = data.getStringExtra(Dodaj_IzmeniPregledActivity.EXTRA_DATUM_PREGLEDA);
            boolean matica = data.getBooleanExtra(Dodaj_IzmeniPregledActivity.EXTRA_MATICA, false);
            boolean mladoLeglo = data.getBooleanExtra(Dodaj_IzmeniPregledActivity.EXTRA_MLADO_LEGLO, false);
            boolean maticnjak = data.getBooleanExtra(Dodaj_IzmeniPregledActivity.EXTRA_MATICNJAK, false);
            boolean konstantovanoRojenje = data.getBooleanExtra(Dodaj_IzmeniPregledActivity.EXTRA_KONSTANTOVANO_ROJENJE, false);
            int brojUlicaPopunjenihPcelom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_ULICA_POPUNJENIH_PCELOM, -1);
            int brojRamovaSaLeglom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_LEGLOM, -1);
            int brojRamovaSaVencomHraneUPlodistu = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_VENCOM_HRANE_U_PLODISTU, -1);
            int brojRamovaSaPolenom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_POLENOM, -1);
            int brojRamovaSaLeglomPodignutihUMediste = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_LEGLOM_PODIGNUTIH_U_MEDISTE, -1);
            int brojOduzetihRamovaSaLeglom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_ODUZETIH_RAMOVA_SA_LEGLOM, -1);
            int brojRamovaSaMedomZaVadjenje = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_MEDOM_ZA_VADJENJE, -1);
            int brojIzvadjenihRamovaSaMedom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_IZVADJENIH_RAMOVA_SA_MEDOM, -1);
            int brojUbacenihOsnova = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_UBACENIH_OSNOVA, -1);
            int brojUbacenihPraznihRamova = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_UBACENIH_PRAZNIH_RAMOVA, -1);
            String napomena = data.getStringExtra(Dodaj_IzmeniPregledActivity.EXTRA_NAPOMENA);



            Pregled pregled = new Pregled(datumPregleda, kosnica.getRedniBrojKosnice(), pcelinjak.getRedniBrojPcelinjaka(), matica, mladoLeglo, maticnjak, konstantovanoRojenje,
                    brojUlicaPopunjenihPcelom, brojRamovaSaLeglom, brojRamovaSaVencomHraneUPlodistu, brojRamovaSaPolenom,
                    brojRamovaSaLeglomPodignutihUMediste, brojOduzetihRamovaSaLeglom, brojRamovaSaMedomZaVadjenje, brojIzvadjenihRamovaSaMedom,
                    brojUbacenihOsnova, brojUbacenihPraznihRamova, napomena);
            pregled.setPregledID(id);
            pregledViewModel.update(pregled);

            Toast.makeText(this, "Pregled je izmenjen", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Pregled nije izmenjen", Toast.LENGTH_SHORT).show();
        }
    }

    private void srediRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void srediViewModel() {
        pregledViewModel = ViewModelProviders.of(this).get(PregledViewModel.class);
        kosnicaViewModel = ViewModelProviders.of(this).get(KosnicaViewModel.class);
        srediObservere();
    }

    private void srediObservere() {
        pregledViewModel.getAllPreglediZaKosnicu(kosnica.getRedniBrojKosnice(), pcelinjak.getRedniBrojPcelinjaka()).observe(this, new Observer<List<Pregled>>() {
            @Override
            public void onChanged(@Nullable List<Pregled> pregledi) {
                adapter.submitList(pregledi);
            }
        });
        pregledViewModel.getMaxDatumPregledaZaKosnicu(kosnica.getRedniBrojKosnice(), pcelinjak.getRedniBrojPcelinjaka()).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String datum) {
                maxDatum = datum;
                kosnica.setDatumPoslednjegPregleda(datum);
                kosnicaViewModel.update(kosnica);
            }
        });
    }


    private void srediIzmeniPregledNaKlik() {
        adapter.setOnItemClickListener(new PregledAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Pregled pregled) {
                Intent intent = new Intent(PregledActivity.this, Dodaj_IzmeniPregledActivity.class);

                intent.putExtra(Dodaj_IzmeniPregledActivity.EXTRA_ID, pregled.getPregledID());
                intent.putExtra(Dodaj_IzmeniPregledActivity.EXTRA_DATUM_PREGLEDA, pregled.getDatumPregleda());
                intent.putExtra(Dodaj_IzmeniPregledActivity.EXTRA_MATICA, pregled.isMatica());
                intent.putExtra(Dodaj_IzmeniPregledActivity.EXTRA_MLADO_LEGLO, pregled.isMladoLeglo());
                intent.putExtra(Dodaj_IzmeniPregledActivity.EXTRA_MATICNJAK, pregled.isMaticnjak());
                intent.putExtra(Dodaj_IzmeniPregledActivity.EXTRA_KONSTANTOVANO_ROJENJE, pregled.isKonstantovanoRojenje());
                intent.putExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_ULICA_POPUNJENIH_PCELOM, pregled.getBrojUlicaPopunjenihPcelom());
                intent.putExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_LEGLOM, pregled.getBrojRamovaSaLeglom());
                intent.putExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_VENCOM_HRANE_U_PLODISTU, pregled.getBrojRamovaSaVencomHraneUPlodistu());
                intent.putExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_POLENOM, pregled.getBrojRamovaSaPolenom());
                intent.putExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_LEGLOM_PODIGNUTIH_U_MEDISTE, pregled.getBrojRamovaSaLeglomPodignutihUMediste());
                intent.putExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_ODUZETIH_RAMOVA_SA_LEGLOM, pregled.getBrojOduzetihRamovaSaLeglom());
                intent.putExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_MEDOM_ZA_VADJENJE, pregled.getBrojRamovaSaMedomZaVadjenje());
                intent.putExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_IZVADJENIH_RAMOVA_SA_MEDOM, pregled.getBrojIzvadjenihRamovaSaMedom());
                intent.putExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_UBACENIH_OSNOVA, pregled.getBrojUbacenihOsnova());
                intent.putExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_UBACENIH_PRAZNIH_RAMOVA, pregled.getBrojUbacenihPraznihRamova());
                intent.putExtra(Dodaj_IzmeniPregledActivity.EXTRA_NAPOMENA, pregled.getNapomena());
                startActivityForResult(intent, IZMENI_PREGLED);
            }
        });
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
                pregledViewModel.delete(adapter.getPregledAt(viewHolder.getAdapterPosition()));
                Toast.makeText(PregledActivity.this, "Pregled je izbrisan", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.glavni_meni, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.izbrisiSve:
                Toast.makeText(this, "Sve napomene su izbrisane", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
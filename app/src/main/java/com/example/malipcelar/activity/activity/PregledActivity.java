package com.example.malipcelar.activity.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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

import java.util.List;

import static com.example.malipcelar.activity.activity.OsnovniPodaciActivity.IME_PCELARA;
import static com.example.malipcelar.activity.activity.OsnovniPodaciActivity.SHARED_PREFS;

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
        setTitle("Pregledi");
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
            int brojUlicaPopunjenihPcelom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_ULICA_POPUNJENIH_PCELOM, 0);
            int brojRamovaSaLeglom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_LEGLOM, 0);
            int brojRamovaSaVencomHraneUPlodistu = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_VENCOM_HRANE_U_PLODISTU, -1);
            int brojRamovaSaPolenom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_POLENOM, 0);
            int brojRamovaSaLeglomPodignutihUMediste = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_LEGLOM_PODIGNUTIH_U_MEDISTE, 0);
            int brojOduzetihRamovaSaLeglom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_ODUZETIH_RAMOVA_SA_LEGLOM, 0);
            int brojRamovaSaMedomZaVadjenje = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_MEDOM_ZA_VADJENJE, 0);
            int brojIzvadjenihRamovaSaMedom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_IZVADJENIH_RAMOVA_SA_MEDOM, 0);
            int brojUbacenihOsnova = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_UBACENIH_OSNOVA, 0);
            int brojUbacenihPraznihRamova = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_UBACENIH_PRAZNIH_RAMOVA, 0);
            String napomena = data.getStringExtra(Dodaj_IzmeniPregledActivity.EXTRA_NAPOMENA);

            Pregled pregled = new Pregled(datumPregleda, kosnica.getRedniBrojKosnice(), pcelinjak.getRedniBrojPcelinjaka(), matica, mladoLeglo, maticnjak, konstantovanoRojenje,
                    brojUlicaPopunjenihPcelom, brojRamovaSaLeglom, brojRamovaSaVencomHraneUPlodistu, brojRamovaSaPolenom,
                    brojRamovaSaLeglomPodignutihUMediste, brojOduzetihRamovaSaLeglom, brojRamovaSaMedomZaVadjenje, brojIzvadjenihRamovaSaMedom,
                    brojUbacenihOsnova, brojUbacenihPraznihRamova, napomena);
            pregledViewModel.insert(pregled);

            Toast.makeText(this, "Pregled je sačuvan", Toast.LENGTH_SHORT).show();
        } else if (requestCode == IZMENI_PREGLED && resultCode == RESULT_OK) {
            int id = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Pregled ne može biti izmenjen", Toast.LENGTH_SHORT).show();
                return;
            }

            String datumPregleda = data.getStringExtra(Dodaj_IzmeniPregledActivity.EXTRA_DATUM_PREGLEDA);
            boolean matica = data.getBooleanExtra(Dodaj_IzmeniPregledActivity.EXTRA_MATICA, false);
            boolean mladoLeglo = data.getBooleanExtra(Dodaj_IzmeniPregledActivity.EXTRA_MLADO_LEGLO, false);
            boolean maticnjak = data.getBooleanExtra(Dodaj_IzmeniPregledActivity.EXTRA_MATICNJAK, false);
            boolean konstantovanoRojenje = data.getBooleanExtra(Dodaj_IzmeniPregledActivity.EXTRA_KONSTANTOVANO_ROJENJE, false);
            int brojUlicaPopunjenihPcelom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_ULICA_POPUNJENIH_PCELOM, 0);
            int brojRamovaSaLeglom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_LEGLOM, 0);
            int brojRamovaSaVencomHraneUPlodistu = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_VENCOM_HRANE_U_PLODISTU, 0);
            int brojRamovaSaPolenom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_POLENOM, 0);
            int brojRamovaSaLeglomPodignutihUMediste = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_LEGLOM_PODIGNUTIH_U_MEDISTE, 0);
            int brojOduzetihRamovaSaLeglom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_ODUZETIH_RAMOVA_SA_LEGLOM, 0);
            int brojRamovaSaMedomZaVadjenje = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_MEDOM_ZA_VADJENJE, 0);
            int brojIzvadjenihRamovaSaMedom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_IZVADJENIH_RAMOVA_SA_MEDOM, 0);
            int brojUbacenihOsnova = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_UBACENIH_OSNOVA, 0);
            int brojUbacenihPraznihRamova = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_UBACENIH_PRAZNIH_RAMOVA, 0);
            String napomena = data.getStringExtra(Dodaj_IzmeniPregledActivity.EXTRA_NAPOMENA);

            Pregled pregled = new Pregled(datumPregleda, kosnica.getRedniBrojKosnice(), pcelinjak.getRedniBrojPcelinjaka(), matica, mladoLeglo, maticnjak, konstantovanoRojenje,
                    brojUlicaPopunjenihPcelom, brojRamovaSaLeglom, brojRamovaSaVencomHraneUPlodistu, brojRamovaSaPolenom,
                    brojRamovaSaLeglomPodignutihUMediste, brojOduzetihRamovaSaLeglom, brojRamovaSaMedomZaVadjenje, brojIzvadjenihRamovaSaMedom,
                    brojUbacenihOsnova, brojUbacenihPraznihRamova, napomena);
            pregled.setPregledID(id);
            pregledViewModel.update(pregled);

            Toast.makeText(this, "Pregled je izmenjen", Toast.LENGTH_SHORT).show();
        }
    }

    private String datumZaPrikaz(String datum) {
        String[] datumi = datum.split("-");
        return datumi[2] + "." + datumi[1] + "." + datumi[0];
    }

    private void srediRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void srediViewModel() {
        pregledViewModel = new ViewModelProvider(this).get(PregledViewModel.class);
        kosnicaViewModel = new ViewModelProvider(this).get(KosnicaViewModel.class);
        srediObservere();
    }

    private void srediObservere() {
        pregledViewModel.getAllPreglediZaKosnicu(kosnica.getRedniBrojKosnice(), pcelinjak.getRedniBrojPcelinjaka()).observe(this, new Observer<List<Pregled>>() {
            @Override
            public void onChanged(@Nullable List<Pregled> pregledi) {
                assert pregledi != null;
                if (pregledi.size() == 0) {
                    poruka();
                }
                adapter.submitList(pregledi);
            }
        });
    }

    private void poruka() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String imePcelara = sharedPreferences.getString(IME_PCELARA, "");
        //String gazdinstvo = sharedPreferences.getString(GAZDINSTVO, "");
        String buffer = "\n\nPozdrav, " + imePcelara + "\n\nKlikom na dugme + možete dodati novi pregled u košnicu: " + kosnica.getRedniBrojKosnice() + ". " + kosnica.getNazivKosnice() + " u pčelinjaku: " + pcelinjak.getRedniBrojPcelinjaka() + ". " + pcelinjak.getNazivPcelinjaka() + " \n";
        prikaziPoruku("Pregled", buffer);
    }

    private void srediIzmeniPregledNaKlik() {
        adapter.setOnItemClickListener(new PregledAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Pregled pregled) {
                poruka(pregled);
            }

            @Override
            public void onLongItemClick(Pregled pregled) {
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

    private void poruka(Pregled pregled) {
        String buffer = "Redni broj pčelinjaka: " + pregled.getPcelinjakID() + "\n"
                + "Redni broj košnice: " + pregled.getKosnicaID() + "\n"
                + "Datum: " + datumZaPrikaz(pregled.getDatumPregleda()) + "\n"
                + "Matica: " + pregled.isMatica() + "\n"
                + "Mlado leglo: " + pregled.isMladoLeglo() + "\n"
                + "Maticnjak: " + pregled.isMaticnjak() + "\n"
                + "Konstantovano rojenje: " + pregled.isKonstantovanoRojenje() + "\n"
                + "Broj ullica popunjenih pčelom: " + pregled.getBrojUlicaPopunjenihPcelom() + "\n"
                + "Broj ramova sa leglom: " + pregled.getBrojRamovaSaLeglom() + "\n"
                + "Broj ramova\nsa vencom hrane u plodištu: " + pregled.getBrojRamovaSaVencomHraneUPlodistu() + "\n"
                + "Broj ramova sa polenom: " + pregled.getBrojRamovaSaPolenom() + "\n"
                + "Broj ramova\nsa leglom podignutih u medište: " + pregled.getBrojRamovaSaLeglomPodignutihUMediste() + "\n"
                + "Broj oduzetih ramova sa leglom: " + pregled.getBrojRamovaSaLeglom() + "\n"
                + "Broj ramova sa medom za vađenje: " + pregled.getBrojRamovaSaMedomZaVadjenje() + "\n"
                + "Broj izvađenih ramova sa medom: " + pregled.getBrojIzvadjenihRamovaSaMedom() + "\n"
                + "Broj ubačenih osnova: " + pregled.getBrojUbacenihOsnova() + "\n"
                + "Broj ubačenih praznih ramova: " + pregled.getBrojUbacenihPraznihRamova() + "\n\n"
                + "Napomena: " + pregled.getNapomena() + "\n";
        prikaziPoruku("Pregled", buffer);

    }

    public void prikaziPoruku(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    private void srediBrisanje() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                pregledViewModel.delete(adapter.getPregledAt(viewHolder.getAdapterPosition()));
                Toast.makeText(PregledActivity.this, "Pregled je izbrisan", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }
}
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
import com.example.malipcelar.activity.adapteri.LecenjeAdapter;
import com.example.malipcelar.activity.domen.Kosnica;
import com.example.malipcelar.activity.domen.Lecenje;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.viewModel.KosnicaViewModel;
import com.example.malipcelar.activity.viewModel.LecenjeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.malipcelar.activity.activity.OsnovniPodaciActivity.IME_PCELARA;
import static com.example.malipcelar.activity.activity.OsnovniPodaciActivity.SHARED_PREFS;

public class LecenjeActivity extends AppCompatActivity {

    public static final String EXTRA_KOSNICA =
            "com.example.malipcelar.activity.activity.KOSNICA";
    public static final String EXTRA_PCELINJAK =
            "com.example.malipcelar.activity.activity.PCELINJAK";

    public static final int DODAJ_NOVO_LECENJE = 1;
    public static final int IZMENI_LECENJE = 2;

    Kosnica kosnica;
    Pcelinjak pcelinjak;
    List<Lecenje> lecenja;
    List<Kosnica> kosnice;

    FloatingActionButton btnDodajLecenje;
    RecyclerView recyclerView;
    LecenjeViewModel lecenjeViewModel;
    KosnicaViewModel kosnicaViewModel;

    final LecenjeAdapter adapter = new LecenjeAdapter();

    String maxDatum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecenje_activity);
        setTitle("Lečenja");
        srediAtribute();
        srediListenere();
        srediViewModel();
        srediBrisanje();
        srediIzmeniLecenjeNaKlik();
    }

    private void srediAtribute() {
        Intent intent = getIntent();
        kosnica = (Kosnica) intent.getSerializableExtra(EXTRA_KOSNICA);
        pcelinjak = (Pcelinjak) intent.getSerializableExtra(EXTRA_PCELINJAK);
        recyclerView = findViewById(R.id.rvLecenja);
        maxDatum = null;
        kosnice = null;
        btnDodajLecenje = findViewById(R.id.btnDodajLecenje);
        lecenja = null;
        srediRecycleView();
    }

    private void srediListenere() {
        btnDodajLecenje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LecenjeActivity.this, Dodaj_IzmeniLecenjeActivity.class);
                startActivityForResult(intent, DODAJ_NOVO_LECENJE);
            }
        });
    }

    private void srediRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void srediViewModel() {
        lecenjeViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(LecenjeViewModel.class);
        kosnicaViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(KosnicaViewModel.class);
        srediObservere();

    }

    private void poruka() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String imePcelara = sharedPreferences.getString(IME_PCELARA, "");
        //String gazdinstvo = sharedPreferences.getString(GAZDINSTVO, "");
        String buffer = "\n\nPozdrav, " + imePcelara + "\n\nKlikom na dugme + možete dodati novo lečenje u košnicu: " + kosnica.getRedniBrojKosnice() + ". " + kosnica.getNazivKosnice() + " u pčelinjaku: " + pcelinjak.getRedniBrojPcelinjaka() + ". " + pcelinjak.getNazivPcelinjaka() + " \n";
        prikaziPoruku("Lečenje", buffer);
    }

    private void srediObservere() {
        lecenjeViewModel.getAllLecenjaZaKosnicu(kosnica.getRedniBrojKosnice(), pcelinjak.getRedniBrojPcelinjaka()).observe(this, new Observer<List<Lecenje>>() {
            @Override
            public void onChanged(@Nullable List<Lecenje> lecenja) {
                if (lecenja != null && lecenja.size() == 0) {
                    poruka();
                }
                adapter.submitList(lecenja);
            }
        });
        kosnicaViewModel.getAllKosniceByRbPcelinjaka(pcelinjak.getRedniBrojPcelinjaka()).observe(this, new Observer<List<Kosnica>>() {
            @Override
            public void onChanged(List<Kosnica> kosnice) {
                LecenjeActivity.this.kosnice = kosnice;
            }
        });
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
                izbrisiLecenje(adapter.getLecenjeAt(viewHolder.getAdapterPosition()));
                Toast.makeText(LecenjeActivity.this, "Lečenje je izbrisano", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }

    private void izbrisiLecenje(Lecenje lecenje) {
        lecenjeViewModel.delete(lecenje);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DODAJ_NOVO_LECENJE && resultCode == RESULT_OK) {
            String datumLecenja = data.getStringExtra(Dodaj_IzmeniLecenjeActivity.EXTRA_DATUM_LECENJA);
            boolean primeniNaSveKOsnice = data.getBooleanExtra(Dodaj_IzmeniLecenjeActivity.EXTRA_PRIMENI_LECENJE_NA_CEO_PCELINJAK, false);
            String bolest = data.getStringExtra(Dodaj_IzmeniLecenjeActivity.EXTRA_BOLEST);
            if (primeniNaSveKOsnice) {
                for (Kosnica k : kosnice) {
                    Lecenje lecenje = new Lecenje(k.getRedniBrojKosnice(), pcelinjak.getRedniBrojPcelinjaka(), datumLecenja, bolest);
                    lecenjeViewModel.insert(lecenje);
                }
            } else {
                Lecenje lecenje = new Lecenje(kosnica.getRedniBrojKosnice(), pcelinjak.getRedniBrojPcelinjaka(), datumLecenja, bolest);
                lecenjeViewModel.insert(lecenje);
            }
            Toast.makeText(this, "Lečenje je sačuvano", Toast.LENGTH_SHORT).show();
        } else if (requestCode == IZMENI_LECENJE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(Dodaj_IzmeniLecenjeActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Lečenje ne može biti izmenjeno", Toast.LENGTH_SHORT).show();
                return;
            }

            String datumLecenja = data.getStringExtra(Dodaj_IzmeniLecenjeActivity.EXTRA_DATUM_LECENJA);
            String bolest = data.getStringExtra(Dodaj_IzmeniLecenjeActivity.EXTRA_BOLEST);


            Lecenje lecenje = new Lecenje(kosnica.getRedniBrojKosnice(), pcelinjak.getRedniBrojPcelinjaka(), datumLecenja, bolest);
            lecenje.setLecenjeID(id);
            lecenjeViewModel.update(lecenje);

            Toast.makeText(this, "Lečenje je izmenjeno", Toast.LENGTH_SHORT).show();
        }
    }

    private void srediIzmeniLecenjeNaKlik() {
        adapter.setOnItemClickListener(new LecenjeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Lecenje lecenje) {
                poruka(lecenje);
            }

            @Override
            public void onLongItemClick(Lecenje lecenje) {
                Intent intent = new Intent(LecenjeActivity.this, Dodaj_IzmeniLecenjeActivity.class);
                intent.putExtra(Dodaj_IzmeniLecenjeActivity.EXTRA_ID, lecenje.getLecenjeID());
                intent.putExtra(Dodaj_IzmeniLecenjeActivity.EXTRA_DATUM_LECENJA, lecenje.getDatumLecenja());
                intent.putExtra(Dodaj_IzmeniLecenjeActivity.EXTRA_BOLEST, lecenje.getBolest());

                startActivityForResult(intent, IZMENI_LECENJE);
            }
        });
    }

    private void poruka(@NonNull Lecenje lecenje) {
        String buffer = "Redni broj pčelinjaka: " + lecenje.getPcelinjakID() + "\n"
                + "Redni broj košnice: " + lecenje.getKosnicaID() + "\n\n"
                + "Datum: " + datumZaPrikaz(lecenje.getDatumLecenja()) + "\n\n"
                + "Bolest: " + lecenje.getBolest() + "\n";
        prikaziPoruku("Lečenje", buffer);
    }

    public void prikaziPoruku(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    @NonNull
    private String datumZaPrikaz(@NonNull String datum) {
        String[] datumi = datum.split("-");
        return datumi[2] + "." + datumi[1] + "." + datumi[0];
    }

}
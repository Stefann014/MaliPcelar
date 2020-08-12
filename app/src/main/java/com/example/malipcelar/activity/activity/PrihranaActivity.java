package com.example.malipcelar.activity.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.adapteri.PrihranaAdapter;
import com.example.malipcelar.activity.domen.Kosnica;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.domen.Prihrana;
import com.example.malipcelar.activity.fragmenti.DatumPickerFragment;
import com.example.malipcelar.activity.fragmenti.DialogNovoLecenjePogaca;
import com.example.malipcelar.activity.fragmenti.DialogNovoLecenjeSirup;
import com.example.malipcelar.activity.fragmenti.PogacaIliSirupBottomSheetDialog;
import com.example.malipcelar.activity.viewModel.KosnicaViewModel;
import com.example.malipcelar.activity.viewModel.PrihranaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import static com.example.malipcelar.activity.activity.OsnovniPodaciActivity.IME_PCELARA;
import static com.example.malipcelar.activity.activity.OsnovniPodaciActivity.SHARED_PREFS;

public class PrihranaActivity extends AppCompatActivity implements PogacaIliSirupBottomSheetDialog.BottomSheetListener, DialogNovoLecenjePogaca.DialogNovoLecenjeListener, DialogNovoLecenjeSirup.DialogNovoLecenjeSirupListener, DatePickerDialog.OnDateSetListener {

    public static final String EXTRA_KOSNICA =
            "com.example.malipcelar.activity.activity.KOSNICA";
    public static final String EXTRA_PCELINJAK =
            "com.example.malipcelar.activity.activity.PCELINJAK";

    Kosnica kosnica;
    Pcelinjak pcelinjak;
    FloatingActionButton btnDodajNovuPrihranu;
    RecyclerView recyclerView;
    List<Prihrana> prihrane;
    final PrihranaAdapter adapter = new PrihranaAdapter();
    PrihranaViewModel prihranaViewModel;
    KosnicaViewModel kosnicaViewModel;
    String maxDatum;
    List<Kosnica> kosnice;
    DialogNovoLecenjeSirup dialogNovoLecenjeSirup;
    DialogNovoLecenjePogaca dialogNovoLecenjePogaca;
    boolean sirup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prihrana_activity);
        setTitle("Prihrane");
        srediAtribute();
        srediListenere();
        srediViewModel();
        srediBrisanje();
        prikaziPrihranuNaKlik();
    }

    private void srediListenere() {
        btnDodajNovuPrihranu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PogacaIliSirupBottomSheetDialog bottomSheet = new PogacaIliSirupBottomSheetDialog();
                bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
            }
        });
    }

    @Override
    public void onBtnPogacaClicked() {
        dialogNovoLecenjePogaca = new DialogNovoLecenjePogaca(PrihranaActivity.this);
        dialogNovoLecenjePogaca.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void onBtnSirupClicked() {
        dialogNovoLecenjeSirup = new DialogNovoLecenjeSirup(PrihranaActivity.this);
        dialogNovoLecenjeSirup.show(getSupportFragmentManager(), "example dialog");
    }

    private void srediAtribute() {
        Intent intent = getIntent();
        kosnica = (Kosnica) intent.getSerializableExtra(EXTRA_KOSNICA);
        pcelinjak = (Pcelinjak) intent.getSerializableExtra(EXTRA_PCELINJAK);
        maxDatum = null;
        btnDodajNovuPrihranu = findViewById(R.id.btnDodajNovuPrihranu);
        recyclerView = findViewById(R.id.rvPrihrane);
        prihrane = null;
        kosnice = null;
        srediRecycleView();

    }

    @Override
    public void sacuvaj(String datum, double kg, boolean primeniNaSve, String vrstaPrihrane) {
        if (primeniNaSve) {
            for (Kosnica k : kosnice) {
                Prihrana p = new Prihrana(k.getRedniBrojKosnice(), pcelinjak.getRedniBrojPcelinjaka(), prevediDatumUFormatZaBazu(datum), vrstaPrihrane, kg);
                prihranaViewModel.insert(p);
            }
        } else {
            Prihrana prihrana = new Prihrana(kosnica.getRedniBrojKosnice(), pcelinjak.getRedniBrojPcelinjaka(), prevediDatumUFormatZaBazu(datum), vrstaPrihrane, kg);
            prihranaViewModel.insert(prihrana);
        }
        Toast.makeText(this, "Prihrana je sačuvana", Toast.LENGTH_LONG).show();
    }

    @Override
    public void sacuvajSirup(String datum, double litar, boolean primeniNaSve, String vrstaPrihrane) {
        if (primeniNaSve) {
            for (Kosnica k : kosnice) {
                Prihrana p = new Prihrana(k.getRedniBrojKosnice(), pcelinjak.getRedniBrojPcelinjaka(), prevediDatumUFormatZaBazu(datum), vrstaPrihrane, litar);
                prihranaViewModel.insert(p);
            }
        } else {
            Prihrana prihrana = new Prihrana(kosnica.getRedniBrojKosnice(), pcelinjak.getRedniBrojPcelinjaka(), prevediDatumUFormatZaBazu(datum), vrstaPrihrane, litar);
            prihranaViewModel.insert(prihrana);
        }
        Toast.makeText(this, "Prihrana je sačuvana", Toast.LENGTH_LONG).show();
    }

    @NonNull
    private String prevediDatumUFormatZaBazu(String datum) {
        datum = datum.substring(0, datum.length() - 1);
        datum = datum.replace('.', '-');
        String[] datumi = datum.split("-");
        return datumi[2] + "-" + datumi[1] + "-" + datumi[0];
    }

    private void srediRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void srediViewModel() {
        prihranaViewModel = new ViewModelProvider(this).get(PrihranaViewModel.class);
        kosnicaViewModel = new ViewModelProvider(this).get(KosnicaViewModel.class);
        srediObservere();
    }

    private void srediObservere() {
        prihranaViewModel.getAllPrihranaZaKosnicu(kosnica.getRedniBrojKosnice(), pcelinjak.getRedniBrojPcelinjaka()).observe(this, new Observer<List<Prihrana>>() {
            @Override
            public void onChanged(@Nullable List<Prihrana> prihrane) {
                assert prihrane != null;
                if (prihrane.size() == 0) {
                    poruka();
                }
                adapter.submitList(prihrane);
            }
        });

        kosnicaViewModel.getAllKosniceByRbPcelinjaka(pcelinjak.getRedniBrojPcelinjaka()).observe(this, new Observer<List<Kosnica>>() {
            @Override
            public void onChanged(List<Kosnica> kosnice) {
                PrihranaActivity.this.kosnice = kosnice;
            }
        });
    }

    private void poruka() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String imePcelara = sharedPreferences.getString(IME_PCELARA, "");
        //String gazdinstvo = sharedPreferences.getString(GAZDINSTVO, "");
        String buffer = "\n\nPozdrav, " + imePcelara + "\n\nKlikom na dugme + možete dodati novu prihranu u košnicu: " + kosnica.getRedniBrojKosnice() + ". " + kosnica.getNazivKosnice() + " u pčelinjaku: " + pcelinjak.getRedniBrojPcelinjaka() + ". " + pcelinjak.getNazivPcelinjaka() + " \n";
        prikaziPoruku("Prihrana", buffer);
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
                prihranaViewModel.delete(adapter.getPrihranaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(PrihranaActivity.this, "Prihrana je izbrisana", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }


    private void prikaziPrihranuNaKlik() {
        adapter.setOnItemClickListener(new PrihranaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Prihrana prihrana) {
                String prih;
                if (prihrana.getVrstaPrihrane().equals("Sirup")) {
                    prih = "l";
                } else {
                    prih = "kg";
                }

                String poruka = "Pcelinjak: " + pcelinjak.getRedniBrojPcelinjaka() + ". " + pcelinjak.getNazivPcelinjaka() + "\n" +
                        "Kosnica: " + kosnica.getRedniBrojKosnice() + ". " + kosnica.getNazivKosnice() + "\n" +
                        "Datum: " + datumZaPrikaz(prihrana.getDatumPrihrane()) +
                        "\nVrsta prihrane: " + prihrana.getVrstaPrihrane() + "\n" +
                        "Količina prihrane: " + prihrana.getKolicinaPrihrane() + " " + prih + "\n\n";
                prikaziPoruku("Prihrana: ", poruka);

            }
        });
    }

    public void prikaziPoruku(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    private String datumZaPrikaz(String datum) {
        String[] datumi = datum.split("-");
        return datumi[2] + "." + datumi[1] + "." + datumi[0];
    }

    public void otvoriKalendar(String string) {
        sirup = string.equals("sirup");
        DialogFragment datePicker = new DatumPickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        if (sirup) {
            dialogNovoLecenjeSirup.setBtnDatumPrihrane(currentDateString);
        } else {
            dialogNovoLecenjePogaca.setBtnDatumPrihrane(currentDateString);
        }
    }
}
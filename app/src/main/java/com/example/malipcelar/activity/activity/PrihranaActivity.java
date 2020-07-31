package com.example.malipcelar.activity.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.adapteri.PrihranaAdapter;
import com.example.malipcelar.activity.domen.Kosnica;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.domen.Prihrana;
import com.example.malipcelar.activity.fragmenti.BottomSheetDialog;
import com.example.malipcelar.activity.fragmenti.DialogNovoLecenjePogaca;
import com.example.malipcelar.activity.fragmenti.DialogNovoLecenjeSirup;
import com.example.malipcelar.activity.viewModel.PrihranaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PrihranaActivity extends AppCompatActivity implements BottomSheetDialog.BottomSheetListener, DialogNovoLecenjePogaca.DialogNovoLecenjeListener, DialogNovoLecenjeSirup.DialogNovoLecenjeSirupListener {

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
    boolean primeniNaSve;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prihrana_activity);

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
                BottomSheetDialog bottomSheet = new BottomSheetDialog();
                bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
            }
        });
    }

    @Override
    public void onBtnPogacaClicked() {
        DialogNovoLecenjePogaca dialogNovoLecenjePogaca = new DialogNovoLecenjePogaca();
        dialogNovoLecenjePogaca.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void onBtnSirupClicked() {
        DialogNovoLecenjeSirup dialogNovoLecenjeSirup = new DialogNovoLecenjeSirup();
        dialogNovoLecenjeSirup.show(getSupportFragmentManager(), "example dialog");
    }

    private void srediAtribute() {
        Intent intent = getIntent();
        kosnica = (Kosnica) intent.getSerializableExtra(EXTRA_KOSNICA);
        pcelinjak = (Pcelinjak) intent.getSerializableExtra(EXTRA_PCELINJAK);
        btnDodajNovuPrihranu = findViewById(R.id.btnDodajNovuPrihranu);
        recyclerView = findViewById(R.id.rvPrihrane);
        prihrane = null;
        primeniNaSve = false;
        srediRecycleView();

    }

    @Override
    public void sacuvaj(String datum, double kg, boolean primeniNaSve, String vrstaPrihrane) {
        this.primeniNaSve = primeniNaSve;
        Prihrana prihrana = new Prihrana(kosnica.getRedniBrojKosnice(), pcelinjak.getRedniBrojPcelinjaka(), prevediDatumUFormatZaBazu(datum), vrstaPrihrane, kg);
        prihranaViewModel.insert(prihrana);
    }

    @NonNull
    private String prevediDatumUFormatZaBazu(String datum) {
        //treba nam format yyyy-MM-dd
        datum = datum.substring(0, datum.length() - 1);
        datum = datum.replace('.', '-');
        String[] datumi = datum.split("-");
        String dobarDatum = datumi[2] + "-" + datumi[1] + "-" + datumi[0];
        return dobarDatum;
    }

    private void srediRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void srediViewModel() {
        prihranaViewModel = ViewModelProviders.of(this).get(PrihranaViewModel.class);
        prihranaViewModel.getAllPrihranaZaKosnicu(kosnica.getRedniBrojKosnice(), pcelinjak.getRedniBrojPcelinjaka()).observe(this, new Observer<List<Prihrana>>() {
            @Override
            public void onChanged(@Nullable List<Prihrana> prihrane) {
                adapter.submitList(prihrane);
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
                prihranaViewModel.delete(adapter.getPrihranaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(PrihranaActivity.this, "Prihrana je izbrisana", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    public void sacuvajSirup(String datum, double litar, boolean primeniNaSve, String vrstaPrihrane) {
        this.primeniNaSve = primeniNaSve;
        Prihrana prihrana = new Prihrana(kosnica.getRedniBrojKosnice(), pcelinjak.getRedniBrojPcelinjaka(), prevediDatumUFormatZaBazu(datum), vrstaPrihrane, litar);
        prihranaViewModel.insert(prihrana);
    }

    private void prikaziPrihranuNaKlik() {
        adapter.setOnItemClickListener(new PrihranaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Prihrana prihrana) {

                StringBuffer buffer = new StringBuffer();
                buffer.append("Pcelinjak: " + pcelinjak.getRedniBrojPcelinjaka() + ". " + pcelinjak.getNazivPcelinjaka() + "\n");
                buffer.append("Kosnica: " + kosnica.getRedniBrojKosnice() + ". " + kosnica.getNazivKosnice() + "\n");
                buffer.append("Datum: " + datumZaPrikaz(prihrana.getDatumPrihrane() + "\n"));
                buffer.append("Vrsta prihrane:" + prihrana.getVrstaPrihrane() + "\n");
                buffer.append("Kolicina prihrane:" + prihrana.getKolicinaPrihrane() + "\n\n");


                showMessage("Lecenje: ", buffer.toString());

            }
        });
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    private String datumZaPrikaz(String datum) {
        String[] datumi = datum.split("-");
        String dobarDatum = datumi[2] + "." + datumi[1] + "." + datumi[0];
        return dobarDatum;
    }
}
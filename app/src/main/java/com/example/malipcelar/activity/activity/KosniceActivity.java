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
import com.example.malipcelar.activity.adapteri.KosniceAdapter;
import com.example.malipcelar.activity.domen.Kosnica;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.viewModel.KosnicaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class KosniceActivity extends AppCompatActivity {

    private static final int DODAJ_NOVU_KOSNICU = 1;
    private static final int IZMENI_KOSNICU = 2;

    public static final String EXTRA_PCELINJAK =
            "com.example.malipcelar.activity.activity.PCELINJAK";

    //vars
    Pcelinjak pcelinjak;
    FloatingActionButton btnDodajKosnicu;
    private KosnicaViewModel kosnicaViewModel;
    RecyclerView recyclerView;
    final KosniceAdapter adapter = new KosniceAdapter();
    List<Kosnica> kosnice;
    List<Integer> zauzetiRBovi;
    int stariRb;

    int pauza = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kosnica_activity);

        srediAtribute();
        srediRecycleView();
        srediListenere();
        srediKomunikacijuSaViewModel();
        srediBrisanje();
        srediIzmeniKosnicuNaKlik();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (pauza == 0) {
            pauza = 1;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pauza == 1) {
            recreate();
            adapter.notifyDataSetChanged();
        }
        pauza = 0;
    }

    private void srediAtribute() {
        Intent intent = getIntent();
        this.pcelinjak = (Pcelinjak) intent.getSerializableExtra(EXTRA_PCELINJAK);
        btnDodajKosnicu = findViewById(R.id.btnDodajNovuKosnicu);
        recyclerView = findViewById(R.id.rvKosnice);
        kosnice = null;
        zauzetiRBovi = null;
        stariRb = -1;

    }

    private void srediListenere() {
        btnDodajKosnicu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KosniceActivity.this, Dodaj_IzmeniKosnicuActivity.class);

                ArrayList<Integer> zauzeti = (ArrayList<Integer>) zauzetiRBovi;
                intent.putExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_ZAUZETI_RB, zauzeti);

                startActivityForResult(intent, DODAJ_NOVU_KOSNICU);

            }
        });
    }

    private void srediRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    private void srediKomunikacijuSaViewModel() {
        kosnicaViewModel = ViewModelProviders.of(this).get(KosnicaViewModel.class);
        srediObservere();
    }

    private void srediObservere() {

        kosnicaViewModel.getAllKosniceByRbPcelinjaka(pcelinjak.getRedniBrojPcelinjaka()).observe(this, new Observer<List<Kosnica>>() {
            @Override
            public void onChanged(@Nullable List<Kosnica> kosnice) {
                KosniceActivity.this.kosnice = kosnice;
                adapter.submitList(kosnice);
            }
        });

        kosnicaViewModel.getAllRBKosniceZaPcelinjak(pcelinjak.getRedniBrojPcelinjaka()).observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                zauzetiRBovi = integers;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DODAJ_NOVU_KOSNICU && resultCode == RESULT_OK) {

            int kosnicaRB = data.getIntExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_RB_KOSNICE, -1);
            String godinaProizvodnjeMatice = data.getStringExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_GODINA_PROIZVODNJE_MATICE);
            Boolean selekcionisana = data.getBooleanExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_RB_SELEKCIONISANA, false);
            Boolean prirodna = data.getBooleanExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_RB_PRIRODNA, false);
            String bolesti = data.getStringExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_BOLESTI);
            String napomena = data.getStringExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_NAPOMENA);
            String naziv = data.getStringExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_REGISTARSKI_BROJ_KOSNICE);

            Kosnica k = new Kosnica(kosnicaRB, pcelinjak.getRedniBrojPcelinjaka(), naziv, godinaProizvodnjeMatice, selekcionisana, prirodna, bolesti, napomena);
            kosnicaViewModel.insert(k);

            Toast.makeText(this, "Košnica je sačuvana", Toast.LENGTH_SHORT).show();

        } else if (requestCode == IZMENI_KOSNICU && resultCode == RESULT_OK) {

            int id = data.getIntExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_RB_KOSNICE, -1);
            if (id == -1) {
                Toast.makeText(this, "Košnica ne moze biti izmenjena", Toast.LENGTH_SHORT).show();
                return;
            }

            int kosnicaRB = data.getIntExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_RB_KOSNICE, -1);
            String godinaProizvodnjeMatice = data.getStringExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_GODINA_PROIZVODNJE_MATICE);
            Boolean selekcionisana = data.getBooleanExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_RB_SELEKCIONISANA, false);
            Boolean prirodna = data.getBooleanExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_RB_PRIRODNA, false);
            String bolesti = data.getStringExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_BOLESTI);
            String napomena = data.getStringExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_NAPOMENA);
            String naziv = data.getStringExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_REGISTARSKI_BROJ_KOSNICE);

            Kosnica k = new Kosnica(kosnicaRB, pcelinjak.getRedniBrojPcelinjaka(), naziv, godinaProizvodnjeMatice, selekcionisana, prirodna, bolesti, napomena);

            kosnicaViewModel.update(k);
            if (stariRb != -1) {
                kosnicaViewModel.updateRb(stariRb, kosnicaRB);
            }
        } else {
            Toast.makeText(this, "Košnica nije izmenjena", Toast.LENGTH_SHORT).show();
        }
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
                kosnicaViewModel.delete(adapter.getKosnicaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(KosniceActivity.this, "Košniica je izbrisana", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void srediIzmeniKosnicuNaKlik() {
        adapter.setOnItemClickListener(new KosniceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Kosnica kosnica) {
                poruka(kosnica);
            }

            @Override
            public void onPregledClick(Kosnica kosnica) {
                Intent intent = new Intent(KosniceActivity.this, PregledActivity.class);
                intent.putExtra(PregledActivity.EXTRA_PCELINJAK, pcelinjak);
                intent.putExtra(PregledActivity.EXTRA_KOSNICA, kosnica);
                startActivity(intent);
            }

            @Override
            public void onLecenjeClick(Kosnica kosnica) {
                Intent intent = new Intent(KosniceActivity.this, LecenjeActivity.class);
                intent.putExtra(LecenjeActivity.EXTRA_KOSNICA, kosnica);
                intent.putExtra(LecenjeActivity.EXTRA_PCELINJAK, pcelinjak);
                startActivity(intent);
            }

            @Override
            public void onPrihranaClick(Kosnica kosnica) {
                Intent intent = new Intent(KosniceActivity.this, PrihranaActivity.class);
                intent.putExtra(PrihranaActivity.EXTRA_KOSNICA, kosnica);
                intent.putExtra(PrihranaActivity.EXTRA_PCELINJAK, pcelinjak);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(Kosnica kosnica) {

                Intent intent = new Intent(KosniceActivity.this, Dodaj_IzmeniKosnicuActivity.class);
                intent.putExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_RB_KOSNICE, kosnica.getRedniBrojKosnice());
                stariRb = kosnica.getRedniBrojKosnice();
                intent.putExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_GODINA_PROIZVODNJE_MATICE, kosnica.getGodinaProizvodnjeMatice());
                intent.putExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_REGISTARSKI_BROJ_KOSNICE, kosnica.getNazivKosnice());
                intent.putExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_RB_SELEKCIONISANA, kosnica.getSelekcionisana());
                intent.putExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_RB_PRIRODNA, kosnica.getPrirodna());
                intent.putExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_BOLESTI, kosnica.getBolesti());
                intent.putExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_NAPOMENA, kosnica.getNapomena());

                ArrayList<Integer> zauzeti = (ArrayList<Integer>) zauzetiRBovi;
                intent.putExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_ZAUZETI_RB, zauzeti);

                startActivityForResult(intent, IZMENI_KOSNICU);
            }
        });
    }

    private void poruka(Kosnica kosnica) {
        String prir;
        if (kosnica.getPrirodna()) {
            prir = "prirodna";
        } else {
            prir = "selekcionisana";
        }
        String buffer = "Redni broj pčelinjaka: " + kosnica.getRednibrojPcelinjaka() + "\n"
                + "Redni broj košnice: " + kosnica.getRedniBrojKosnice() + "\n"
                + "Registarski broj/Naziv: " + kosnica.getNazivKosnice() + "\n"
                + "Vrsta: " + prir + "\n\n"
                + "Bolesti: " + kosnica.getBolesti() + "\n\n"
                + "Napomena: " + kosnica.getNapomena() + "\n";

        prikaziPoruku("Košnica", buffer);

    }

    public void prikaziPoruku(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


}
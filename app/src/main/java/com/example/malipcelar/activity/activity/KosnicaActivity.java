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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.adapteri.KosniceAdapter;
import com.example.malipcelar.activity.domen.Kosnica;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.viewModel.KosnicaViewModel;
import com.example.malipcelar.activity.viewModel.PcelinjakViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class KosnicaActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kosnica_activity);

        srediAtribute();
        srediListenere();
        srediKomunikacijuSaViewModel();
    }


    private void srediAtribute() {
        Intent intent = getIntent();
        this.pcelinjak = (Pcelinjak) intent.getSerializableExtra(EXTRA_PCELINJAK);
        btnDodajKosnicu = findViewById(R.id.btnDodajNovuKosnicu);
        recyclerView = findViewById(R.id.rvKosnice);
        kosnice = null;
        zauzetiRBovi = null;
        srediRecycleView();
    }

    private void srediListenere() {
        btnDodajKosnicu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KosnicaActivity.this, Dodaj_IzmeniKosnicuActivity.class);

                ArrayList<Integer> zauzeti = (ArrayList<Integer>) zauzetiRBovi;
                intent.putExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_ZAUZETI_RB, zauzeti);

                startActivityForResult(intent, DODAJ_NOVU_KOSNICU);
            }
        });
    }

    private void srediRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
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
                KosnicaActivity.this.kosnice = kosnice;
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
            Boolean selekcionisana = data.getBooleanExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_RB_SELEKCIONISANA,false);
            Boolean prirodna = data.getBooleanExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_RB_PRIRODNA,false);
            String bolesti = data.getStringExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_BOLESTI);
            String napomena = data.getStringExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_NAPOMENA);

            Kosnica k = new Kosnica(kosnicaRB,pcelinjak.getRedniBrojPcelinjaka(),"",godinaProizvodnjeMatice,selekcionisana,prirodna,bolesti,napomena);
            kosnicaViewModel.insert(k);

            Toast.makeText(this, "Kosnica je sacuvana", Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
        }/* else if (requestCode == IZMENI_PCELINJAK && resultCode == RESULT_OK) {

            int id = data.getIntExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_RB, -1);
            if (id == -1) {
                Toast.makeText(this, "Pcelinjak ne moze biti izmenjen", Toast.LENGTH_SHORT).show();
                return;
            }
            int pcelinjakRB = data.getIntExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_RB, -1);
            String nazivPcelinjaka = data.getStringExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_NAZIV_PCELINJAKA);
            String lokacija = data.getStringExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_LOKACIJA);
            String nadmorskaVisina = data.getStringExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_NADMORSKA_VISINA);
            String slika = data.getStringExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_SLIKA);
            Pcelinjak pcelinjak = new Pcelinjak(pcelinjakRB, nazivPcelinjaka, lokacija, nadmorskaVisina, slika);

            pcelinjakViewModel.update(pcelinjak);
            if (stariRb != -1) {
                pcelinjakViewModel.updateRb(stariRb, pcelinjakRB);

            }
            adapter.notifyDataSetChanged();
            Toast.makeText(this, pcelinjak.toString(), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Pcelinjak nije izmenjen", Toast.LENGTH_SHORT).show();
        }*/
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
                kosnicaViewModel.deleteAllKosnice();
                Toast.makeText(this, "Sve kosnice su izbrisane", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
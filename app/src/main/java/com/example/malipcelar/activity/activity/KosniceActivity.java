package com.example.malipcelar.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.adapteri.KosniceAdapter;
import com.example.malipcelar.activity.adapteri.PcelinjaciAdapter;
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
    ConstraintLayout expandableView;
    Button arrowBtn;
    CardView cardView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kosnica_activity);

        srediAtribute();
        srediListenere();
        srediKomunikacijuSaViewModel();
        srediBrisanje();
        srediIzmeniKosnicuNaKlik();
    }


    private void srediAtribute() {
        Intent intent = getIntent();
        this.pcelinjak = (Pcelinjak) intent.getSerializableExtra(EXTRA_PCELINJAK);
        btnDodajKosnicu = findViewById(R.id.btnDodajNovuKosnicu);
        recyclerView = findViewById(R.id.rvKosnice);
        kosnice = null;
        zauzetiRBovi = null;
        stariRb = -1;
        expandableView = findViewById(R.id.expandableView);
        arrowBtn = findViewById(R.id.arrowBtn);
        cardView = findViewById(R.id.cardViewKosnica);

        srediRecycleView();

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

            Kosnica k = new Kosnica(kosnicaRB, pcelinjak.getRedniBrojPcelinjaka(), "", godinaProizvodnjeMatice, selekcionisana, prirodna, bolesti, napomena);
            kosnicaViewModel.insert(k);

            Toast.makeText(this, "Kosnica je sacuvana", Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
        } else if (requestCode == IZMENI_KOSNICU && resultCode == RESULT_OK) {

            int id = data.getIntExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_RB_KOSNICE, -1);
            if (id == -1) {
                Toast.makeText(this, "Kosnica ne moze biti izmenjen", Toast.LENGTH_SHORT).show();
                return;
            }

            int kosnicaRB = data.getIntExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_RB_KOSNICE, -1);
            String godinaProizvodnjeMatice = data.getStringExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_GODINA_PROIZVODNJE_MATICE);
            Boolean selekcionisana = data.getBooleanExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_RB_SELEKCIONISANA, false);
            Boolean prirodna = data.getBooleanExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_RB_PRIRODNA, false);
            String bolesti = data.getStringExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_BOLESTI);
            String napomena = data.getStringExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_NAPOMENA);


            Kosnica k = new Kosnica(kosnicaRB, pcelinjak.getRedniBrojPcelinjaka(), "", godinaProizvodnjeMatice, selekcionisana, prirodna, bolesti, napomena);

            kosnicaViewModel.update(k);
            if (stariRb != -1) {
                kosnicaViewModel.updateRb(stariRb, kosnicaRB);

            }
            adapter.notifyDataSetChanged();
            Toast.makeText(this, k.toString(), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Kosnica nije izmenjena", Toast.LENGTH_SHORT).show();
        }
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
                kosnicaViewModel.delete(adapter.getKosnicaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(KosniceActivity.this, "Kosniica je izbrisana", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void srediIzmeniKosnicuNaKlik() {
        adapter.setOnItemClickListener(new KosniceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Kosnica kosnica) {

                Intent intent = new Intent(KosniceActivity.this, Dodaj_IzmeniKosnicuActivity.class);
                intent.putExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_RB_KOSNICE, kosnica.getRedniBrojKosnice());
                stariRb = kosnica.getRedniBrojKosnice();
                intent.putExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_GODINA_PROIZVODNJE_MATICE, kosnica.getGodinaProizvodnjeMatice());
                intent.putExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_RB_SELEKCIONISANA, kosnica.getSelekciona());
                intent.putExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_RB_PRIRODNA, kosnica.getPrirodna());
                intent.putExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_BOLESTI, kosnica.getBolesti());
                intent.putExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_NAPOMENA, kosnica.getNapomena());

                ArrayList<Integer> zauzeti = (ArrayList<Integer>) zauzetiRBovi;
                intent.putExtra(Dodaj_IzmeniKosnicuActivity.EXTRA_ZAUZETI_RB, zauzeti);

                startActivityForResult(intent, IZMENI_KOSNICU);
            }
        });
/*
        adapter.setOnIzmeniClickListener(new KosniceAdapter.OnDropdownClickListener() {

            @Override
            public void onItemClickk(Kosnica kosnica) {
                Log.d("TAG","ODJE SAM");

                if (expandableView.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView.setVisibility(View.VISIBLE);
                    arrowBtn.setBackgroundResource(R.drawable.ic_dropdown_strelica_gore);
                } else {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView.setVisibility(View.GONE);
                    arrowBtn.setBackgroundResource(R.drawable.ic_dropdown_strelica_dole);
                }

            }
        });
*/

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
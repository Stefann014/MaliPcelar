package com.example.malipcelar.activity.activity;

import android.content.Intent;
import android.os.Bundle;

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

import java.util.List;

public class KosnicaActivity extends AppCompatActivity {

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

    }
}
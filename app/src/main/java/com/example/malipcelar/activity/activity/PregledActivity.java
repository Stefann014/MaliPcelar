package com.example.malipcelar.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.domen.Kosnica;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PregledActivity extends AppCompatActivity {

    public static final String EXTRA_KOSNICA =
            "com.example.malipcelar.activity.activity.KOSNICA";
    public static final String EXTRA_PCELINJAK =
            "com.example.malipcelar.activity.activity.PCELINJAK";

    public static final int DODAJ_NOVI_PREGLED = 1;
    public static final int IZMENI_PREGLED = 2;

    FloatingActionButton btnDodajPregled;
    RecyclerView recyclerView;

    Kosnica kosnica;
    Pcelinjak pcelinjak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pregled_activity);

        srediAtribute();
        srediListenere();

    }

    private void srediAtribute() {
        Intent intent = getIntent();
        kosnica = (Kosnica) intent.getSerializableExtra(EXTRA_KOSNICA);
        pcelinjak = (Pcelinjak) intent.getSerializableExtra(EXTRA_PCELINJAK);
        recyclerView = findViewById(R.id.rvPregledi);
        btnDodajPregled = findViewById(R.id.btnDodajNoviPregled);
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

}
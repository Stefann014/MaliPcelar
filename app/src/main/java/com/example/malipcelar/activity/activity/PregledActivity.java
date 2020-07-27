package com.example.malipcelar.activity.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.domen.Kosnica;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PregledActivity extends AppCompatActivity {

    public static final String EXTRA_KOSNICA =
            "com.example.malipcelar.activity.activity.KOSNICA";

    public static final int DODAJ_NOVI_PREGLED = 1;
    public static final int IZMENI_PREGLED = 2;

    FloatingActionButton btnDodajPregled;
    RecyclerView recyclerView;

    Kosnica kosnica;

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
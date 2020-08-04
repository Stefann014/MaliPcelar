package com.example.malipcelar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.malipcelar.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BilansProizvodaActivity extends AppCompatActivity {

    public static final int DODAJ_NOVU_PASU = 1;

    FloatingActionButton btnPrikaziIstorijuPasa;
    FloatingActionButton btnDodajNovuPasu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bilans_proizvoda_activity);

        srediAtribute();
        srediListenere();
    }

    private void srediListenere() {
        btnDodajNovuPasu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BilansProizvodaActivity.this, NovaPasaActivity.class);
                startActivityForResult(intent, DODAJ_NOVU_PASU);
            }
        });
        btnPrikaziIstorijuPasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BilansProizvodaActivity.this, IstorijaPasaActivity.class);
                startActivity(intent);
            }
        });
    }

    private void srediAtribute() {
        btnDodajNovuPasu = findViewById(R.id.btnDodajNovuPasu);
        btnPrikaziIstorijuPasa = findViewById(R.id.btnIstorijaPasa);
    }
}
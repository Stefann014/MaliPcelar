package com.example.malipcelar.activity.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.malipcelar.R;

public class PocetniActivity extends AppCompatActivity {

    Button btnNapomene;
    Button btnPcelinjaci;
    Button btnIstorijaAktivnosti;
    Button btnBilansProivoda;
    Button btnOsnovniPodaci;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pocetni_activity);

        proveriDaLiJePrviPut();

        srediAtribute();
        srediListener();

    }

    private void proveriDaLiJePrviPut() {
        boolean prviPut = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("prviPut", true);


        if (prviPut) {
            startActivity(new Intent(PocetniActivity.this, OsnovniPodaciActivity.class));
        }

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("prviPut", false).apply();
    }

    private void srediAtribute() {
        btnNapomene = findViewById(R.id.btnNapomene);
        btnPcelinjaci = findViewById(R.id.btnPcelinjaci);
        btnIstorijaAktivnosti = findViewById(R.id.btnIstorijaAktivnosti);
        btnBilansProivoda = findViewById(R.id.btnBilansProizvoda);
        btnOsnovniPodaci = findViewById(R.id.btnOsnovniPodaci);
    }

    private void srediListener() {
        btnNapomene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otvoriActivityOpsteNapomene();
            }
        });
        btnPcelinjaci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otvoriActivityPcelinjaci();
            }
        });
        btnIstorijaAktivnosti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otvoriActivityIstorijaAktivnosti();
            }
        });
        btnBilansProivoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otvoriActivityBilansProizvoda();
            }
        });
        btnOsnovniPodaci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otvoriOsnovnePodatke();
            }
        });
    }

    private void otvoriActivityIstorijaAktivnosti() {
        Intent intent = new Intent(this, IstorijaAktivnostiActivity.class);
        startActivity(intent);
    }

    private void otvoriActivityPcelinjaci() {
        Intent intent = new Intent(this, PcelinjaciActivity.class);
        startActivity(intent);
    }

    private void otvoriActivityOpsteNapomene() {
        Intent intent = new Intent(this, OpsteNapomeneActivity.class);
        startActivity(intent);
    }

    private void otvoriActivityBilansProizvoda() {
        Intent intent = new Intent(this, BilansProizvodaActivity.class);
        startActivity(intent);
    }

    private void otvoriOsnovnePodatke() {
        Intent intent = new Intent(this, OsnovniPodaciActivity.class);
        startActivity(intent);
    }

}

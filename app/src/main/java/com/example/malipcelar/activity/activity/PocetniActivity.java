package com.example.malipcelar.activity.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.viewModel.PcelinjakViewModel;

import java.util.List;

public class PocetniActivity extends AppCompatActivity {

    Button btnNapomene;
    Button btnPcelinjaci;
    Button btnIstorijaAktivnosti;
    Button btnBilansProivoda;
    Button btnOsnovniPodaci;

    private long prosloVreme;
    private Toast tost;

    List<Pcelinjak> pcelinjaci;

    PcelinjakViewModel pcelinjakViewModel;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pocetni_activity);

        proveriDaLiJePrviPut();

        srediAtribute();
        srediListenere();
        srediViewModel();

    }

    private void srediViewModel() {
        pcelinjakViewModel = ViewModelProviders.of(this).get(PcelinjakViewModel.class);
        srediObservere();
    }

    private void srediObservere() {

        pcelinjakViewModel.getAllPcelinjaci().observe(this, new Observer<List<Pcelinjak>>() {
            @Override
            public void onChanged(List<Pcelinjak> pcelinjaks) {
                pcelinjaci = pcelinjaks;
                if (pcelinjaci.size() == 0) {
                    iskljuciBtnIstorijaAktivnosti();
                    iskljuciBilansProizvoda();
                } else {
                    ukljuciBtnIstorijaAktivnosti();
                    ukljuciBilansProizvoda();
                }
            }
        });

    }

    private void ukljuciBtnIstorijaAktivnosti() {
        btnIstorijaAktivnosti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otvoriActivityIstorijaAktivnosti();
            }
        });

    }

    private void ukljuciBilansProizvoda() {
        btnBilansProivoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otvoriActivityBilansProizvoda();
            }
        });
    }

    private void iskljuciBilansProizvoda() {
        btnBilansProivoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               poruka();
            }
        });
    }

    private void iskljuciBtnIstorijaAktivnosti() {
        btnIstorijaAktivnosti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poruka();
            }
        });
    }

    private void poruka() {
        String buffer = "Morate imati makar jedan unet pčelinjak da bi mogli da koristite ovu opciju.";
        prikaziPoruku("Obavezno!", buffer);

    }

    public void prikaziPoruku(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
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

    private void srediListenere() {
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

    @Override
    public void onBackPressed() {
        if (prosloVreme + 2000 > System.currentTimeMillis()) {
            tost.cancel();
            super.onBackPressed();
            return;
        } else {
            tost = Toast.makeText(getBaseContext(), "Pritisnite jos jednom nazad za izlaz", Toast.LENGTH_SHORT);
            tost.show();
        }
        prosloVreme = System.currentTimeMillis();
    }

}

package com.example.malipcelar.activity.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malipcelar.R;

import java.util.List;

public class Dodaj_IzmeniKosnicuActivity extends AppCompatActivity {


    private static final String TAG = "Dodaj_IzmeniKosnicuActivity";

    public static final String EXTRA_RB_KOSNICE =
            "com.example.malipcelar.activity.activity.EXTRA_RB_KOSNICE";
    public static final String EXTRA_GODINA_PROIZVODNJE_MATICE =
            "com.example.malipcelar.activity.activity.EXTRA_GODINA_PROIZVODNJE_MATICE";
    public static final String EXTRA_RB_SELEKCIONISANA =
            "com.example.malipcelar.activity.activity.EXTRA_RB_SELEKCIONISANA";
    public static final String EXTRA_RB_PRIRODNA =
            "com.example.malipcelar.activity.activity.EXTRA_RB_PRIRODNA";
    public static final String EXTRA_BOLESTI =
            "com.example.malipcelar.activity.activity.EXTRA_BOLESTI";
    public static final String EXTRA_NAPOMENA =
            "com.example.malipcelar.activity.activity.EXTRA_NAPOMENA";
    public static final String EXTRA_ZAUZETI_RB =
            "com.example.malipcelar.activity.activity.EXTRA_ZAUZETI_RB";

    TextView txtRedniBroj;
    Spinner spGodinaProizvodnje;
    RadioButton rbSelekcionisana;
    RadioButton rbPrirodna;
    TextView txtBolesti;
    TextView txtNapomena;
    List<Integer> zauzetiRbOvi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodaj_izmeni_kosnicu_activity);

        srediAtribute();
    }

    private void srediAtribute() {
        txtRedniBroj = findViewById(R.id.txtRBKosnice);
        spGodinaProizvodnje = (Spinner) findViewById(R.id.spGodinaProizvodnje);
        rbSelekcionisana = findViewById(R.id.rbSelekcionisana);
        rbPrirodna = findViewById(R.id.rbPrirodna);
        txtBolesti = findViewById(R.id.txtBolesti);
        txtNapomena = findViewById(R.id.txtNapomena);

        zauzetiRbOvi = getIntent().getIntegerArrayListExtra(EXTRA_ZAUZETI_RB);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dodaj_novu_napomenu_meni, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sacuvaj_napomenu:
                sacuvajKosnicu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sacuvajKosnicu() {
        String rb = txtRedniBroj.getText().toString().trim();
        int redniBroj = -1;
        try {
            redniBroj = Integer.parseInt(rb);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Molimo unesite brojnu vrednost", Toast.LENGTH_LONG).show();
            return;
        }
        /*
        if (redniBroj == (getIntent().getIntExtra(EXTRA_RB, -1))) {
            //izmena

            String naziv = txtNaziv.getText().toString().trim();

            Double latituda = null;
            Double longituda = null;

            if (mMarker != null) {
                latituda = mMarker.getPosition().latitude;
                longituda = mMarker.getPosition().longitude;
            }

            String lokacija = latituda + "," + longituda;
            int visina = (int) nVisina;
            String nadmorskaVisina = visina + "";//-1000

            Bitmap image = ((BitmapDrawable) pcelinjakSlika.getDrawable()).getBitmap();
            String slika;
            if (image.getWidth() == 250 && image.getHeight() == 250) {
                slika = bitmapToString(image);
            } else {
                slika = bitmapToString(resizeBitmap(image));
            }

            if (naziv.isEmpty()) {
                Toast.makeText(this, "Unesite naziv pcelinjaka", Toast.LENGTH_SHORT).show();
                return;
            }

            if (visina == -1000) {
                Toast.makeText(this, "Niste postavili lokaciju", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent podaci = new Intent();
            podaci.putExtra(EXTRA_RB, redniBroj);
            podaci.putExtra(EXTRA_NAZIV_PCELINJAKA, naziv);
            podaci.putExtra(EXTRA_LOKACIJA, lokacija);
            podaci.putExtra(EXTRA_NADMORSKA_VISINA, nadmorskaVisina);
            podaci.putExtra(EXTRA_SLIKA, slika);

            setResult(RESULT_OK, podaci);
            finish();

            return;
        }*/
        //novi
        if (zauzetiRbOvi.contains(redniBroj)) {
            Toast.makeText(getApplicationContext(), "Pokusavate da unosite postojeci redni broj", Toast.LENGTH_LONG).show();
            return;
        }

        String godina = (String) spGodinaProizvodnje.getSelectedItem();
        Boolean selekcionisana = rbSelekcionisana.isSelected();
        Boolean prirodna = rbPrirodna.isSelected();

        String bolesti = txtBolesti.getText().toString().trim();
        String napomena = txtNapomena.getText().toString().trim();

        Intent podaci = new Intent();
        podaci.putExtra(EXTRA_RB_KOSNICE, redniBroj);
        podaci.putExtra(EXTRA_GODINA_PROIZVODNJE_MATICE, godina);
        podaci.putExtra(EXTRA_RB_SELEKCIONISANA, selekcionisana);
        podaci.putExtra(EXTRA_RB_PRIRODNA, prirodna);
        podaci.putExtra(EXTRA_BOLESTI, bolesti);
        podaci.putExtra(EXTRA_NAPOMENA, napomena);
        /*
        int id = getIntent().getIntExtra(EXTRA_RB, -1);
        if (id != -1) {
            podaci.putExtra(EXTRA_RB, id);
        }*/
        setResult(RESULT_OK, podaci);
        finish();
    }

}
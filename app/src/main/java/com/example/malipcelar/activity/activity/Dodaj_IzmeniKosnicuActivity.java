package com.example.malipcelar.activity.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.malipcelar.R;

public class Dodaj_IzmeniKosnicuActivity extends AppCompatActivity {

    private static final String TAG = "Dodaj_IzmeniKosnicuActivity";


    TextView txtRedniBroj;
    Spinner spGodinaProizvodnje;
    RadioButton rbSelekcionisana;
    RadioButton rbPrirodna;
    TextView bolesti;
    TextView napomena;

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
        bolesti = findViewById(R.id.txtBolesti);
        napomena = findViewById(R.id.txtNapomena);
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
    }

}
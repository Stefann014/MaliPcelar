package com.example.malipcelar.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.malipcelar.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodaj_izmeni_kosnicu_activity);

        srediAtribute();
        srediIntent();
    }

    private void srediIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_RB_KOSNICE)) {
            setTitle("Izmeni kosnicu");
            txtRedniBroj.setText(intent.getIntExtra(EXTRA_RB_KOSNICE, -1) + "");
            txtBolesti.setText(intent.getStringExtra(EXTRA_BOLESTI));
            txtNapomena.setText(intent.getStringExtra(EXTRA_NAPOMENA));

            Boolean prirodna = intent.getBooleanExtra(EXTRA_RB_PRIRODNA, false);
            String godina = intent.getStringExtra(EXTRA_GODINA_PROIZVODNJE_MATICE);

            if (prirodna) {
                radioGroup.check(R.id.rbPrirodna);
            } else {
                radioGroup.check(R.id.rbSelekcionisana);
            }
            spGodinaProizvodnje.setSelection(((ArrayAdapter<String>) spGodinaProizvodnje.getAdapter()).getPosition(godina));

        } else {
            setTitle("Dodaj pcelinjak");
        }
    }

    private void srediAtribute() {
        txtRedniBroj = findViewById(R.id.txtRBKosnice);
        spGodinaProizvodnje = (Spinner) findViewById(R.id.spGodinaProizvodnje);
        rbSelekcionisana = findViewById(R.id.rbSelekcionisana);
        rbPrirodna = findViewById(R.id.rbPrirodna);
        txtBolesti = findViewById(R.id.txtBolesti);
        txtNapomena = findViewById(R.id.txtNapomena);
        radioGroup = findViewById(R.id.radioGroup);
        srediSpinner();

        zauzetiRbOvi = getIntent().getIntegerArrayListExtra(EXTRA_ZAUZETI_RB);
    }

    private void srediSpinner() {
        Long mili = System.currentTimeMillis();

        Date date = new Date(mili);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        //Add one to month {0 - 11}

        ArrayList<String> godine = new ArrayList<>();
        godine.add(year - 5 + "");
        godine.add(year - 4 + "");
        godine.add(year - 3 + "");
        godine.add(year - 2 + "");
        godine.add(year - 1 + "");

        godine.add(year + "");

        godine.add(year + 1 + "");
        godine.add(year + 2 + "");
        godine.add(year + 3 + "");
        godine.add(year + 4 + "");
        godine.add(year + 5 + "");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, godine); // ako je greska tu je
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGodinaProizvodnje.setAdapter(adapter);
        spGodinaProizvodnje.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


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

        if (redniBroj == (getIntent().getIntExtra(EXTRA_RB_KOSNICE, -1))) {
            //izmena

            String godina = (String) spGodinaProizvodnje.getSelectedItem();

            if (radioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getApplicationContext(), "Morate selektovati vrstu matice", Toast.LENGTH_LONG).show();
                return;
            }

            int selectedId = radioGroup.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            RadioButton selectedRadioButton = (RadioButton) findViewById(selectedId);
            Toast.makeText(getApplicationContext(), selectedRadioButton.getText().toString() + " is selected", Toast.LENGTH_SHORT).show();

            Boolean prirodna = false;
            Boolean selekcionisana = false;

            if (selectedRadioButton.getText().toString().equals("Prirodna")) {
                prirodna = true;
            }
            if (selectedRadioButton.getText().toString().equals("Selekcionisana")) {
                selekcionisana = true;
            }
            String bolesti = txtBolesti.getText().toString().trim();
            String napomena = txtNapomena.getText().toString().trim();

            Intent podaci = new Intent();
            podaci.putExtra(EXTRA_RB_KOSNICE, redniBroj);
            podaci.putExtra(EXTRA_GODINA_PROIZVODNJE_MATICE, godina);
            podaci.putExtra(EXTRA_RB_SELEKCIONISANA, selekcionisana);
            podaci.putExtra(EXTRA_RB_PRIRODNA, prirodna);
            podaci.putExtra(EXTRA_BOLESTI, bolesti);
            podaci.putExtra(EXTRA_NAPOMENA, napomena);

            setResult(RESULT_OK, podaci);
            finish();

            return;
        }
        //novi
        if (zauzetiRbOvi.contains(redniBroj)) {
            Toast.makeText(getApplicationContext(), "Pokusavate da unosite postojeci redni broj", Toast.LENGTH_LONG).show();
            return;
        }

        String godina = (String) spGodinaProizvodnje.getSelectedItem();

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Morate selektovati vrstu matice", Toast.LENGTH_LONG).show();
            return;
        }

        int selectedId = radioGroup.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        RadioButton selectedRadioButton = (RadioButton) findViewById(selectedId);
        Toast.makeText(getApplicationContext(), selectedRadioButton.getText().toString() + " is selected", Toast.LENGTH_SHORT).show();

        Boolean prirodna = false;
        Boolean selekcionisana = false;

        if (selectedRadioButton.getText().toString().equals("Prirodna")) {
            prirodna = true;
        }
        if (selectedRadioButton.getText().toString().equals("Selekcionisana")) {
            selekcionisana = true;
        }

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
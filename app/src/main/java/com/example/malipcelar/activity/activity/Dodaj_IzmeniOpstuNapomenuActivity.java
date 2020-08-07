package com.example.malipcelar.activity.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.adapteri.TipNapomeneAdapter;
import com.example.malipcelar.activity.domen.OpstaNapomena;
import com.example.malipcelar.activity.fragmenti.DatumPickerFragment;
import com.example.malipcelar.activity.pomocneKlase.TipNapomeneZaSpinner;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Dodaj_IzmeniOpstuNapomenuActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String EXTRA_ID =
            "com.example.malipcelar.activity.activity.EXTRA_ID";
    public static final String EXTRA_TIP_NAPOMENE =
            "com.example.malipcelar.activity.activity.EXTRA_TIP_NAPOMENE";
    public static final String EXTRA_NAPOMENA =
            "com.example.malipcelar.activity.activity.EXTRA_NAPOMENA";
    public static final String EXTRA_DATUM =
            "com.example.malipcelar.activity.activity.EXTRA_DATUM";

    Button btnDatum;
    Button btnSacuvaj;
    OpstaNapomena opsteNapomene;
    TextView txtNapomena;
    Spinner spinnerVrstaNapomene;
    ArrayList<TipNapomeneZaSpinner> tipNapomeneZaSpinner;
    TipNapomeneAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodaj_izmeni_opstu_napomenu_activity);
        srediAtribute();
        srediListenere();
        srediIntent();
    }

    private void srediIntent() {
        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Izmeni Napomenu");
            for (int i = 0; i < spinnerVrstaNapomene.getCount(); i++) {
                if (spinnerVrstaNapomene.getItemAtPosition(i).toString().equals(intent.getStringExtra(EXTRA_TIP_NAPOMENE))) {
                    spinnerVrstaNapomene.setSelection(i);
                    break;
                }
            }
            txtNapomena.setText(intent.getStringExtra(EXTRA_NAPOMENA));
            String datum = intent.getStringExtra(EXTRA_DATUM);
            // treba nam sad u formatu 20.05.1997
            String[] datumi = datum.split("-");
            String dobarDatum = datumi[2] + "." + datumi[1] + "." + datumi[0] + ".";
            btnDatum.setText(dobarDatum);
        } else {
            setTitle("Dodaj napomenu");
        }
    }

    @NonNull
    private String prevediDatumUFormatZaBazu(String datum) {
        //treba nam format yyyy-MM-dd
        datum = datum.substring(0, datum.length() - 1);
        datum = datum.replace('.', '-');
        String[] datumi = datum.split("-");
        return datumi[2] + "-" + datumi[1] + "-" + datumi[0];
    }

    private void sacuvajNapomenu() {
        String tipNapomene = spinnerVrstaNapomene.getSelectedItem().toString().trim();
        String napomena = txtNapomena.getText().toString().trim();
        String datum = prevediDatumUFormatZaBazu(btnDatum.getText().toString().trim());

        if (napomena.isEmpty()) {
            Toast.makeText(this, "Unesite napomenu", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent podaci = new Intent();
        podaci.putExtra(EXTRA_TIP_NAPOMENE, tipNapomene);
        podaci.putExtra(EXTRA_NAPOMENA, napomena);
        podaci.putExtra(EXTRA_DATUM, datum);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            podaci.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, podaci);
        finish();
    }

    private void srediAtribute() {
        btnSacuvaj = findViewById(R.id.btnSacuvaj);
        opsteNapomene = new OpstaNapomena();
        txtNapomena = findViewById(R.id.txtNapomena);
        srediDatum();
        srediSpiner();
    }

    private void srediListenere() {
        btnDatum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatumPickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        spinnerVrstaNapomene.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnSacuvaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sacuvajNapomenu();
            }
        });
    }

    private void srediSpiner() {
        spinnerVrstaNapomene = findViewById(R.id.spinnerNapomene);
        initList();
        adapter = new TipNapomeneAdapter(this, tipNapomeneZaSpinner);
        spinnerVrstaNapomene.setAdapter(adapter);
    }

    private void srediDatum() {
        btnDatum = (Button) findViewById(R.id.btnPcelinjaci);
        Date c = Calendar.getInstance().getTime();
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        btnDatum.setText(currentDateString);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        btnDatum.setText(currentDateString);
    }

    private void initList() {
        tipNapomeneZaSpinner = new ArrayList<>();
        tipNapomeneZaSpinner.add(new TipNapomeneZaSpinner("Opšte", R.drawable.tip_napomene_spinner_opste));
        tipNapomeneZaSpinner.add(new TipNapomeneZaSpinner("Pčelinjak", R.drawable.tip_napomene_spinner_pcelinjak));
        tipNapomeneZaSpinner.add(new TipNapomeneZaSpinner("Košnica", R.drawable.tip_napomene_spinner_kosnica));
        tipNapomeneZaSpinner.add(new TipNapomeneZaSpinner("Hitno", R.drawable.tip_napomene_spinner_veomavazno));
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
                sacuvajNapomenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
package com.example.malipcelar.activity.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.fragmenti.DatumPickerFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Dodaj_IzmeniLecenjeActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String EXTRA_ID =
            "com.example.malipcelar.activity.activity.EXTRA_ID";
    public static final String EXTRA_DATUM_LECENJA =
            "com.example.malipcelar.activity.activity.EXTRA_DATUM_LECENJA";
    public static final String EXTRA_BOLEST =
            "com.example.malipcelar.activity.activity.EXTRA_BOLEST";
    public static final String EXTRA_PRIMENI_LECENJE_NA_CEO_PCELINJAK =
            "com.example.malipcelar.activity.activity.EXTRA_PRIMENI_LECENJE_NA_CEO_PCELINJAK";

    Button btnDatumLecenja;
    Button btnSacuvaj;
    Spinner spBolesti;
    CheckBox chPrimeniLecenjeNaPcelinjak;
    TextView tvLecenje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodaj_izmeni_lecenje_activity);

        srediAtribute();
        srediListenere();
        srediIntent();
    }

    private void srediAtribute() {
        btnDatumLecenja = findViewById(R.id.btnDatumLecenja);
        btnSacuvaj = findViewById(R.id.btnSacuvajLecenje);
        spBolesti = findViewById(R.id.spBolesti);
        chPrimeniLecenjeNaPcelinjak = findViewById(R.id.chPrimeniLecenjeNaPcelinjak);
        tvLecenje = findViewById(R.id.tvLecenje);
        srediSpinner();
        srediDatum();
    }

    private void srediIntent() {
        Intent data = getIntent();
        if (data.hasExtra(EXTRA_ID)) {
            setTitle("Izmeni lečenje");
            String datum = data.getStringExtra(EXTRA_DATUM_LECENJA);

            assert datum != null;
            String[] datumi = datum.split("-");
            String dobarDatum = datumi[2] + "." + datumi[1] + "." + datumi[0] + ".";
            btnDatumLecenja.setText(dobarDatum);
            String bolest = data.getStringExtra(EXTRA_BOLEST);
            spBolesti.setSelection(((ArrayAdapter<String>) spBolesti.getAdapter()).getPosition(bolest));
            chPrimeniLecenjeNaPcelinjak.setVisibility(View.INVISIBLE);
            tvLecenje.setVisibility(View.INVISIBLE);
        } else {
            setTitle("Dodaj novo lečenje");
        }
    }

    private void srediSpinner() {

        ArrayList<String> bolesti = new ArrayList<>();
        bolesti.add("Nozemoza");
        bolesti.add("Varoa");
        bolesti.add("Americka kuga");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, bolesti); // ako je greska tu je
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBolesti.setAdapter(adapter);
        spBolesti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void srediDatum() {
        Date c = Calendar.getInstance().getTime();
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        btnDatumLecenja.setText(currentDateString);
    }

    private void srediListenere() {
        btnDatumLecenja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatumPickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        btnSacuvaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sacuvajLecenje();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        btnDatumLecenja.setText(currentDateString);
    }

    private void sacuvajLecenje() {
        String datum = prevediDatumUFormatZaBazu(btnDatumLecenja.getText().toString().trim());
        String bolest = (String) spBolesti.getSelectedItem();

        boolean primeni = false;
        if (chPrimeniLecenjeNaPcelinjak.isChecked()) {
            primeni = true;
        }
        Intent podaci = new Intent();
        podaci.putExtra(EXTRA_DATUM_LECENJA, datum);
        podaci.putExtra(EXTRA_BOLEST, bolest);
        podaci.putExtra(EXTRA_PRIMENI_LECENJE_NA_CEO_PCELINJAK, primeni);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            podaci.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, podaci);
        finish();
    }

    @NonNull
    private String prevediDatumUFormatZaBazu(String datum) {
        datum = datum.substring(0, datum.length() - 1);
        datum = datum.replace('.', '-');
        String[] datumi = datum.split("-");
        return datumi[2] + "-" + datumi[1] + "-" + datumi[0];
    }

}
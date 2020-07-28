package com.example.malipcelar.activity.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;

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

    Button btnDatumLecenja;
    Spinner spBolesti;
    CheckBox chPrimeniLecenjeNaPcelinjak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodaj_izmeni_lecenje_activity);

        srediAtribute();
    }

    private void srediAtribute() {
        btnDatumLecenja = findViewById(R.id.btnDatumLecenja);
        spBolesti = findViewById(R.id.spBolesti);
        chPrimeniLecenjeNaPcelinjak = findViewById(R.id.chPrimeniLecenjeNaPcelinjak);
        srediSpinner();
        srediDatum();
        srediListenere();
    }

    private void srediSpinner() {

        ArrayList<String> bolesti = new ArrayList<>();
        bolesti.add("Nozemoza");
        bolesti.add("Varoa");
        bolesti.add("Americka kuga");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
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
                sacuvajLecenje();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sacuvajLecenje() {
    }

    @NonNull
    private String prevediDatumUFormatZaBazu(String datum) {
        //treba nam format yyyy-MM-dd
        datum = datum.substring(0, datum.length() - 1);
        datum = datum.replace('.', '-');
        String[] datumi = datum.split("-");
        String dobarDatum = datumi[2] + "-" + datumi[1] + "-" + datumi[0];
        return dobarDatum;
    }

}
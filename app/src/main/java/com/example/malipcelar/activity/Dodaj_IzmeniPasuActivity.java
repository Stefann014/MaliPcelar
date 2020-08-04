package com.example.malipcelar.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.fragmenti.DatumPickerFragment;

import java.util.Calendar;

public class Dodaj_IzmeniPasuActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button btnDatumOd;
    Button btnDatumDo;
    Spinner spPcelinjaci;
    TextView txtPrikupljenoMeda;
    TextView txtPrikupljenoPolena;
    TextView txtPrikupljenoPropolisa;
    TextView txtPrikupljenoMaticnogMleca;
    TextView txtPrikupljenoPrikupljenePerge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodaj_izmeni_pasa_activity);

        srediAtribute();
        srediListenere();
    }

    private void srediListenere() {
        btnDatumOd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatumPickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        btnDatumDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatumPickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    private void srediAtribute() {
        btnDatumOd = findViewById(R.id.btnDatumOdPasa);
        btnDatumDo = findViewById(R.id.btnDatumDoPasa);
        spPcelinjaci = findViewById(R.id.spPcelinjaci);
        txtPrikupljenoMeda = findViewById(R.id.txtPrikupljenoMeda);
        txtPrikupljenoPolena = findViewById(R.id.txtPrikupljenoPolena);
        txtPrikupljenoPropolisa = findViewById(R.id.txtPrikupljenoPropolisa);
        txtPrikupljenoMaticnogMleca = findViewById(R.id.txtPrikupljenoMaticnogMleca);
        txtPrikupljenoPrikupljenePerge = findViewById(R.id.txtPrikupljenoPerge);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        //String currentDateString = DateFormat.getDateInstance().format(c.getTime());
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
                sacuvajPasu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sacuvajPasu() {
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
package com.example.malipcelar.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.fragmenti.DatumPickerFragment;
import com.example.malipcelar.activity.viewModel.PcelinjakViewModel;

import java.util.Calendar;
import java.util.List;

public class Dodaj_IzmeniPasuActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button btnDatumOd;
    Button btnDatumDo;
    Spinner spPcelinjaci;
    TextView txtPrikupljenoMeda;
    TextView txtPrikupljenoPolena;
    TextView txtPrikupljenoPropolisa;
    TextView txtPrikupljenoMaticnogMleca;
    TextView txtPrikupljenoPrikupljenePerge;

    PcelinjakViewModel pcelinjakViewModel;
    List<Pcelinjak> pcelinjaci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodaj_izmeni_pasa_activity);

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
            public void onChanged(List<Pcelinjak> pcelinjaci) {
                Dodaj_IzmeniPasuActivity.this.pcelinjaci = pcelinjaci;
                srediSpinner(pcelinjaci);
            }
        });
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
        pcelinjaci = null;
    }

    private void srediSpinner(List<Pcelinjak> pcelinjaci) {

        ArrayAdapter<Pcelinjak> adapter = new ArrayAdapter<Pcelinjak>(this,
                android.R.layout.simple_spinner_item, pcelinjaci); // ako je greska tu je
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPcelinjaci.setAdapter(adapter);
        spPcelinjaci.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
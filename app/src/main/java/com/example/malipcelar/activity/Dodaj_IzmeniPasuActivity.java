package com.example.malipcelar.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.fragmenti.DatumPickerFragment;
import com.example.malipcelar.activity.viewModel.PcelinjakViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Dodaj_IzmeniPasuActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String EXTRA_ID =
            "com.example.malipcelar.activity.activity.EXTRA_ID";
    public static final String EXTRA_DATUM_OD_PASE =
            "com.example.malipcelar.activity.activity.EXTRA_DATUM_OD_PASE";
    public static final String EXTRA_DATUM_DO_PASE =
            "com.example.malipcelar.activity.activity.EXTRA_DATUM_DO_PASE";
    public static final String EXTRA_PCELINJAK_ID =
            "com.example.malipcelar.activity.activity.EXTRA_PCELINJAK_ID";
    public static final String EXTRA_PRIKUPLJENO_MEDA =
            "com.example.malipcelar.activity.activity.EXTRA_PRIKUPLJENO_MEDA";
    public static final String EXTRA_PRIKUPLJENO_POLENA =
            "com.example.malipcelar.activity.activity.EXTRA_PRIKUPLJENO_POLENA";
    public static final String EXTRA_PRIKUPLJENO_PROPOLISA =
            "com.example.malipcelar.activity.activity.EXTRA_PRIKUPLJENO_PROPOLISA";
    public static final String EXTRA_PRIKUPLJENO_MATICNOG_MLECA =
            "com.example.malipcelar.activity.activity.EXTRA_PRIKUPLJENO_MATICNOG_MLECA";
    public static final String EXTRA_PRIKUPLJENO_PERGE =
            "com.example.malipcelar.activity.activity.EXTRA_PRIKUPLJENO_PERGE";
    public static final String EXTRA_NAPOMENA_PASA = "com.example.malipcelar.activity.activity.EXTRA_NAPOPMENA_PASA";

    Button btnDatumOd;
    Button btnDatumDo;
    Spinner spPcelinjaci;
    TextView txtPrikupljenoMeda;
    TextView txtPrikupljenoPolena;
    TextView txtPrikupljenoPropolisa;
    TextView txtPrikupljenoMaticnogMleca;
    TextView txtPrikupljenoPrikupljenePerge;
    TextView txtNapomena;

    PcelinjakViewModel pcelinjakViewModel;
    List<Pcelinjak> pcelinjaci;

    int brKliknutog = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodaj_izmeni_pasa_activity);

        srediAtribute();
        srediListenere();
        srediViewModel();
        srediIntent();
    }

    private void srediIntent() {
        Intent data = getIntent();
        if (data.hasExtra(EXTRA_ID)) {
            setTitle("Izmeni pasu");
            String datum = data.getStringExtra(EXTRA_DATUM_OD_PASE);
            // treba nam sad u formatu 20.05.1997
            String[] datumi = datum.split("-");
            String dobarDatum = datumi[2] + "." + datumi[1] + "." + datumi[0] + ".";
            btnDatumOd.setText(dobarDatum);
            String datum2 = data.getStringExtra(EXTRA_DATUM_OD_PASE);
            // treba nam sad u formatu 20.05.1997
            String[] datumi2 = datum.split("-");
            String dobarDatum2 = datumi[2] + "." + datumi[1] + "." + datumi[0] + ".";
            btnDatumDo.setText(dobarDatum2);

            String prikupljenoMeda = data.getStringExtra(EXTRA_PRIKUPLJENO_MEDA);
            txtPrikupljenoMeda.setText(prikupljenoMeda);
            String prikupljenoPolena = data.getStringExtra(EXTRA_PRIKUPLJENO_MEDA);
            txtPrikupljenoMeda.setText(prikupljenoPolena);
            String prikupljenoPropolisa = data.getStringExtra(EXTRA_PRIKUPLJENO_MEDA);
            txtPrikupljenoMeda.setText(prikupljenoPropolisa);
            String prikupljenoMaticnogMleca = data.getStringExtra(EXTRA_PRIKUPLJENO_MEDA);
            txtPrikupljenoMeda.setText(prikupljenoMaticnogMleca);
            String prikupljenoPerge = data.getStringExtra(EXTRA_PRIKUPLJENO_MEDA);
            txtPrikupljenoMeda.setText(prikupljenoPerge);
            Pcelinjak pcelinjak = (Pcelinjak) data.getSerializableExtra(EXTRA_PCELINJAK_ID);
            spPcelinjaci.setSelection(((ArrayAdapter<Pcelinjak>) spPcelinjaci.getAdapter()).getPosition(pcelinjak));
            String napomena = data.getStringExtra(EXTRA_NAPOMENA_PASA);
            txtNapomena.setText(napomena);

        } else {
            setTitle("Dodaj pasu");
        }
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
                brKliknutog = 1;
                DialogFragment datePicker = new DatumPickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        btnDatumDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brKliknutog = 2;
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
        txtNapomena = findViewById(R.id.txtNapomenaPasa);
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
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        if (brKliknutog == 1) {
            btnDatumOd.setText(currentDateString);
        }
        if (brKliknutog == 2) {
            btnDatumDo.setText(currentDateString);
        }
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

        if (btnDatumOd.getText().toString().trim().equals("Datum od:") || btnDatumDo.getText().toString().trim().equals("Datum do:")) {
            Toast.makeText(this, "Unesite datume", Toast.LENGTH_LONG).show();
            return;
        }

        String datumOd = prevediDatumUFormatZaBazu(btnDatumOd.getText().toString().trim());
        String datumDo = prevediDatumUFormatZaBazu(btnDatumDo.getText().toString().trim());

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date datum1 = sdf.parse(btnDatumOd.getText().toString().trim());
            Date datum2 = sdf.parse(btnDatumDo.getText().toString().trim());

            if (datum1.getTime() > datum2.getTime()) {
                Toast.makeText(this, "Datum OD ne moze biti veci od datuma DO", Toast.LENGTH_LONG).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Pcelinjak pcelinjak = (Pcelinjak) spPcelinjaci.getSelectedItem();

        double prikupljenoMedaD;
        double prikupljenoPolenaD;
        double prikupljenoPropolisaD;
        double prikupljenoMaticnogMlecaD;
        double prikupljenoPergeD;


        String prikupljenoMeda = txtPrikupljenoMeda.getText().toString().trim();
        String prikupljenoPolena = txtPrikupljenoPolena.getText().toString().trim();
        String prikupljenoPropolisa = txtPrikupljenoPropolisa.getText().toString().trim();
        String prikupljenoMaticnogMleca = txtPrikupljenoMaticnogMleca.getText().toString().trim();
        String prikupljenoPerge = txtPrikupljenoPrikupljenePerge.getText().toString().trim();

        if (prikupljenoMeda.isEmpty()) {
            prikupljenoMedaD = 0;
        } else {
            prikupljenoMedaD = Double.parseDouble(prikupljenoMeda);
        }
        if (prikupljenoPolena.isEmpty()) {
            prikupljenoPolenaD = 0;
        } else {
            prikupljenoPolenaD = Double.parseDouble(prikupljenoPolena);
        }
        if (prikupljenoPropolisa.isEmpty()) {
            prikupljenoPropolisaD = 0;
        } else {
            prikupljenoPropolisaD = Double.parseDouble(prikupljenoPropolisa);
        }
        if (prikupljenoMaticnogMleca.isEmpty()) {
            prikupljenoMaticnogMlecaD = 0;
        } else {
            prikupljenoMaticnogMlecaD = Double.parseDouble(prikupljenoMaticnogMleca);
        }
        if (prikupljenoPerge.isEmpty()) {
            prikupljenoPergeD = 0;
        } else {
            prikupljenoPergeD = Double.parseDouble(prikupljenoPerge);
        }

        String napomena = txtNapomena.getText().toString().trim();

        Intent podaci = new Intent();
        podaci.putExtra(EXTRA_DATUM_OD_PASE, datumOd);
        podaci.putExtra(EXTRA_DATUM_DO_PASE, datumDo);
        podaci.putExtra(EXTRA_PCELINJAK_ID, pcelinjak);
        podaci.putExtra(EXTRA_PRIKUPLJENO_MEDA, prikupljenoMedaD);
        podaci.putExtra(EXTRA_PRIKUPLJENO_POLENA, prikupljenoPolenaD);
        podaci.putExtra(EXTRA_PRIKUPLJENO_PROPOLISA, prikupljenoPropolisaD);
        podaci.putExtra(EXTRA_PRIKUPLJENO_MATICNOG_MLECA, prikupljenoMaticnogMlecaD);
        podaci.putExtra(EXTRA_PRIKUPLJENO_PERGE, prikupljenoPergeD);
        podaci.putExtra(EXTRA_NAPOMENA_PASA, napomena);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            podaci.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, podaci);
        finish();
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
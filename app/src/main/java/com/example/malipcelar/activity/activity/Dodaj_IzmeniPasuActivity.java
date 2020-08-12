package com.example.malipcelar.activity.activity;

import android.annotation.SuppressLint;
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
import androidx.lifecycle.ViewModelProvider;

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
    Button btnSacuvaj;
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

    @SuppressLint("SetTextI18n")
    private void srediIntent() {
        Intent data = getIntent();
        if (data.hasExtra(EXTRA_ID)) {
            setTitle("Izmeni pašu");

            String datum = data.getStringExtra(EXTRA_DATUM_OD_PASE);
            if (datum != null) {
                btnDatumOd.setText(pretvoriUDatumZaPrikazivanje(datum));
            }

            String datum2 = data.getStringExtra(EXTRA_DATUM_DO_PASE);
            if (datum2 != null) {
                btnDatumDo.setText(pretvoriUDatumZaPrikazivanje(datum2));
            }

            double prikupljenoMeda = data.getDoubleExtra(EXTRA_PRIKUPLJENO_MEDA, 0);
            txtPrikupljenoMeda.setText(prikupljenoMeda + "");
            double prikupljenoPolena = data.getDoubleExtra(EXTRA_PRIKUPLJENO_POLENA, 0);
            txtPrikupljenoPolena.setText(prikupljenoPolena + "");
            double prikupljenoPropolisa = data.getDoubleExtra(EXTRA_PRIKUPLJENO_PROPOLISA, 0);
            txtPrikupljenoPropolisa.setText(prikupljenoPropolisa + "");
            double prikupljenoMaticnogMleca = data.getDoubleExtra(EXTRA_PRIKUPLJENO_MATICNOG_MLECA, 0);
            txtPrikupljenoMaticnogMleca.setText(prikupljenoMaticnogMleca + "");
            double prikupljenoPerge = data.getDoubleExtra(EXTRA_PRIKUPLJENO_PERGE, 0);
            txtPrikupljenoPrikupljenePerge.setText(prikupljenoPerge + "");
            int pcelinjakID = data.getIntExtra(EXTRA_PCELINJAK_ID, -1);
            Pcelinjak pcelinjak = pronadjiPcelinjak(pcelinjakID);
            pcelinjak.setRedniBrojPcelinjaka(pcelinjakID);
            String napomena = data.getStringExtra(EXTRA_NAPOMENA_PASA);
            txtNapomena.setText(napomena);

        } else {
            setTitle("Dodaj pašu");
        }
    }

    private String pretvoriUDatumZaPrikazivanje(String datum) {
        String[] datumi = datum.split("-");
        return datumi[2] + "." + datumi[1] + "." + datumi[0] + ".";
    }

    private Pcelinjak pronadjiPcelinjak(int pcelinjakID) {
        final Pcelinjak[] p = {new Pcelinjak()};
        pcelinjakViewModel.getPcelinjakByID(pcelinjakID).observe(this, new Observer<Pcelinjak>() {
            @Override
            public void onChanged(Pcelinjak pcelinjak) {
                p[0] = pcelinjak;
            }
        });
        return p[0];
    }

    private void srediViewModel() {
        pcelinjakViewModel = new ViewModelProvider(this).get(PcelinjakViewModel.class);
        srediObservere();
    }

    private void srediObservere() {
        pcelinjakViewModel.getAllPcelinjaci().observe(this, new Observer<List<Pcelinjak>>() {
            @Override
            public void onChanged(List<Pcelinjak> pcelinjaci) {
                Dodaj_IzmeniPasuActivity.this.pcelinjaci = pcelinjaci;
                if (pcelinjaci.size() == 0) {
                    btnSacuvaj.setEnabled(false);
                } else {
                    btnSacuvaj.setEnabled(true);
                }
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
        btnSacuvaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sacuvajPasu();
            }
        });
    }

    private void srediAtribute() {
        btnDatumOd = findViewById(R.id.btnDatumOdPasa);
        btnDatumDo = findViewById(R.id.btnDatumDoPasa);
        btnSacuvaj = findViewById(R.id.btnSacuvajPasu);
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

        ArrayAdapter<Pcelinjak> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, pcelinjaci);
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

    private void sacuvajPasu() {

        if (spPcelinjaci.getSelectedItem().toString().equals("") || spPcelinjaci.getSelectedItem() == null) {
            Toast.makeText(this, "Molimo Vas dodajte prvo pčelinjak", Toast.LENGTH_LONG).show();
            return;
        }

        String txtDatumOd = btnDatumOd.getText().toString().trim();
        String txtDatumDo = btnDatumDo.getText().toString().trim();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {
            sdf.parse(txtDatumOd);
            sdf.parse(txtDatumDo);
        } catch (ParseException e) {
            Toast.makeText(this, "Unesite datume", Toast.LENGTH_LONG).show();
            return;
        }

        String datumOd = prevediDatumUFormatZaBazu(btnDatumOd.getText().toString().trim());
        String datumDo = prevediDatumUFormatZaBazu(btnDatumDo.getText().toString().trim());

        try {
            Date datum1 = sdf.parse(btnDatumOd.getText().toString().trim());
            Date datum2 = sdf.parse(btnDatumDo.getText().toString().trim());

            if (datum2 != null && datum1 != null && datum1.getTime() > datum2.getTime()) {
                Toast.makeText(this, "Datum OD ne može biti veći od datuma DO", Toast.LENGTH_LONG).show();
                return;
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
        datum = datum.substring(0, datum.length() - 1);
        datum = datum.replace('.', '-');
        String[] datumi = datum.split("-");
        return datumi[2] + "-" + datumi[1] + "-" + datumi[0];
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dodaj_novi_meni, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.ikonica_sacuvaj) {
            if (btnSacuvaj.isEnabled()) {
                sacuvajPasu();
            } else {
                Toast.makeText(this, "Molimo Vas dodajte prvo pčelinjak", Toast.LENGTH_LONG).show();
                return true;
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
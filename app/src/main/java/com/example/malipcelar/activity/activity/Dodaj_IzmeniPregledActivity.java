package com.example.malipcelar.activity.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.fragmenti.DatumPickerFragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class Dodaj_IzmeniPregledActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String EXTRA_ID =
            "com.example.malipcelar.activity.activity.EXTRA_ID";
    public static final String EXTRA_DATUM_PREGLEDA =
            "com.example.malipcelar.activity.activity.EXTRA_DATUM_PREGLEDA";
    public static final String EXTRA_MATICA =
            "com.example.malipcelar.activity.activity.EXTRA_MATICA";
    public static final String EXTRA_MLADO_LEGLO =
            "com.example.malipcelar.activity.activity.EXTRA_MLADO_LEGLO";
    public static final String EXTRA_MATICNJAK =
            "com.example.malipcelar.activity.activity.EXTRA_MATICNJAK";
    public static final String EXTRA_KONSTANTOVANO_ROJENJE =
            "com.example.malipcelar.activity.activity.EXTRA_KONSTANTOVANO_ROJENJE";
    public static final String EXTRA_BROJ_ULICA_POPUNJENIH_PCELOM =
            "com.example.malipcelar.activity.activity.EXTRA_BROJ_ULICA_POPUNJENIH_PCELOM";
    public static final String EXTRA_BROJ_RAMOVA_SA_LEGLOM =
            "com.example.malipcelar.activity.activity.EXTRA_BROJ_RAMOVA_SA_LEGLOM";
    public static final String EXTRA_BROJ_RAMOVA_SA_VENCOM_HRANE_U_PLODISTU =
            "com.example.malipcelar.activity.activity.EXTRA_BROJ_RAMOVA_SA_VENCOM_HRANE_U_PLODISTU";
    public static final String EXTRA_BROJ_RAMOVA_SA_POLENOM =
            "com.example.malipcelar.activity.activity.EXTRA_BROJ_RAMOVA_SA_POLENOM";
    public static final String EXTRA_BROJ_RAMOVA_SA_LEGLOM_PODIGNUTIH_U_MEDISTE =
            "com.example.malipcelar.activity.activity.EXTRA_BROJ_RAMOVA_SA_LEGLOM_PODIGNUTIH_U_MEDISTE";
    public static final String EXTRA_BROJ_ODUZETIH_RAMOVA_SA_LEGLOM =
            "com.example.malipcelar.activity.activity.EXTRA_BROJ_ODUZETIH_RAMOVA_SA_LEGLOM";
    public static final String EXTRA_BROJ_RAMOVA_SA_MEDOM_ZA_VADJENJE =
            "com.example.malipcelar.activity.activity.EXTRA_BROJ_RAMOVA_SA_MEDOM_ZA_VADJENJE";
    public static final String EXTRA_BROJ_IZVADJENIH_RAMOVA_SA_MEDOM =
            "com.example.malipcelar.activity.activity.EXTRA_BROJ_IZVADJENIH_RAMOVA_SA_MEDOM";
    public static final String EXTRA_BROJ_UBACENIH_OSNOVA =
            "com.example.malipcelar.activity.activity.EXTRA_BROJ_UBACENIH_OSNOVA";
    public static final String EXTRA_BROJ_UBACENIH_PRAZNIH_RAMOVA =
            "com.example.malipcelar.activity.activity.EXTRA_BROJ_UBACENIH_PRAZNIH_RAMOVA";
    public static final String EXTRA_NAPOMENA =
            "com.example.malipcelar.activity.activity.EXTRA_NAPOMENA";

    Button btnDatumPregleda;
    Button btnSacuvaj;
    TextView txtBrUlicaPopunjenihPcelom;
    RadioButton rbMatica;
    RadioButton rbMladoLeglo;
    CheckBox cbMaticnjak;
    CheckBox cbKonstantovanoRojenje;
    TextView txtBrRamovaSaLeglom;
    TextView txtBrRamovaSaVencomHraneUPlodistu;
    TextView txtBrRamovaSaPolenom;
    TextView txtBrRamovaSaLeglomPodignutihUMediste;
    TextView txtBrOduzetihRamovaSaLeglom;
    TextView txtBrRamovaSaMedomZaVadjenje;
    TextView txtBrIzvadjenihRamovaSaMedom;
    TextView txtBrUbacenihOsnova;
    TextView txtBrUbacenihPraznihRamova;
    TextView txtNapomena;
    RadioGroup radioGroupMatica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodaj_izmeni_pregled_activity);

        srediAtribute();
        srediListenere();
        srediIntent();
    }

    private void srediAtribute() {
        btnDatumPregleda = findViewById(R.id.btnDatumPregleda);
        txtBrUlicaPopunjenihPcelom = findViewById(R.id.txtBrojUlicaPopunjenihPcelom);
        rbMatica = findViewById(R.id.rbMatica);
        rbMladoLeglo = findViewById(R.id.rbMladoLeglo);
        cbMaticnjak = findViewById(R.id.cbMaticnjak);
        cbKonstantovanoRojenje = findViewById(R.id.cbRojenje);
        txtBrRamovaSaLeglom = findViewById(R.id.txtBrRamovaSaLeglom);
        txtBrRamovaSaVencomHraneUPlodistu = findViewById(R.id.txtBrRamovaSaVencomHraneUPlodistu);
        txtBrRamovaSaPolenom = findViewById(R.id.txtBrRamovaSaPolenom);
        txtBrRamovaSaLeglomPodignutihUMediste = findViewById(R.id.txtBrRamovaSaLeglomPodignutihUMediste);
        txtBrOduzetihRamovaSaLeglom = findViewById(R.id.txtBrOduzetihRamovaSaLeglom);
        txtBrRamovaSaMedomZaVadjenje = findViewById(R.id.txtBrRamovaSaMedomZaVadjenje);
        txtBrIzvadjenihRamovaSaMedom = findViewById(R.id.txtBrRamovaSaMedom);
        txtBrUbacenihOsnova = findViewById(R.id.txtBrUbacenihOsnova);
        txtBrUbacenihPraznihRamova = findViewById(R.id.txtBrUbacenihPraznihRamova);
        txtNapomena = findViewById(R.id.txtNapomene);
        radioGroupMatica = findViewById(R.id.radioGroupMatica);
        btnSacuvaj = findViewById(R.id.btnSacuvajPregled);
        srediDatum();
    }

    private void srediDatum() {
        Date c = Calendar.getInstance().getTime();
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        btnDatumPregleda.setText(currentDateString);
    }

    private void sacuvajPregled() {

        String datum = prevediDatumUFormatZaBazu(btnDatumPregleda.getText().toString().trim());
        String txtBrUlicaPopunjenihPcelom = this.txtBrUlicaPopunjenihPcelom.getText().toString().trim();
        String txtBrRamovaSaLeglom = this.txtBrRamovaSaLeglom.getText().toString().trim();
        String txtBrRamovaSaVencomHraneUPlodistu = this.txtBrRamovaSaVencomHraneUPlodistu.getText().toString().trim();
        String txtBrRamovaSaPolenom = this.txtBrRamovaSaPolenom.getText().toString().trim();
        String txtBrRamovaSaLeglomPodignutihUMediste = this.txtBrRamovaSaLeglomPodignutihUMediste.getText().toString().trim();
        String txtBrOduzetihRamovaSaLeglom = this.txtBrOduzetihRamovaSaLeglom.getText().toString().trim();
        String txtBrRamovaSaMedomZaVadjenje = this.txtBrRamovaSaMedomZaVadjenje.getText().toString().trim();
        String txtBrIzvadjenihRamovaSaMedom = this.txtBrIzvadjenihRamovaSaMedom.getText().toString().trim();
        String txtBrUbacenihOsnova = this.txtBrUbacenihOsnova.getText().toString().trim();
        String txtBrUbacenihPraznihRamova = this.txtBrUbacenihPraznihRamova.getText().toString().trim();
        String txtNapomena = this.txtNapomena.getText().toString().trim();

        int brUlicaPopunjenihPcelom = 0;
        int brRamovaSaLeglom = 0;
        int brRamovaSaVencomHraneUPlodistu = 0;
        int brRamovaSaPolenom = 0;
        int brRamovaSaLeglomPodignutihUMediste = 0;
        int brOduzetihRamovaSaLeglom = 0;
        int brRamovaSaMedomZaVadjenje = 0;
        int brIzvadjenihRamovaSaMedom = 0;
        int brUbacenihOsnova = 0;
        int brUbacenihPraznihRamova = 0;

        if (!txtBrUlicaPopunjenihPcelom.isEmpty()) {
            try {
                brUlicaPopunjenihPcelom = Integer.parseInt(txtBrUlicaPopunjenihPcelom);
            } catch (Exception e) {
                Toast.makeText(this, "Unesite broj", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (!txtBrRamovaSaLeglom.isEmpty()) {
            try {
                brRamovaSaLeglom = Integer.parseInt(txtBrRamovaSaLeglom);
            } catch (Exception e) {
                Toast.makeText(this, "Unesite broj", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (!txtBrRamovaSaVencomHraneUPlodistu.isEmpty()) {
            try {
                brRamovaSaVencomHraneUPlodistu = Integer.parseInt(txtBrRamovaSaVencomHraneUPlodistu);
            } catch (Exception e) {
                Toast.makeText(this, "Unesite broj", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (!txtBrRamovaSaPolenom.isEmpty()) {
            try {
                brRamovaSaPolenom = Integer.parseInt(txtBrRamovaSaPolenom);
            } catch (Exception e) {
                Toast.makeText(this, "Unesite broj", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (!txtBrRamovaSaLeglomPodignutihUMediste.isEmpty()) {
            try {
                brRamovaSaLeglomPodignutihUMediste = Integer.parseInt(txtBrRamovaSaLeglomPodignutihUMediste);
            } catch (Exception e) {
                Toast.makeText(this, "Unesite broj", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (!txtBrOduzetihRamovaSaLeglom.isEmpty()) {
            try {
                brOduzetihRamovaSaLeglom = Integer.parseInt(txtBrOduzetihRamovaSaLeglom);
            } catch (Exception e) {
                Toast.makeText(this, "Unesite broj", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (!txtBrRamovaSaMedomZaVadjenje.isEmpty()) {
            try {
                brRamovaSaMedomZaVadjenje = Integer.parseInt(txtBrRamovaSaMedomZaVadjenje);
            } catch (Exception e) {
                Toast.makeText(this, "Unesite broj", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (!txtBrIzvadjenihRamovaSaMedom.isEmpty()) {
            try {
                brIzvadjenihRamovaSaMedom = Integer.parseInt(txtBrIzvadjenihRamovaSaMedom);
            } catch (Exception e) {
                Toast.makeText(this, "Unesite broj", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (!txtBrUbacenihOsnova.isEmpty()) {
            try {
                brUbacenihOsnova = Integer.parseInt(txtBrUbacenihOsnova);
            } catch (Exception e) {
                Toast.makeText(this, "Unesite broj", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (!txtBrUbacenihPraznihRamova.isEmpty()) {
            try {
                brUbacenihPraznihRamova = Integer.parseInt(txtBrUbacenihPraznihRamova);
            } catch (Exception e) {
                Toast.makeText(this, "Unesite broj", Toast.LENGTH_LONG).show();
                return;
            }
        }

        int selectedId = radioGroupMatica.getCheckedRadioButtonId();

        RadioButton selectedRadioButton = (RadioButton) findViewById(selectedId);

        boolean matica = false;
        boolean mladoLeglo = false;
        if (selectedRadioButton != null) {
            if (selectedRadioButton.getText().toString().equals("Matica")) {
                matica = true;
            }
            if (selectedRadioButton.getText().toString().equals("Mlado leglo")) {
                mladoLeglo = true;
            }
        }
        boolean maticnjak = false;
        boolean konstantovanoRojenje = false;

        if (cbMaticnjak.isChecked()) {
            maticnjak = true;
        }

        if (cbKonstantovanoRojenje.isChecked()) {
            konstantovanoRojenje = true;
        }

        Intent podaci = new Intent();
        podaci.putExtra(EXTRA_DATUM_PREGLEDA, datum);
        podaci.putExtra(EXTRA_MATICA, matica);
        podaci.putExtra(EXTRA_MLADO_LEGLO, mladoLeglo);
        podaci.putExtra(EXTRA_MATICNJAK, maticnjak);
        podaci.putExtra(EXTRA_KONSTANTOVANO_ROJENJE, konstantovanoRojenje);
        podaci.putExtra(EXTRA_BROJ_ULICA_POPUNJENIH_PCELOM, brUlicaPopunjenihPcelom);
        podaci.putExtra(EXTRA_BROJ_RAMOVA_SA_LEGLOM, brRamovaSaLeglom);
        podaci.putExtra(EXTRA_BROJ_RAMOVA_SA_VENCOM_HRANE_U_PLODISTU, brRamovaSaVencomHraneUPlodistu);
        podaci.putExtra(EXTRA_BROJ_RAMOVA_SA_POLENOM, brRamovaSaPolenom);
        podaci.putExtra(EXTRA_BROJ_RAMOVA_SA_LEGLOM_PODIGNUTIH_U_MEDISTE, brRamovaSaLeglomPodignutihUMediste);
        podaci.putExtra(EXTRA_BROJ_ODUZETIH_RAMOVA_SA_LEGLOM, brOduzetihRamovaSaLeglom);
        podaci.putExtra(EXTRA_BROJ_RAMOVA_SA_MEDOM_ZA_VADJENJE, brRamovaSaMedomZaVadjenje);
        podaci.putExtra(EXTRA_BROJ_IZVADJENIH_RAMOVA_SA_MEDOM, brIzvadjenihRamovaSaMedom);
        podaci.putExtra(EXTRA_BROJ_UBACENIH_OSNOVA, brUbacenihOsnova);
        podaci.putExtra(EXTRA_BROJ_UBACENIH_PRAZNIH_RAMOVA, brUbacenihPraznihRamova);
        podaci.putExtra(EXTRA_NAPOMENA, txtNapomena);


        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            podaci.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, podaci);
        finish();
    }

    private void srediListenere() {
        btnDatumPregleda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatumPickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        btnSacuvaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sacuvajPregled();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void srediIntent() {
        Intent data = getIntent();

        if (data.hasExtra(EXTRA_ID)) {
            setTitle("Izmeni pregled");
            txtNapomena.setText(data.getStringExtra(EXTRA_NAPOMENA));
            String datum = data.getStringExtra(EXTRA_DATUM_PREGLEDA);

            boolean matica = data.getBooleanExtra(Dodaj_IzmeniPregledActivity.EXTRA_MATICA, false);
            boolean mladoLeglo = data.getBooleanExtra(Dodaj_IzmeniPregledActivity.EXTRA_MLADO_LEGLO, false);
            boolean maticnjak = data.getBooleanExtra(Dodaj_IzmeniPregledActivity.EXTRA_MATICNJAK, false);
            boolean konstantovanoRojenje = data.getBooleanExtra(Dodaj_IzmeniPregledActivity.EXTRA_KONSTANTOVANO_ROJENJE, false);

            if (matica) {
                radioGroupMatica.check(R.id.rbMatica);
            } else if (mladoLeglo) {
                radioGroupMatica.check(R.id.rbMladoLeglo);
            }
            if (maticnjak) {
                cbMaticnjak.setChecked(true);
            }
            if (konstantovanoRojenje) {
                cbKonstantovanoRojenje.setChecked(true);
            }

            int brojUlicaPopunjenihPcelom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_ULICA_POPUNJENIH_PCELOM, -1);
            int brojRamovaSaLeglom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_LEGLOM, -1);
            int brojRamovaSaVencomHraneUPlodistu = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_VENCOM_HRANE_U_PLODISTU, -1);
            int brojRamovaSaPolenom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_POLENOM, -1);
            int brojRamovaSaLeglomPodignutihUMediste = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_LEGLOM_PODIGNUTIH_U_MEDISTE, -1);
            int brojOduzetihRamovaSaLeglom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_ODUZETIH_RAMOVA_SA_LEGLOM, -1);
            int brojRamovaSaMedomZaVadjenje = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_RAMOVA_SA_MEDOM_ZA_VADJENJE, -1);
            int brojIzvadjenihRamovaSaMedom = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_IZVADJENIH_RAMOVA_SA_MEDOM, -1);
            int brojUbacenihOsnova = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_UBACENIH_OSNOVA, -1);
            int brojUbacenihPraznihRamova = data.getIntExtra(Dodaj_IzmeniPregledActivity.EXTRA_BROJ_UBACENIH_PRAZNIH_RAMOVA, -1);


            if (brojUlicaPopunjenihPcelom != -1) {
                txtBrUlicaPopunjenihPcelom.setText(brojUlicaPopunjenihPcelom + "");
            }
            if (brojRamovaSaLeglom != -1) {
                txtBrRamovaSaLeglom.setText(brojRamovaSaLeglom + "");
            }
            if (brojRamovaSaVencomHraneUPlodistu != -1) {
                txtBrRamovaSaVencomHraneUPlodistu.setText(brojRamovaSaVencomHraneUPlodistu + "");
            }
            if (brojRamovaSaPolenom != -1) {
                txtBrRamovaSaPolenom.setText(brojRamovaSaPolenom + "");
            }
            if (brojRamovaSaLeglomPodignutihUMediste != -1) {
                txtBrRamovaSaLeglomPodignutihUMediste.setText(brojRamovaSaLeglomPodignutihUMediste + "");
            }
            if (brojOduzetihRamovaSaLeglom != -1) {
                txtBrOduzetihRamovaSaLeglom.setText(brojOduzetihRamovaSaLeglom + "");
            }
            if (brojRamovaSaMedomZaVadjenje != -1) {
                txtBrRamovaSaMedomZaVadjenje.setText(brojRamovaSaMedomZaVadjenje + "");
            }

            if (brojIzvadjenihRamovaSaMedom != -1) {
                txtBrIzvadjenihRamovaSaMedom.setText(brojIzvadjenihRamovaSaMedom + "");
            }
            if (brojUbacenihOsnova != -1) {
                txtBrUbacenihOsnova.setText(brojUbacenihOsnova + "");
            }
            if (brojUbacenihPraznihRamova != -1) {
                txtBrUbacenihPraznihRamova.setText(brojUbacenihPraznihRamova + "");
            }

            String[] datumi = new String[0];
            if (datum != null) datumi = datum.split("-");
            String dobarDatum = datumi[2] + "." + datumi[1] + "." + datumi[0] + ".";
            btnDatumPregleda.setText(dobarDatum);


        } else {
            setTitle("Dodaj pregled");
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        btnDatumPregleda.setText(currentDateString);
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
            sacuvajPregled();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    private String prevediDatumUFormatZaBazu(String datum) {
        datum = datum.substring(0, datum.length() - 1);
        datum = datum.replace('.', '-');
        String[] datumi = datum.split("-");
        return datumi[2] + "-" + datumi[1] + "-" + datumi[0];
    }
}
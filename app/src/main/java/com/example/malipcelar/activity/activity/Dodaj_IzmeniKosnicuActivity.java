package com.example.malipcelar.activity.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.pomocneKlase.QRSkenerPortret;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Dodaj_IzmeniKosnicuActivity extends AppCompatActivity {

    public static final String EXTRA_RB_KOSNICE =
            "com.example.malipcelar.activity.activity.EXTRA_RB_KOSNICE";
    public static final String EXTRA_REGISTARSKI_BROJ_KOSNICE =
            "com.example.malipcelar.activity.activity.EXTRA_REGISTARSKI_BROJ_KOSNICE";
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

    private static final int CAMERA_PERMISSION_CODE = 101;

    TextView txtRedniBroj;
    TextView txtRegistarskiBroj;
    Spinner spGodinaProizvodnje;
    RadioButton rbSelekcionisana;
    RadioButton rbPrirodna;
    TextView txtBolesti;
    TextView txtNapomena;
    List<Integer> zauzetiRbOvi;
    RadioGroup radioGroup;
    Button btnSacuvaj;
    Button btnQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodaj_izmeni_kosnicu_activity);

        srediAtribute();
        srediListenere();
        srediIntent();
    }

    private void srediListenere() {
        btnSacuvaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sacuvajKosnicu();
            }
        });
        btnQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    otvoriQRSkener();
                } else {
                    requestPermission();
                }
            }
        });
    }

    private void otvoriQRSkener() {
        IntentIntegrator integrator = new IntentIntegrator(Dodaj_IzmeniKosnicuActivity.this);
        integrator.setPrompt("Skenirajte QR kod");
        integrator.setCameraId(0);
        integrator.setOrientationLocked(true);
        integrator.setBeepEnabled(true);
        integrator.setCaptureActivity(QRSkenerPortret.class);
        integrator.initiateScan();
    }

    @SuppressLint("SetTextI18n")
    private void srediIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_RB_KOSNICE)) {
            setTitle("Izmeni košnicu");
            txtRedniBroj.setText(intent.getIntExtra(EXTRA_RB_KOSNICE, -1) + "");
            txtBolesti.setText(intent.getStringExtra(EXTRA_BOLESTI));
            txtNapomena.setText(intent.getStringExtra(EXTRA_NAPOMENA));
            txtRegistarskiBroj.setText(intent.getStringExtra(EXTRA_REGISTARSKI_BROJ_KOSNICE));
            boolean prirodna = intent.getBooleanExtra(EXTRA_RB_PRIRODNA, false);
            String godina = intent.getStringExtra(EXTRA_GODINA_PROIZVODNJE_MATICE);

            if (prirodna) {
                radioGroup.check(R.id.rbPrirodna);
            } else {
                radioGroup.check(R.id.rbSelekcionisana);
            }
            spGodinaProizvodnje.setSelection(((ArrayAdapter<String>) spGodinaProizvodnje.getAdapter()).getPosition(godina));

        } else {
            setTitle("Dodaj novu košnicu");
        }
    }

    private void srediAtribute() {
        txtRedniBroj = findViewById(R.id.txtRBKosnice);
        spGodinaProizvodnje = findViewById(R.id.spGodinaProizvodnje);
        rbSelekcionisana = findViewById(R.id.rbSelekcionisana);
        rbPrirodna = findViewById(R.id.rbPrirodna);
        txtBolesti = findViewById(R.id.txtBolesti);
        txtNapomena = findViewById(R.id.txtNapomenaKosnica);
        btnSacuvaj = findViewById(R.id.btnSacuvajKosnicu);
        btnQR = findViewById(R.id.btnQR);
        radioGroup = findViewById(R.id.radioGroup);
        txtRegistarskiBroj = findViewById(R.id.txtRegistarskiBroj);
        srediSpinner();

        zauzetiRbOvi = getIntent().getIntegerArrayListExtra(EXTRA_ZAUZETI_RB);
    }

    private void srediSpinner() {
        long mili = System.currentTimeMillis();

        Date date = new Date(mili);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);

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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
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

    private void sacuvajKosnicu() {
        String rb = txtRedniBroj.getText().toString().trim();
        int redniBroj;
        try {
            redniBroj = Integer.parseInt(rb);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Molimo unesite brojnu vrednost", Toast.LENGTH_LONG).show();
            return;
        }

        if (redniBroj == (getIntent().getIntExtra(EXTRA_RB_KOSNICE, -1))) {
            //izmena

            String registarskiBrojNaziv = txtRegistarskiBroj.getText().toString().trim();
            if (registarskiBrojNaziv.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Morate uneti registarski broj ili naziv", Toast.LENGTH_LONG).show();
                return;
            }

            String godina = (String) spGodinaProizvodnje.getSelectedItem();

            if (radioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getApplicationContext(), "Morate selektovati vrstu matice", Toast.LENGTH_LONG).show();
                return;
            }

            int selectedId = radioGroup.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = findViewById(selectedId);
            boolean prirodna = false;
            boolean selekcionisana = false;

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
            podaci.putExtra(EXTRA_REGISTARSKI_BROJ_KOSNICE, registarskiBrojNaziv);
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
            Toast.makeText(getApplicationContext(), "Pokušavate da unosite postojeći redni broj", Toast.LENGTH_LONG).show();
            return;
        }

        String registarskiBrojNaziv = txtRegistarskiBroj.getText().toString().trim();
        if (registarskiBrojNaziv.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Morate uneti registarski broj ili naziv", Toast.LENGTH_LONG).show();
            return;
        }

        String godina = (String) spGodinaProizvodnje.getSelectedItem();

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Morate selektovati vrstu matice", Toast.LENGTH_LONG).show();
            return;
        }

        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);

        boolean prirodna = false;
        boolean selekcionisana = false;

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
        podaci.putExtra(EXTRA_REGISTARSKI_BROJ_KOSNICE, registarskiBrojNaziv);
        podaci.putExtra(EXTRA_RB_SELEKCIONISANA, selekcionisana);
        podaci.putExtra(EXTRA_RB_PRIRODNA, prirodna);
        podaci.putExtra(EXTRA_BOLESTI, bolesti);
        podaci.putExtra(EXTRA_NAPOMENA, napomena);

        setResult(RESULT_OK, podaci);
        finish();
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
            sacuvajKosnicu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(Dodaj_IzmeniKosnicuActivity.this, Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(Dodaj_IzmeniKosnicuActivity.this, Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(Dodaj_IzmeniKosnicuActivity.this, new String[]{Manifest.permission.CAMERA}, Dodaj_IzmeniKosnicuActivity.CAMERA_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                otvoriQRSkener();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                txtRegistarskiBroj.setText("" + result.getContents());
            }
        }
    }

}
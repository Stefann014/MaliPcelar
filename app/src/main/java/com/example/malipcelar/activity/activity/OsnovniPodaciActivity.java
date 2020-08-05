package com.example.malipcelar.activity.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.malipcelar.R;

public class OsnovniPodaciActivity extends AppCompatActivity {

    private Button btnIzmeni;
    private Button btnSacuvaj;

    private TextView txtImePcelara;
    private TextView txtGazdinstvo;

    private String gazdinstvo;
    private String imePcelara;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String IME_PCELARA = "ime pcelara";
    public static final String GAZDINSTVO = "gazdinstvo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.osnovni__podaci_activity);

        srediAtribute();
    }

    private void srediListenere() {
        btnSacuvaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sacuvajPodatke();
                btnSacuvaj.setEnabled(false);
                btnIzmeni.setEnabled(true);
                txtImePcelara.setEnabled(false);
                txtGazdinstvo.setEnabled(false);
            }
        });
        btnIzmeni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnIzmeni.setEnabled(false);
                btnSacuvaj.setEnabled(true);
                txtGazdinstvo.setEnabled(true);
                txtImePcelara.setEnabled(true);
            }
        });
    }

    private void srediAtribute() {
        btnIzmeni = findViewById(R.id.btnIzmeniPcelara);
        btnSacuvaj = findViewById(R.id.btnSacuvajPcelara);
        txtImePcelara = findViewById(R.id.txtImePcelara);
        txtGazdinstvo = findViewById(R.id.txtGazdinstvo);

        imePcelara = txtImePcelara.getText().toString().trim();
        gazdinstvo = txtGazdinstvo.getText().toString().trim();

        srediListenere();
        ucitajPodatke();
        updatePodatke();

        if (imePcelara.isEmpty() && gazdinstvo.isEmpty()) {
            txtImePcelara.setEnabled(true);
            txtGazdinstvo.setEnabled(true);
            btnIzmeni.setEnabled(false);
            btnSacuvaj.setEnabled(true);
        }

        if (!imePcelara.isEmpty() && !gazdinstvo.isEmpty()) {
            txtImePcelara.setEnabled(false);
            txtGazdinstvo.setEnabled(false);
            btnIzmeni.setEnabled(true);
            btnSacuvaj.setEnabled(false);
        }
    }

    public void ucitajPodatke() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        imePcelara = sharedPreferences.getString(IME_PCELARA, "");
        gazdinstvo = sharedPreferences.getString(GAZDINSTVO, "");
    }

    public void sacuvajPodatke() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IME_PCELARA, txtImePcelara.getText().toString());
        editor.putString(GAZDINSTVO, txtGazdinstvo.getText().toString());
        editor.apply();
        Toast.makeText(this, "Podaci sacuvani", Toast.LENGTH_SHORT).show();
    }

    public void updatePodatke() {
        txtImePcelara.setText(imePcelara);
        txtGazdinstvo.setText(gazdinstvo);
    }

}
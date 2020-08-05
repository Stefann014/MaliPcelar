package com.example.malipcelar.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.malipcelar.R;

public class OsnovniPodaciActivity extends AppCompatActivity {

    Button btnIzmeni;
    Button btnSacuvaj;

    TextView txtImePcelara;
    TextView txtGazdinstvo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.osnovni__podaci_activity);

        srediAtribute();
        srediListenere();
    }

    private void srediListenere() {
    }

    private void srediAtribute() {
        btnIzmeni = findViewById(R.id.btnIzmeniPcelara);
        btnSacuvaj = findViewById(R.id.btnSacuvajPcelara);
        txtImePcelara = findViewById(R.id.txtImePcelara);
        txtGazdinstvo = findViewById(R.id.txtGazdinstvo);

    }
}
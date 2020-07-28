package com.example.malipcelar.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.domen.Kosnica;
import com.example.malipcelar.activity.domen.Pcelinjak;

public class LecenjeActivity extends AppCompatActivity {

    public static final String EXTRA_KOSNICA =
            "com.example.malipcelar.activity.activity.KOSNICA";
    public static final String EXTRA_PCELINJAK =
            "com.example.malipcelar.activity.activity.PCELINJAK";

    public static final int DODAJ_NOVO_LECENJE = 1;
    public static final int IZMENI_LECENJE = 2;

    Kosnica kosnica;
    Pcelinjak pcelinjak;

    Button btnDodajLecenje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecenje_activity);

        srediAtribute();
        srediListenere();
    }

    private void srediAtribute() {
        Intent intent = getIntent();
        kosnica = (Kosnica) intent.getSerializableExtra(EXTRA_KOSNICA);
        pcelinjak = (Pcelinjak) intent.getSerializableExtra(EXTRA_PCELINJAK);

        btnDodajLecenje = findViewById(R.id.btnDodajLecenje);
    }

    private void srediListenere() {
        btnDodajLecenje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LecenjeActivity.this, Dodaj_IzmeniLecenjeActivity.class);
                startActivityForResult(intent, DODAJ_NOVO_LECENJE);
            }
        });
    }
}
package com.example.malipcelar.activity.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.domen.Pcelinjak;

public class Dodaj_IzmeniKosnicuActivity extends AppCompatActivity {

    public static final String EXTRA_PCELINJAK =
            "com.example.malipcelar.activity.activity.PCELINJAK";

    Pcelinjak pcelinjak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodaj_izmeni_kosnicu_activity);
        srediAtribute();

    }

    private void srediAtribute() {
        Intent intent = getIntent();
        this.pcelinjak = (Pcelinjak) intent.getSerializableExtra(EXTRA_PCELINJAK);
    }
}
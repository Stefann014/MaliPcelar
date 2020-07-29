package com.example.malipcelar.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.ExampleDialog;
import com.example.malipcelar.activity.domen.Kosnica;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.fragmenti.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PrihranaActivity extends AppCompatActivity implements BottomSheetDialog.BottomSheetListener, ExampleDialog.ExampleDialogListener {

    public static final String EXTRA_KOSNICA =
            "com.example.malipcelar.activity.activity.KOSNICA";
    public static final String EXTRA_PCELINJAK =
            "com.example.malipcelar.activity.activity.PCELINJAK";

    Kosnica kosnica;
    Pcelinjak pcelinjak;
    FloatingActionButton btnDodajNovuPrihranu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prihrana_activity);

        srediAtribute();
        srediListenere();
    }

    private void srediListenere() {
        btnDodajNovuPrihranu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheet = new BottomSheetDialog();
                bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
            }
        });
    }

    @Override
    public void onBtnPogacaClicked() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void onBtnSirupClicked() {
    }

    private void srediAtribute() {
        Intent intent = getIntent();
        kosnica = (Kosnica) intent.getSerializableExtra(EXTRA_KOSNICA);
        pcelinjak = (Pcelinjak) intent.getSerializableExtra(EXTRA_PCELINJAK);
        btnDodajNovuPrihranu = findViewById(R.id.btnDodajNovuPrihranu);
    }

    @Override
    public void sacuvaj(String datum, String kg) {



    }

}
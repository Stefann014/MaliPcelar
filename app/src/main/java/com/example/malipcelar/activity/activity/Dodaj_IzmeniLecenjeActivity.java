package com.example.malipcelar.activity.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.malipcelar.R;

import java.util.ArrayList;

public class Dodaj_IzmeniLecenjeActivity extends AppCompatActivity {

    Button btnDatum;
    Spinner spBolesti;
    CheckBox chPrimeniLecenjeNaPcelinjak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodaj_izmeni_lecenje_activity);

        srediAtribute();
    }

    private void srediAtribute() {
        btnDatum = findViewById(R.id.btnDatumLecenja);
        spBolesti = findViewById(R.id.spBolesti);
        chPrimeniLecenjeNaPcelinjak = findViewById(R.id.chPrimeniLecenjeNaPcelinjak);
        srediSpinner();
    }

    private void srediSpinner() {

        ArrayList<String> bolesti = new ArrayList<>();
        bolesti.add("Nozemoza");
        bolesti.add("Varoa");
        bolesti.add("Americka kuga");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, bolesti); // ako je greska tu je
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBolesti.setAdapter(adapter);
        spBolesti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}
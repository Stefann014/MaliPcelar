package com.example.malipcelar.activity.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.malipcelar.R;

public class PocetniActivity extends AppCompatActivity {

    Button btnNapomene;
    Button btnPcelinjaci;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pocetni_activity);
        srediAtribute();
        srediListener();
    }


    private void srediAtribute() {
        btnNapomene = findViewById(R.id.btnNapomene);
        btnPcelinjaci = findViewById(R.id.btnPcelinjaci);
    }

    private void srediListener() {
        btnNapomene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otvoriActivityOpsteNapomene();
            }
        });
        btnPcelinjaci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otvoriActivityPcelinjaci();
            }
        });

    }

    private void otvoriActivityPcelinjaci() {
        Intent intent = new Intent(this, PcelinjaciActivity.class);
        startActivity(intent);
    }

    private void otvoriActivityOpsteNapomene() {
        Intent intent = new Intent(this, OpsteNapomeneActivity.class);
        startActivity(intent);
    }
}

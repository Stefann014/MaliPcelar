package com.example.malipcelar.activity.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.adapteri.PcelinjaciAdapter;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.viewModel.PcelinjakViewModel;

import java.util.List;

public class OsnovniPodaciActivity extends AppCompatActivity {

    private Button btnIzmeni;
    private Button btnSacuvaj;

    List<Pcelinjak> pcelinjaci;

    private TextView txtImePcelara;
    private TextView txtGazdinstvo;

    private String gazdinstvo;
    private String imePcelara;

    RecyclerView recyclerView;
    private PcelinjakViewModel pcelinjakViewModel;
    final PcelinjaciAdapter adapter = new PcelinjaciAdapter();

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String IME_PCELARA = "ime pcelara";
    public static final String GAZDINSTVO = "gazdinstvo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.osnovni_podaci_activity);

        srediAtribute();
        srediRecycleView();
        srediKomunikacijuSaViewModel();
    }

    private void srediKomunikacijuSaViewModel() {
        pcelinjakViewModel = ViewModelProviders.of(this).get(PcelinjakViewModel.class);
        srediObservere();
    }

    public void srediRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void srediObservere() {

        pcelinjakViewModel.getAllPcelinjaci().observe(this, new Observer<List<Pcelinjak>>() {
            @Override
            public void onChanged(@Nullable List<Pcelinjak> pcelinjaci) {
                OsnovniPodaciActivity.this.pcelinjaci = pcelinjaci;
                adapter.submitList(pcelinjaci);
            }
        });

    }

    private void srediListenere() {
        btnSacuvaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtImePcelara.getText().toString().isEmpty() || txtGazdinstvo.getText().toString().isEmpty()) {
                    Toast.makeText(OsnovniPodaciActivity.this, "Morate uneti podatke!", Toast.LENGTH_LONG).show();
                    return;
                }
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
        recyclerView = findViewById(R.id.rvParcele);

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
package com.example.malipcelar.activity.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.adapteri.PcelinjaciAdapter;
import com.example.malipcelar.activity.domen.Pasa;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.fragmenti.DaLiZelisDaIzbrisesDialog;
import com.example.malipcelar.activity.viewModel.PcelinjakViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.malipcelar.activity.activity.OsnovniPodaciActivity.GAZDINSTVO;
import static com.example.malipcelar.activity.activity.OsnovniPodaciActivity.IME_PCELARA;
import static com.example.malipcelar.activity.activity.OsnovniPodaciActivity.SHARED_PREFS;

public class PcelinjaciActivity extends AppCompatActivity implements DaLiZelisDaIzbrisesDialog.ExampleDialogListener {

    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final int DODAJ_NOVI_PCELINJAK = 1;
    private static final int IZMENI_PCELINJAK = 2;


    //vars
    FloatingActionButton btnDodajPcelinjak;
    private PcelinjakViewModel pcelinjakViewModel;
    RecyclerView recyclerView;
    final PcelinjaciAdapter adapter = new PcelinjaciAdapter();
    List<Pcelinjak> pcelinjaci;
    List<Integer> zauzetiRBovi;
    int stariRb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcelinjaci_activity);
        setTitle("Pčelinjaci");
        srediAtribute();
        if (isServiceOK()) {
            srediListenere();
        }
        srediKomunikacijuSaViewModel();
        srediIzmeniPcelinjakNaKlik();
    }


    private void srediIzmeniPcelinjakNaKlik() {
        adapter.setOnItemClickListener(new PcelinjaciAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Pcelinjak pcelinjak) {
                Intent intent = new Intent(PcelinjaciActivity.this, KosniceActivity.class);
                intent.putExtra(KosniceActivity.EXTRA_PCELINJAK, pcelinjak);
                startActivity(intent);
            }

            @Override
            public void onIzmeniClick(Pcelinjak pcelinjak) {
                Intent intent = new Intent(PcelinjaciActivity.this, Dodaj_IzmeniPcelinjakActivity.class);
                intent.putExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_RB, pcelinjak.getRedniBrojPcelinjaka());
                stariRb = pcelinjak.getRedniBrojPcelinjaka();
                intent.putExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_NAZIV_PCELINJAKA, pcelinjak.getNazivPcelinjaka());
                intent.putExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_LOKACIJA, pcelinjak.getLokacija());
                intent.putExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_NADMORSKA_VISINA, pcelinjak.getNadmorskaVisina());
                intent.putExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_SLIKA, pcelinjak.getSlika());

                ArrayList<Integer> zauzeti = (ArrayList<Integer>) zauzetiRBovi;
                intent.putExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_ZAUZETI_RB, zauzeti);

                startActivityForResult(intent, IZMENI_PCELINJAK);
            }

            @Override
            public void onIzbrisiClick(Pcelinjak pcelinjak) {
                daLiZelisDaIzbrises(pcelinjak);
            }
        });
    }

    private void daLiZelisDaIzbrises(Pcelinjak pcelinjak) {
        DaLiZelisDaIzbrisesDialog dialog = new DaLiZelisDaIzbrisesDialog(pcelinjak);
        dialog.show(getSupportFragmentManager(), "Dialog");
    }

    private void srediKomunikacijuSaViewModel() {
        pcelinjakViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(PcelinjakViewModel.class);
        srediObservere();
    }

    private void srediObservere() {

        pcelinjakViewModel.getAllPcelinjaci().observe(this, new Observer<List<Pcelinjak>>() {
            @Override
            public void onChanged(@Nullable List<Pcelinjak> pcelinjaci) {
                PcelinjaciActivity.this.pcelinjaci = pcelinjaci;
                if (PcelinjaciActivity.this.pcelinjaci != null && PcelinjaciActivity.this.pcelinjaci.size() == 0) {
                    poruka();
                }
                adapter.submitList(pcelinjaci);
            }
        });

        pcelinjakViewModel.getAllPcelinjakRB().observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                zauzetiRBovi = integers;
            }
        });

    }

    private void poruka() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String imePcelara = sharedPreferences.getString(IME_PCELARA, "");
        String gazdinstvo = sharedPreferences.getString(GAZDINSTVO, "");
        String buffer = "Pozdrav, " + imePcelara + ".\nDa biste mogli da koristite sve naše funkcionalnosti morate dodati novi pčelinjak klikom na dugme plus.\n\n"
                + "Nadamo se da ćemo biti od pomoći vašem gazdinstvu: '" + gazdinstvo + "'";
        prikaziPoruku("Pčelinjaci", buffer);
    }

    public void prikaziPoruku(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void srediAtribute() {
        btnDodajPcelinjak = findViewById(R.id.btnDodajNoviPcelinjak);
        recyclerView = findViewById(R.id.rvPcelinjaci);
        pcelinjaci = null;
        zauzetiRBovi = null;
        srediRecycleView();
        stariRb = -1;
    }

    public void srediRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    public void srediListenere() {
        btnDodajPcelinjak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PcelinjaciActivity.this, Dodaj_IzmeniPcelinjakActivity.class);
                ArrayList<Integer> zauzeti = (ArrayList<Integer>) zauzetiRBovi;
                intent.putExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_ZAUZETI_RB, zauzeti);

                startActivityForResult(intent, DODAJ_NOVI_PCELINJAK);
            }
        });
    }

    public boolean isServiceOK() {
        int dostupan = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(PcelinjaciActivity.this);
        if (dostupan == ConnectionResult.SUCCESS) {
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(dostupan)) {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(PcelinjaciActivity.this, dostupan, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(getApplicationContext(), "Ne možemo pristupiti mapi", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DODAJ_NOVI_PCELINJAK && resultCode == RESULT_OK) {

            int pcelinjakRB = data.getIntExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_RB, -1);
            String nazivPcelinjaka = data.getStringExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_NAZIV_PCELINJAKA);
            String lokacija = data.getStringExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_LOKACIJA);
            String nadmorskaVisina = data.getStringExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_NADMORSKA_VISINA);
            String slika = data.getStringExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_SLIKA);

            Pcelinjak pcelinjak = new Pcelinjak(pcelinjakRB, nazivPcelinjaka, lokacija, nadmorskaVisina, slika);
            pcelinjakViewModel.insert(pcelinjak);
            Toast.makeText(this, "Pčelinjak je sačuvan", Toast.LENGTH_SHORT).show();

        } else if (requestCode == IZMENI_PCELINJAK && resultCode == RESULT_OK) {

            int id = data.getIntExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_RB, -1);
            if (id == -1) {
                Toast.makeText(this, "Pčelinjak ne može biti izmenjen", Toast.LENGTH_SHORT).show();
                return;
            }
            int pcelinjakRB = data.getIntExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_RB, -1);
            String nazivPcelinjaka = data.getStringExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_NAZIV_PCELINJAKA);
            String lokacija = data.getStringExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_LOKACIJA);
            String nadmorskaVisina = data.getStringExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_NADMORSKA_VISINA);
            String slika = data.getStringExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_SLIKA);
            Pcelinjak pcelinjak = new Pcelinjak(pcelinjakRB, nazivPcelinjaka, lokacija, nadmorskaVisina, slika);

            pcelinjakViewModel.update(pcelinjak);
            Toast.makeText(this, "Pčelinjak je izmenjen", Toast.LENGTH_SHORT).show();

            if (stariRb != -1) {
                pcelinjakViewModel.updateRb(stariRb, pcelinjakRB);
            }
        }
    }

    @Override
    public void kliknutoDa(Pasa pasa) {
    }

    @Override
    public void kliknutoDa(Pcelinjak pcelinjak) {
        izbrisiPcelinjak(pcelinjak);
        Toast.makeText(this, "Pčelinjak je izbrisan", Toast.LENGTH_SHORT).show();
    }

    private void izbrisiPcelinjak(Pcelinjak pcelinjak) {
        pcelinjakViewModel.delete(pcelinjak);
    }


}

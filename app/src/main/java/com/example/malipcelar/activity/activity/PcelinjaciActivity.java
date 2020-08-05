package com.example.malipcelar.activity.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.adapteri.PcelinjaciAdapter;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.viewModel.PcelinjakViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PcelinjaciActivity extends AppCompatActivity {

    private static final String TAG = "PcelinjaciActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final int DODAJ_NOVI_PCELINJAK = 3;
    private static final int IZMENI_PCELINJAK = 4;


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
        srediAtribute();
        if (isServiceOK()) {
            srediListenere();
        }
        srediKomunikacijuSaViewModel();
        srediBrisanje();
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
        });

        adapter.setOnIzmeniClickListener(new PcelinjaciAdapter.OnDropdownClickListener() {

            @Override
            public void onItemClickk(Pcelinjak pcelinjak) {
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
        });

    }

    private void srediKomunikacijuSaViewModel() {
        pcelinjakViewModel = ViewModelProviders.of(this).get(PcelinjakViewModel.class);
        srediObservere();
    }

    private void srediObservere() {

        pcelinjakViewModel.getAllPcelinjaci().observe(this, new Observer<List<Pcelinjak>>() {
            @Override
            public void onChanged(@Nullable List<Pcelinjak> pcelinjaci) {
                PcelinjaciActivity.this.pcelinjaci = pcelinjaci;
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
            Toast.makeText(getApplicationContext(), "Ne mozemo pristupiti mapi", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void srediBrisanje() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                pcelinjakViewModel.delete(adapter.getPcelinjakAt(viewHolder.getAdapterPosition()));
                Toast.makeText(PcelinjaciActivity.this, "Pcelinjak je izbrisana", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
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
            Toast.makeText(this, "Pcelinjak je sacuvan", Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
        } else if (requestCode == IZMENI_PCELINJAK && resultCode == RESULT_OK) {

            int id = data.getIntExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_RB, -1);
            if (id == -1) {
                Toast.makeText(this, "Pcelinjak ne moze biti izmenjen", Toast.LENGTH_SHORT).show();
                return;
            }
            int pcelinjakRB = data.getIntExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_RB, -1);
            String nazivPcelinjaka = data.getStringExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_NAZIV_PCELINJAKA);
            String lokacija = data.getStringExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_LOKACIJA);
            String nadmorskaVisina = data.getStringExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_NADMORSKA_VISINA);
            String slika = data.getStringExtra(Dodaj_IzmeniPcelinjakActivity.EXTRA_SLIKA);
            Pcelinjak pcelinjak = new Pcelinjak(pcelinjakRB, nazivPcelinjaka, lokacija, nadmorskaVisina, slika);

            pcelinjakViewModel.update(pcelinjak);
            if (stariRb != -1) {
                pcelinjakViewModel.updateRb(stariRb, pcelinjakRB);

            }
            adapter.notifyDataSetChanged();
            Toast.makeText(this, pcelinjak.toString(), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Pcelinjak nije izmenjen", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.glavni_meni, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.izbrisiSve:
                pcelinjakViewModel.deleteAllPcelinjaci();
                Toast.makeText(this, "Svi pcelinjaci su izbrisani", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

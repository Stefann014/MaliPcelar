package com.example.malipcelar.activity.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.malipcelar.activity.viewModel.OpstaNapomenaViewModel;
import com.example.malipcelar.activity.adapteri.OpsteNapomeneAdapter;
import com.example.malipcelar.activity.domen.OpstaNapomena;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class OpsteNapomeneActivity extends AppCompatActivity {
    public static final int DODAJ_NOVU_NAPOMENU = 1;
    public static final int IZMENI_NAPOMENU = 2;

    private OpstaNapomenaViewModel opstaNapomenaViewModel;
    FloatingActionButton btnDodajOpstuNapomenu;
    RecyclerView recyclerView;
    final OpsteNapomeneAdapter adapter = new OpsteNapomeneAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opste_napomene_activity);
        srediAtribute();
        srediListenere();
        srediViewModel();
        srediBrisanje();
        srediIzmeniNapomeneNaKlik();
    }

    private void srediViewModel() {
        opstaNapomenaViewModel = ViewModelProviders.of(this).get(OpstaNapomenaViewModel.class);
        opstaNapomenaViewModel.getAllOpsteNapomene().observe(this, new Observer<List<OpstaNapomena>>() {
            @Override
            public void onChanged(@Nullable List<OpstaNapomena> opsteNapomene) {
                adapter.submitList(opsteNapomene);
            }
        });
    }

    private void srediIzmeniNapomeneNaKlik() {
        adapter.setOnItemClickListener(new OpsteNapomeneAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(OpstaNapomena opstaNapomena) {
                Intent intent = new Intent(OpsteNapomeneActivity.this, Dodaj_IzmeniOpstuNapomenuActivity.class);
                intent.putExtra(Dodaj_IzmeniOpstuNapomenuActivity.EXTRA_ID, opstaNapomena.getOpstaNapomenaID());
                intent.putExtra(Dodaj_IzmeniOpstuNapomenuActivity.EXTRA_TIP_NAPOMENE, opstaNapomena.getTipOpsteNapomene());
                intent.putExtra(Dodaj_IzmeniOpstuNapomenuActivity.EXTRA_NAPOMENA, opstaNapomena.getOpstaNapomena());
                intent.putExtra(Dodaj_IzmeniOpstuNapomenuActivity.EXTRA_DATUM, opstaNapomena.getDatumNapomene());
                startActivityForResult(intent, IZMENI_NAPOMENU);

            }
        });
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
                opstaNapomenaViewModel.delete(adapter.getOpstaNapomenaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(OpsteNapomeneActivity.this, "Napomena je izbrisana", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }

    private void srediListenere() {
        btnDodajOpstuNapomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpsteNapomeneActivity.this, Dodaj_IzmeniOpstuNapomenuActivity.class);
                startActivityForResult(intent, DODAJ_NOVU_NAPOMENU);
            }
        });
    }

    private void srediAtribute() {
        btnDodajOpstuNapomenu = findViewById(R.id.btnDodajOpstuNapomenu);
        recyclerView = findViewById(R.id.rvOpstaNapomena);
        srediRecycleView();
    }

    private void srediRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DODAJ_NOVU_NAPOMENU && resultCode == RESULT_OK) {
            String tipNapomene = data.getStringExtra(Dodaj_IzmeniOpstuNapomenuActivity.EXTRA_TIP_NAPOMENE);
            String napomena = data.getStringExtra(Dodaj_IzmeniOpstuNapomenuActivity.EXTRA_NAPOMENA);
            String datum = data.getStringExtra(Dodaj_IzmeniOpstuNapomenuActivity.EXTRA_DATUM);

            OpstaNapomena opstaNapomena = new OpstaNapomena(tipNapomene, napomena, datum);
            opstaNapomenaViewModel.insert(opstaNapomena);

            Toast.makeText(this, "Napomena je sacuvana", Toast.LENGTH_SHORT).show();
        } else if (requestCode == IZMENI_NAPOMENU && resultCode == RESULT_OK) {
            int id = data.getIntExtra(Dodaj_IzmeniOpstuNapomenuActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Napomena ne moze biti izmenjena", Toast.LENGTH_SHORT).show();
                return;
            }

            String tipNapomene = data.getStringExtra(Dodaj_IzmeniOpstuNapomenuActivity.EXTRA_TIP_NAPOMENE);
            String napomena = data.getStringExtra(Dodaj_IzmeniOpstuNapomenuActivity.EXTRA_NAPOMENA);
            String datum = data.getStringExtra(Dodaj_IzmeniOpstuNapomenuActivity.EXTRA_DATUM);

            OpstaNapomena opstaNapomena = new OpstaNapomena(tipNapomene, napomena, datum);
            opstaNapomena.setOpstaNapomenaID(id);
            opstaNapomenaViewModel.update(opstaNapomena);

            Toast.makeText(this, "Opsta napomena je izmenjena", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Opsta napomena nije izmenjena", Toast.LENGTH_SHORT).show();
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
                opstaNapomenaViewModel.deleteAllOpsteNapomene();
                Toast.makeText(this, "Sve napomene su izbrisane", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}


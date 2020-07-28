package com.example.malipcelar.activity.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.malipcelar.activity.adapteri.LecenjeAdapter;
import com.example.malipcelar.activity.domen.Kosnica;
import com.example.malipcelar.activity.domen.Lecenje;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.viewModel.LecenjeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class LecenjeActivity extends AppCompatActivity {

    public static final String EXTRA_KOSNICA =
            "com.example.malipcelar.activity.activity.KOSNICA";
    public static final String EXTRA_PCELINJAK =
            "com.example.malipcelar.activity.activity.PCELINJAK";

    public static final int DODAJ_NOVO_LECENJE = 1;
    public static final int IZMENI_LECENJE = 2;

    Kosnica kosnica;
    Pcelinjak pcelinjak;
    List<Lecenje> lecenja;

    FloatingActionButton btnDodajLecenje;
    RecyclerView recyclerView;
    LecenjeViewModel lecenjeViewModel;

    final LecenjeAdapter adapter = new LecenjeAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecenje_activity);

        srediAtribute();
        srediListenere();
        srediViewModel();
        srediBrisanje();
    }

    private void srediAtribute() {
        Intent intent = getIntent();
        kosnica = (Kosnica) intent.getSerializableExtra(EXTRA_KOSNICA);
        pcelinjak = (Pcelinjak) intent.getSerializableExtra(EXTRA_PCELINJAK);
        recyclerView = findViewById(R.id.rvLecenja);

        btnDodajLecenje = findViewById(R.id.btnDodajLecenje);
        lecenja = null;
        srediRecycleView();
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

    private void srediRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void srediViewModel() {
        lecenjeViewModel = ViewModelProviders.of(this).get(LecenjeViewModel.class);
        lecenjeViewModel.getAllLecenjaZaKosnicu(kosnica.getRedniBrojKosnice(), pcelinjak.getRedniBrojPcelinjaka()).observe(this, new Observer<List<Lecenje>>() {
            @Override
            public void onChanged(@Nullable List<Lecenje> lecenja) {
                adapter.submitList(lecenja);
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
                lecenjeViewModel.delete(adapter.getLecenjeAt(viewHolder.getAdapterPosition()));
                Toast.makeText(LecenjeActivity.this, "Lecenje je izbrisano", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }

}
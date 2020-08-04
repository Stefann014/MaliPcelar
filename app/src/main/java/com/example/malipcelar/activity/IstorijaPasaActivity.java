package com.example.malipcelar.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.activity.LecenjeActivity;
import com.example.malipcelar.activity.adapteri.PasaAdapter;
import com.example.malipcelar.activity.domen.Pasa;
import com.example.malipcelar.activity.viewModel.PasaViewModel;

import java.util.List;

public class IstorijaPasaActivity extends AppCompatActivity {

    public static final int IZMENI_PASU = 2;

    RecyclerView recyclerView;
    PasaViewModel pasaViewModel;

    List<Pasa> pase;

    final PasaAdapter adapter = new PasaAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.istorija_pasa_activity);

        srediAtribute();
        srediViewModel();
        srediBrisanje();
    }

    private void srediAtribute() {
        recyclerView = findViewById(R.id.rvPase);
        srediRecycleView();
    }

    private void srediViewModel() {
        pasaViewModel = ViewModelProviders.of(this).get(PasaViewModel.class);
        srediObservere();

    }

    private void srediObservere() {
        pasaViewModel.getAllPase().observe(this, new Observer<List<Pasa>>() {
            @Override
            public void onChanged(@Nullable List<Pasa> pase) {
                adapter.submitList(pase);
            }
        });
    }

    private void srediRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
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
                pasaViewModel.delete(adapter.getPasaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(IstorijaPasaActivity.this, "Lecenje je izbrisano", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }

}
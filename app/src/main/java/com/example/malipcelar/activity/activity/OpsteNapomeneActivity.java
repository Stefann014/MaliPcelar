package com.example.malipcelar.activity.activity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.adapteri.OpsteNapomeneAdapter;
import com.example.malipcelar.activity.domen.OpstaNapomena;
import com.example.malipcelar.activity.pomocneKlase.NotifikacijaZaNapomenu;
import com.example.malipcelar.activity.viewModel.OpstaNapomenaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OpsteNapomeneActivity extends AppCompatActivity {
    public static final int DODAJ_NOVU_NAPOMENU = 1;
    public static final int IZMENI_NAPOMENU = 2;

    private OpstaNapomenaViewModel opstaNapomenaViewModel;
    FloatingActionButton btnDodajOpstuNapomenu;
    RecyclerView recyclerView;
    final OpsteNapomeneAdapter adapter = new OpsteNapomeneAdapter();

    List<OpstaNapomena> opsteNapomene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opste_napomene_activity);
        setTitle("Napomene");
        createNotificationChannel();
        srediAtribute();
        srediListenere();
        srediViewModel();
        srediBrisanje();
        srediIzmeniNapomeneNaKlik();
    }

    private void srediViewModel() {
        opstaNapomenaViewModel = ViewModelProviders.of(this).get(OpstaNapomenaViewModel.class);
        srediObserver();

    }

    private void srediObserver() {
        opstaNapomenaViewModel.getAllOpsteNapomene().observe(this, new Observer<List<OpstaNapomena>>() {
            @Override
            public void onChanged(@Nullable List<OpstaNapomena> opsteNapomene) {
                OpsteNapomeneActivity.this.opsteNapomene = opsteNapomene;
                assert OpsteNapomeneActivity.this.opsteNapomene != null;
                if (OpsteNapomeneActivity.this.opsteNapomene.size() == 0) {
                    poruka();
                }
                adapter.submitList(OpsteNapomeneActivity.this.opsteNapomene);
            }
        });
    }

    private void srediIzmeniNapomeneNaKlik() {
        adapter.setOnItemClickListener(new OpsteNapomeneAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(OpstaNapomena opstaNapomena) {

                String poruka = "Tip napomene: " + opstaNapomena.getTipOpsteNapomene() + "\n\n" + opstaNapomena.getOpstaNapomena() + "\n"
                        + "\n\n" + datumZaPrikaz(opstaNapomena.getDatumNapomene());

                prikaziPoruku("Napomena", poruka);
            }

            @Override
            public void onLongItemClick(OpstaNapomena opstaNapomena) {
                Intent intent = new Intent(OpsteNapomeneActivity.this, Dodaj_IzmeniOpstuNapomenuActivity.class);
                intent.putExtra(Dodaj_IzmeniOpstuNapomenuActivity.EXTRA_ID, opstaNapomena.getOpstaNapomenaID());
                intent.putExtra(Dodaj_IzmeniOpstuNapomenuActivity.EXTRA_TIP_NAPOMENE, opstaNapomena.getTipOpsteNapomene());
                intent.putExtra(Dodaj_IzmeniOpstuNapomenuActivity.EXTRA_NAPOMENA, opstaNapomena.getOpstaNapomena());
                intent.putExtra(Dodaj_IzmeniOpstuNapomenuActivity.EXTRA_DATUM, opstaNapomena.getDatumNapomene());
                startActivityForResult(intent, IZMENI_NAPOMENU);

            }
        });
    }

    private String datumZaPrikaz(String datum) {
        String[] datumi = datum.split("-");
        return datumi[2] + "." + datumi[1] + "." + datumi[0];
    }

    private void srediBrisanje() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
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
        opsteNapomene = null;
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


            assert datum != null;
            String[] datumi = datum.split("-");
            String dobarDatum = datumi[2] + "." + datumi[1] + "." + datumi[0] + ".";
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date date = null;
            try {
                date = sdf.parse(dobarDatum);
                assert date != null;
                Log.d("DATUM", date.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }


            OpstaNapomena opstaNapomena = new OpstaNapomena(tipNapomene, napomena, datum);
            opstaNapomenaViewModel.insert(opstaNapomena);


            Intent intent = new Intent(OpsteNapomeneActivity.this, NotifikacijaZaNapomenu.class);
            intent.putExtra("NAPOMENA", opstaNapomena.getOpstaNapomena());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(OpsteNapomeneActivity.this, 0, intent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            long dvanaestSati = 1000 * 60 * 60 * 12;
            Log.d("TEN", dvanaestSati + "");

            assert alarmManager != null;
            assert date != null;
            alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime() + dvanaestSati, pendingIntent);

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

            assert datum != null;
            String[] datumi = datum.split("-");
            String dobarDatum = datumi[2] + "." + datumi[1] + "." + datumi[0] + ".";
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date date = null;
            try {
                date = sdf.parse(dobarDatum);
                assert date != null;
                Log.d("DATUM", date.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            OpstaNapomena opstaNapomena = new OpstaNapomena(tipNapomene, napomena, datum);
            opstaNapomena.setOpstaNapomenaID(id);
            opstaNapomenaViewModel.update(opstaNapomena);

            Intent intent = new Intent(OpsteNapomeneActivity.this, NotifikacijaZaNapomenu.class);
            intent.putExtra("NAPOMENA", opstaNapomena.getOpstaNapomena());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(OpsteNapomeneActivity.this, 0, intent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            long dvanaestSati = 1000 * 60 * 60 * 12;
            Log.d("TEN", dvanaestSati + "");

            assert alarmManager != null;
            assert date != null;
            alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime() + dvanaestSati, pendingIntent);


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
        if (item.getItemId() == R.id.izbrisiSve) {
            opstaNapomenaViewModel.deleteAllOpsteNapomene();
            Toast.makeText(this, "Sve napomene su izbrisane", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void poruka() {
        String buffer = "Opšte napomene služe za unošenje napomena, koje treba da podsete pčelara na njegove obaveze.\n\n\tU slučaju da Vam se ništa ne prikazuje,\nto znači da nema unesenih stavki.";
        prikaziPoruku("Opšte napomene", buffer);

    }

    public void prikaziPoruku(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "LemubitReminderChannel";
            String description = "Channel for Leumvit reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyLemubit", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

    }

}


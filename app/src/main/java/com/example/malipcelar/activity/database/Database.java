package com.example.malipcelar.activity.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.malipcelar.activity.dao.OpsteNapomeneDAO;
import com.example.malipcelar.activity.dao.PcelinjakDAO;
import com.example.malipcelar.activity.domen.OpstaNapomena;
import com.example.malipcelar.activity.domen.Pcelinjak;

// moze se dodati vises entiteta u bazu!
// verzija se odnosi na bazu kada se menja , ona se uvek inkrementira


@androidx.room.Database(entities = {OpstaNapomena.class, Pcelinjak.class}, version = 1)
public abstract class Database extends RoomDatabase {

    private static Database instance;

    public abstract OpsteNapomeneDAO opsteNapomeneDAO();

    public abstract PcelinjakDAO pcelinjakDAO();

    //singleton pattern
    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Database.class, "database2")
                    .fallbackToDestructiveMigration() // da ne bi povecavali verziju, brisemo je ii instaliramo opet
                    .addCallback(roomCallback) // da popunimo necim bazu, ali samo prvi put kad se instancira singlton
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private OpsteNapomeneDAO napomenaDAO;
        private PcelinjakDAO pcelinjakDAO;

        private PopulateDbAsyncTask(Database db) {
            napomenaDAO = db.opsteNapomeneDAO();
            pcelinjakDAO = db.pcelinjakDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dodajOpsteNapomene(napomenaDAO);
            dodajPcelinjake(pcelinjakDAO);
            return null;
        }
    }

    private static void dodajOpsteNapomene(OpsteNapomeneDAO napomenaDAO) {
        napomenaDAO.insert(new OpstaNapomena("Opšte", "Napomena 1", "2026-02-02"));
        napomenaDAO.insert(new OpstaNapomena("Hitno", "Napomena 2", "2019-02-02"));
        napomenaDAO.insert(new OpstaNapomena("Opšte", "Napomena 3", "2026-07-02"));
        napomenaDAO.insert(new OpstaNapomena("Hitno", "Napomena 4", "2021-02-01"));
    }

    private static void dodajPcelinjake(PcelinjakDAO pcelinjakDAO) {
        pcelinjakDAO.insert(new Pcelinjak(1, "Pcelinjak", "45.23,22.46", "155", ""));
        pcelinjakDAO.insert(new Pcelinjak(2, "Pcelinjak", "47.24,22.49", "300", null));
        pcelinjakDAO.insert(new Pcelinjak(3, "Pcelinjak", "45.23,22.46", "400", null));
        pcelinjakDAO.insert(new Pcelinjak(4, "Pcelinjak", "45.23,22.46", "500", null));

    }

}

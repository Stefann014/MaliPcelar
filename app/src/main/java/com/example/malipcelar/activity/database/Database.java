package com.example.malipcelar.activity.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.malipcelar.activity.dao.KosnicaDAO;
import com.example.malipcelar.activity.dao.LecenjeDAO;
import com.example.malipcelar.activity.dao.OpsteNapomeneDAO;
import com.example.malipcelar.activity.dao.PasaDAO;
import com.example.malipcelar.activity.dao.PcelinjakDAO;
import com.example.malipcelar.activity.dao.PregledDAO;
import com.example.malipcelar.activity.dao.PrihranaDAO;
import com.example.malipcelar.activity.domen.Kosnica;
import com.example.malipcelar.activity.domen.Lecenje;
import com.example.malipcelar.activity.domen.OpstaNapomena;
import com.example.malipcelar.activity.domen.Pasa;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.domen.Pregled;
import com.example.malipcelar.activity.domen.Prihrana;

// moze se dodati vises entiteta u bazu!
// verzija se odnosi na bazu kada se menja , ona se uvek inkrementira


@androidx.room.Database(entities = {OpstaNapomena.class, Pcelinjak.class, Kosnica.class, Pregled.class, Lecenje.class, Prihrana.class, Pasa.class}, version = 1)
public abstract class Database extends RoomDatabase {

    private static Database instance;

    public abstract OpsteNapomeneDAO opsteNapomeneDAO();

    public abstract PcelinjakDAO pcelinjakDAO();

    public abstract KosnicaDAO kosnicaDAO();

    public abstract PregledDAO pregledDAO();

    public abstract LecenjeDAO lecenjeDAO();

    public abstract PrihranaDAO prihranaDAO();

    public abstract PasaDAO pasaDAO();

    //singleton pattern
    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Database.class, "database4") // menjaj naziv kad izmenis nesto
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
        private KosnicaDAO kosnicaDAO;
        private PregledDAO pregledDAO;
        private LecenjeDAO lecenjeDAO;
        private PrihranaDAO prihranaDAO;
        private PasaDAO pasaDAO;

        private PopulateDbAsyncTask(Database db) {
            napomenaDAO = db.opsteNapomeneDAO();
            pcelinjakDAO = db.pcelinjakDAO();
            kosnicaDAO = db.kosnicaDAO();
            pregledDAO = db.pregledDAO();
            lecenjeDAO = db.lecenjeDAO();
            prihranaDAO = db.prihranaDAO();
            pasaDAO = db.pasaDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dodajOpsteNapomene(napomenaDAO);
            dodajPcelinjake(pcelinjakDAO);
            dodajKosnice(kosnicaDAO);
            dodajPreglede(pregledDAO);
            dodajLecenje(lecenjeDAO);
            dodajPrihrane(prihranaDAO);
            dodajPase(pasaDAO);
            return null;
        }


    }

    private static void dodajOpsteNapomene(OpsteNapomeneDAO napomenaDAO) {
        /*napomenaDAO.insert(new OpstaNapomena("Opšte", "Napomena 1", "2026-02-02"));
        napomenaDAO.insert(new OpstaNapomena("Hitno", "Napomena 2", "2019-02-02"));
        napomenaDAO.insert(new OpstaNapomena("Opšte", "Napomena 3", "2026-07-02"));
        napomenaDAO.insert(new OpstaNapomena("Hitno", "Napomena 4", "2021-02-01"));
   */
    }

    private static void dodajPcelinjake(PcelinjakDAO pcelinjakDAO) {
     /*   pcelinjakDAO.insert(new Pcelinjak(1, "Pcelinjak", "45.23,22.46", "155", ""));
        pcelinjakDAO.insert(new Pcelinjak(2, "Pcelinjak", "47.24,22.49", "300", null));
        pcelinjakDAO.insert(new Pcelinjak(3, "Pcelinjak", "45.23,22.46", "400", null));
        pcelinjakDAO.insert(new Pcelinjak(4, "Pcelinjak", "45.23,22.46", "500", null));
   */
    }

    private static void dodajKosnice(KosnicaDAO kosnicaDAO) {
       /* kosnicaDAO.insert(new Kosnica(1, 2, "Kosnica 1.", "2010", true, true, "", ""));
        kosnicaDAO.insert(new Kosnica(2, 2, "Kosnica 12.", "2012", true, true, "", ""));
        kosnicaDAO.insert(new Kosnica(3, 2, "Kosnica 122.", "2013", true, true, "", ""));
        kosnicaDAO.insert(new Kosnica(3, 1, "Kosnica 13.", "2014", true, true, "", ""));
    */
    }

    private static void dodajPreglede(PregledDAO pregledDAO) {
        //pregledDAO.insert(new Pregled("2020-05-05", 1,1, true, false, true, true, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ""));
    }

    private static void dodajLecenje(LecenjeDAO lecenjeDAO) {
    }

    private static void dodajPrihrane(PrihranaDAO prihranaDAO) {
    }

    private static void dodajPase(PasaDAO pasaDAO) {
    }
}

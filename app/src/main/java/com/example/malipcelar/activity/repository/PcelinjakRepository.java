package com.example.malipcelar.activity.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.malipcelar.activity.dao.PcelinjakDAO;
import com.example.malipcelar.activity.database.Database;
import com.example.malipcelar.activity.domen.Pcelinjak;
import com.example.malipcelar.activity.pomocneKlase.PcelinjakIDatumi;

import java.util.List;

public class PcelinjakRepository {

    private PcelinjakDAO pcelinjakDAO;
    private LiveData<List<Pcelinjak>> allPcelinjaci;
    private LiveData<List<Integer>> zauzetiRBovi;


    public PcelinjakRepository(Application application) {
        Database database = Database.getInstance(application);
        pcelinjakDAO = database.pcelinjakDAO();
        allPcelinjaci = pcelinjakDAO.getAllPcelinjaci();
        zauzetiRBovi = pcelinjakDAO.getAllPcelinjakRB();
    }

    // za svaku operaciju je neophodna posebna asinhrona u pozadini

    public void insert(Pcelinjak pcelinjak) {
        new InsertPcelinjakAsyncTask(pcelinjakDAO).execute(pcelinjak);
    }

    public void update(Pcelinjak pcelinjak) {
        new UpdatePcelinjakAsyncTask(pcelinjakDAO).execute(pcelinjak);
    }

    public void updateRb(int stariRb, int noviRb) {
        new UpdatePcelinjakRbAsyncTask(pcelinjakDAO, stariRb, noviRb).execute();
    }

    public void delete(Pcelinjak pcelinjak) {
        new DeletePcelinjakAsyncTask(pcelinjakDAO).execute(pcelinjak);
    }

    public void deleteAllPcelinjaci() {
        new DeleteAllPcelinjakAsyncTask(pcelinjakDAO).execute();
    }

    ////////////////////// livedata
    public LiveData<List<Pcelinjak>> getAllPcelinjaci() {
        return allPcelinjaci;
    }

    public LiveData<Pcelinjak> getPcelinjakByID(int id) {
        return pcelinjakDAO.getPcelinjakByRB(id);
    }

    public LiveData<List<PcelinjakIDatumi>> getPcelinjakIDatumi() {
        return pcelinjakDAO.getPcelinjakIDatumi();
    }

    public LiveData<List<Integer>> getAllPcelinjakRB() {
        return zauzetiRBovi;
    }

    ///////asinhroni
    private static class InsertPcelinjakAsyncTask extends AsyncTask<Pcelinjak, Void, Void> {
        private PcelinjakDAO pcelinjakDAO;

        private InsertPcelinjakAsyncTask(PcelinjakDAO pcelinjakDAO) {
            this.pcelinjakDAO = pcelinjakDAO;
        }

        @Override
        protected Void doInBackground(Pcelinjak... pcelinjaci) {
            pcelinjakDAO.insert(pcelinjaci[0]);
            return null;
        }
    }

    private static class UpdatePcelinjakAsyncTask extends AsyncTask<Pcelinjak, Void, Void> {
        private PcelinjakDAO pcelinjakDAO;

        private UpdatePcelinjakAsyncTask(PcelinjakDAO pcelinjakDAO) {
            this.pcelinjakDAO = pcelinjakDAO;
        }

        @Override
        protected Void doInBackground(Pcelinjak... pcelinjaci) {
            pcelinjakDAO.update(pcelinjaci[0]);
            return null;
        }
    }

    private static class DeletePcelinjakAsyncTask extends AsyncTask<Pcelinjak, Void, Void> {
        private PcelinjakDAO pcelinjakDAO;

        private DeletePcelinjakAsyncTask(PcelinjakDAO pcelinjakDAO) {
            this.pcelinjakDAO = pcelinjakDAO;
        }

        @Override
        protected Void doInBackground(Pcelinjak... pcelinjaci) {
            pcelinjakDAO.delete(pcelinjaci[0]);
            return null;
        }
    }

    private static class DeleteAllPcelinjakAsyncTask extends AsyncTask<Void, Void, Void> {
        private PcelinjakDAO pcelinjakDAO;

        private DeleteAllPcelinjakAsyncTask(PcelinjakDAO pcelinjakDAO) {
            this.pcelinjakDAO = pcelinjakDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            pcelinjakDAO.deleteAllPcelinjaci();
            return null;
        }
    }

    private static class UpdatePcelinjakRbAsyncTask extends AsyncTask<Pcelinjak, Void, Void> {

        private PcelinjakDAO pcelinjakDAO;
        private int stariRb;
        private int noviRb;

        private UpdatePcelinjakRbAsyncTask(PcelinjakDAO pcelinjakDAO, int stariRb, int noviRb) {
            this.pcelinjakDAO = pcelinjakDAO;
            this.stariRb = stariRb;
            this.noviRb = noviRb;
        }

        @Override
        protected Void doInBackground(Pcelinjak... pcelinjaci) {
            pcelinjakDAO.updateRb(stariRb, noviRb);
            return null;
        }

    }
}

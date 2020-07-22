package com.example.malipcelar.activity.repozitorijum;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.malipcelar.activity.dao.KosnicaDAO;
import com.example.malipcelar.activity.database.Database;
import com.example.malipcelar.activity.domen.Kosnica;

import java.util.List;

public class KosnicaRepository {

    private KosnicaDAO kosnicaDAO;

    public KosnicaRepository(Application application) {
        Database database = Database.getInstance(application);
        kosnicaDAO = database.kosnicaDAO();
    }

    public LiveData<List<Kosnica>> getKosniceByRbPcelinjaka(int rb_pcelinjak) {
        return kosnicaDAO.getAllKosnice(rb_pcelinjak);
    }

    public LiveData<Kosnica> getKosnicaByRBKosnice(int rb_kosnice, int pcelinjak) {
        return kosnicaDAO.getKosnicaByRB(rb_kosnice, pcelinjak);
    }

    //asinh

    public void insert(Kosnica kosnica) {
        new KosnicaRepository.InsertKosnicaAsyncTask(kosnicaDAO).execute(kosnica);
    }

    public void update(Kosnica kosnica) {
        new KosnicaRepository.UpdateKosnicaAsyncTask(kosnicaDAO).execute(kosnica);
    }

    public void updateRb(int stariRb, int noviRb) {
        new KosnicaRepository.UpdateKosnicaRbAsyncTask(kosnicaDAO, stariRb, noviRb).execute();
    }

    public void delete(Kosnica kosnica) {
        new KosnicaRepository.DeleteKosnicaAsyncTask(kosnicaDAO).execute(kosnica);
    }

    public void deleteAllKosnice() {
        new KosnicaRepository.DeleteAllKosnicaAsyncTask(kosnicaDAO).execute();
    }


    private static class InsertKosnicaAsyncTask extends AsyncTask<Kosnica, Void, Void> {
        private KosnicaDAO kosnicaDAO;

        private InsertKosnicaAsyncTask(KosnicaDAO kosnicaDAO) {
            this.kosnicaDAO = kosnicaDAO;
        }

        @Override
        protected Void doInBackground(Kosnica... kosnice) {
            kosnicaDAO.insert(kosnice[0]);
            return null;
        }
    }

    private static class UpdateKosnicaAsyncTask extends AsyncTask<Kosnica, Void, Void> {
        private KosnicaDAO kosnicaDAO;

        private UpdateKosnicaAsyncTask(KosnicaDAO kosnicaDAO) {
            this.kosnicaDAO = kosnicaDAO;
        }

        @Override
        protected Void doInBackground(Kosnica... kosnice) {
            kosnicaDAO.update(kosnice[0]);
            return null;
        }
    }

    private static class DeleteKosnicaAsyncTask extends AsyncTask<Kosnica, Void, Void> {
        private KosnicaDAO kosnicaDAO;

        private DeleteKosnicaAsyncTask(KosnicaDAO kosnicaDAO) {
            this.kosnicaDAO = kosnicaDAO;
        }

        @Override
        protected Void doInBackground(Kosnica... kosnice) {
            kosnicaDAO.delete(kosnice[0]);
            return null;
        }
    }

    private static class DeleteAllKosnicaAsyncTask extends AsyncTask<Void, Void, Void> {
        private KosnicaDAO kosnicaDAO;

        private DeleteAllKosnicaAsyncTask(KosnicaDAO kosnicaDAO) {
            this.kosnicaDAO = kosnicaDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            kosnicaDAO.deleteAllKosnice();
            return null;
        }
    }

    private static class UpdateKosnicaRbAsyncTask extends AsyncTask<Kosnica, Void, Void> {

        private KosnicaDAO kosnicaDAO;
        private int stariRb;
        private int noviRb;

        private UpdateKosnicaRbAsyncTask(KosnicaDAO kosnicaDAO, int stariRb, int noviRb) {
            this.kosnicaDAO = kosnicaDAO;
            this.stariRb = stariRb;
            this.noviRb = noviRb;
        }

        @Override
        protected Void doInBackground(Kosnica... kosnice) {
            kosnicaDAO.updateRb(stariRb, noviRb);
            return null;
        }
    }
}
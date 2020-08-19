package com.example.malipcelar.activity.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.malipcelar.activity.dao.KosnicaDAO;
import com.example.malipcelar.activity.database.Database;
import com.example.malipcelar.activity.domen.Kosnica;
import com.example.malipcelar.activity.pomocneKlase.KosnicaIDatumi;

import java.util.List;

public class KosnicaRepository {

    private KosnicaDAO kosnicaDAO;

    public KosnicaRepository(Application application) {
        Database database = Database.getInstance(application);
        kosnicaDAO = database.kosnicaDAO();
    }

    public LiveData<List<Kosnica>> getAllKosniceByRbPcelinjaka(int rb_pcelinjak) {
        return kosnicaDAO.getAllKosniceByRbPcelinjaka(rb_pcelinjak);
    }

    public LiveData<List<KosnicaIDatumi>> getAllKosniceIDatumeZaPcelinjak(int pcelinjak) {
        return kosnicaDAO.getAllKosniceIDatumeZaPcelinjak(pcelinjak);
    }

    public LiveData<List<Integer>> getAllRBKosniceZaPcelinjak(int pcelinjak) {
        return kosnicaDAO.getAllRBKosniceZaPcelinjak(pcelinjak);
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
package com.example.malipcelar.activity.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.malipcelar.activity.dao.PrihranaDAO;
import com.example.malipcelar.activity.database.Database;
import com.example.malipcelar.activity.domen.Prihrana;

import java.util.List;

public class PrihranaRepository {

    private PrihranaDAO prihranaDAO;

    public PrihranaRepository(Application application) {
        Database database = Database.getInstance(application);
        prihranaDAO = database.prihranaDAO();
    }

    public LiveData<List<Prihrana>> getAllPrihranaZaKosnicu(int kosnicaID, int pcelinjakID) {
        return prihranaDAO.getAllPrihranaZaKosnicu(kosnicaID, pcelinjakID);
    }

    public LiveData<String> getMaxDatumPrihranaZaKosnicu(int kosnicaID, int pcelinjakID) {
        return prihranaDAO.getMaxDatumPrihranaZaKosnicu(kosnicaID, pcelinjakID);
    }

    //
    public void insert(Prihrana prihrana) {
        new PrihranaRepository.InsertPrihranaAsyncTask(prihranaDAO).execute(prihrana);
    }

    public void update(Prihrana prihrana) {
        new PrihranaRepository.UpdatePrihranaAsyncTask(prihranaDAO).execute(prihrana);
    }

    public void delete(Prihrana prihrana) {
        new PrihranaRepository.DeletePrihranaAsyncTask(prihranaDAO).execute(prihrana);
    }

/////////////////// da ne bi pukla aplikacija ovo moramo raditi u pozadini, livedata je automatski sinhronizovan

    private static class InsertPrihranaAsyncTask extends AsyncTask<Prihrana, Void, Void> {
        private PrihranaDAO prihranaDAO;

        private InsertPrihranaAsyncTask(PrihranaDAO prihranaDAO) {
            this.prihranaDAO = prihranaDAO;
        }

        @Override
        protected Void doInBackground(Prihrana... prihrane) {
            prihranaDAO.insert(prihrane[0]);
            return null;
        }
    }

    private static class UpdatePrihranaAsyncTask extends AsyncTask<Prihrana, Void, Void> {
        private PrihranaDAO prihranaDAO;

        private UpdatePrihranaAsyncTask(PrihranaDAO prihranaDAO) {
            this.prihranaDAO = prihranaDAO;
        }

        @Override
        protected Void doInBackground(Prihrana... prihrane) {
            prihranaDAO.update(prihrane[0]);
            return null;
        }
    }

    private static class DeletePrihranaAsyncTask extends AsyncTask<Prihrana, Void, Void> {
        private PrihranaDAO prihranaDAO;

        private DeletePrihranaAsyncTask(PrihranaDAO prihranaDAO) {
            this.prihranaDAO = prihranaDAO;
        }

        @Override
        protected Void doInBackground(Prihrana... prihrane) {
            prihranaDAO.delete(prihrane[0]);
            return null;
        }
    }
}

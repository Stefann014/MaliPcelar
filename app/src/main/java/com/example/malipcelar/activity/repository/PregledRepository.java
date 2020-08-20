package com.example.malipcelar.activity.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.malipcelar.activity.dao.PregledDAO;
import com.example.malipcelar.activity.database.Database;
import com.example.malipcelar.activity.domen.Pregled;

import java.util.List;

public class PregledRepository {

    private PregledDAO pregledDAO;

    public PregledRepository(Application application) {
        Database database = Database.getInstance(application);
        pregledDAO = database.pregledDAO();
    }

    public LiveData<List<Pregled>> getAllPreglediZaKosnicu(int kosnicaID, int pcelinjakID) {
        return pregledDAO.getAllPreglediZaKosnicu(kosnicaID, pcelinjakID);
    }

    public void insert(Pregled pregled) {
        new InsertPregledAsyncTask(pregledDAO).execute(pregled);
    }

    public void update(Pregled pregled) {
        new UpdatePregledAsyncTask(pregledDAO).execute(pregled);
    }

    public void delete(Pregled pregled) {
        new DeletePregledAsyncTask(pregledDAO).execute(pregled);
    }

/////////////////// da ne bi pukla aplikacija ovo moramo raditi u pozadini, livedata je automatski sinhronizovan

    private static class InsertPregledAsyncTask extends AsyncTask<Pregled, Void, Void> {
        private PregledDAO pregledDAO;

        private InsertPregledAsyncTask(PregledDAO pregledDAO) {
            this.pregledDAO = pregledDAO;
        }

        @Override
        protected Void doInBackground(@NonNull Pregled... pregledi) {
            pregledDAO.insert(pregledi[0]);
            return null;
        }
    }

    private static class UpdatePregledAsyncTask extends AsyncTask<Pregled, Void, Void> {
        private PregledDAO pregledDAO;

        private UpdatePregledAsyncTask(PregledDAO pregledDAO) {
            this.pregledDAO = pregledDAO;
        }

        @Override
        protected Void doInBackground(@NonNull Pregled... pregledi) {
            pregledDAO.update(pregledi[0]);
            return null;
        }
    }

    private static class DeletePregledAsyncTask extends AsyncTask<Pregled, Void, Void> {
        private PregledDAO pregledDAO;

        private DeletePregledAsyncTask(PregledDAO pregledDAO) {
            this.pregledDAO = pregledDAO;
        }

        @Override
        protected Void doInBackground(@NonNull Pregled... pregledi) {
            pregledDAO.delete(pregledi[0]);
            return null;
        }
    }
}

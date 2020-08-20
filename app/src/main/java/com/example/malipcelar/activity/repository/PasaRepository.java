package com.example.malipcelar.activity.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.malipcelar.activity.dao.PasaDAO;
import com.example.malipcelar.activity.database.Database;
import com.example.malipcelar.activity.domen.Pasa;

import java.util.List;

public class PasaRepository {

    private PasaDAO pasaDAO;

    public PasaRepository(Application application) {
        Database database = Database.getInstance(application);
        pasaDAO = database.pasaDAO();
    }

    public LiveData<List<Pasa>> getAllPase() {
        return pasaDAO.getAllPase();
    }

    public void insert(Pasa pasa) {
        new InsertPaseAsyncTask(pasaDAO).execute(pasa);
    }

    public void update(Pasa pasa) {
        new UpdatePaseAsyncTask(pasaDAO).execute(pasa);
    }

    public void delete(Pasa pasa) {
        new DeletePaseAsyncTask(pasaDAO).execute(pasa);
    }

/////////////////// da ne bi pukla aplikacija ovo moramo raditi u pozadini, livedata je automatski sinhronizovan

    private static class InsertPaseAsyncTask extends AsyncTask<Pasa, Void, Void> {
        private PasaDAO pasaDAO;

        private InsertPaseAsyncTask(PasaDAO pasaDAO) {
            this.pasaDAO = pasaDAO;
        }

        @Override
        protected Void doInBackground(@NonNull Pasa... pase) {
            pasaDAO.insert(pase[0]);
            return null;
        }
    }

    private static class UpdatePaseAsyncTask extends AsyncTask<Pasa, Void, Void> {
        private PasaDAO pasaDAO;

        private UpdatePaseAsyncTask(PasaDAO pasaDAO) {
            this.pasaDAO = pasaDAO;
        }

        @Override
        protected Void doInBackground(@NonNull Pasa... pase) {
            pasaDAO.update(pase[0]);
            return null;
        }
    }

    private static class DeletePaseAsyncTask extends AsyncTask<Pasa, Void, Void> {
        private PasaDAO pasaDAO;

        private DeletePaseAsyncTask(PasaDAO pasaDAO) {
            this.pasaDAO = pasaDAO;
        }

        @Override
        protected Void doInBackground(@NonNull Pasa... pase) {
            pasaDAO.delete(pase[0]);
            return null;
        }
    }
}

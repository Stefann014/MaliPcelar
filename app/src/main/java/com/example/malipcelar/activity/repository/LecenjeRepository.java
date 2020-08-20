package com.example.malipcelar.activity.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.malipcelar.activity.dao.LecenjeDAO;
import com.example.malipcelar.activity.database.Database;
import com.example.malipcelar.activity.domen.Lecenje;

import java.util.List;

public class LecenjeRepository {

    private LecenjeDAO lecenjeDAO;

    public LecenjeRepository(Application application) {
        Database database = Database.getInstance(application);
        lecenjeDAO = database.lecenjeDAO();
    }

    public LiveData<List<Lecenje>> getAllLecenjaZaKosnicu(int kosnicaID, int pcelinjakID) {
        return lecenjeDAO.getAllLecenjaZaKosnicu(kosnicaID, pcelinjakID);
    }

    public void insert(Lecenje lecenje) {
        new LecenjeRepository.InsertLecenjeAsyncTask(lecenjeDAO).execute(lecenje);
    }

    public void update(Lecenje lecenje) {
        new LecenjeRepository.UpdateLecenjeAsyncTask(lecenjeDAO).execute(lecenje);
    }

    public void delete(Lecenje lecenje) {
        new LecenjeRepository.DeleteLecenjeAsyncTask(lecenjeDAO).execute(lecenje);
    }

/////////////////// da ne bi pukla aplikacija ovo moramo raditi u pozadini, livedata je automatski sinhronizovan

    private static class InsertLecenjeAsyncTask extends AsyncTask<Lecenje, Void, Void> {
        private LecenjeDAO lecenjeDAO;

        private InsertLecenjeAsyncTask(LecenjeDAO lecenjeDAO) {
            this.lecenjeDAO = lecenjeDAO;
        }

        @Override
        protected Void doInBackground(@NonNull Lecenje... lecenja) {
            lecenjeDAO.insert(lecenja[0]);
            return null;
        }
    }

    private static class UpdateLecenjeAsyncTask extends AsyncTask<Lecenje, Void, Void> {
        private LecenjeDAO lecenjeDAO;

        private UpdateLecenjeAsyncTask(LecenjeDAO lecenjeDAO) {
            this.lecenjeDAO = lecenjeDAO;
        }

        @Override
        protected Void doInBackground(@NonNull Lecenje... lecenja) {
            lecenjeDAO.update(lecenja[0]);
            return null;
        }
    }

    private static class DeleteLecenjeAsyncTask extends AsyncTask<Lecenje, Void, Void> {
        private LecenjeDAO lecenjeDAO;

        private DeleteLecenjeAsyncTask(LecenjeDAO lecenjeDAO) {
            this.lecenjeDAO = lecenjeDAO;
        }

        @Override
        protected Void doInBackground(@NonNull Lecenje... lecenja) {
            lecenjeDAO.delete(lecenja[0]);
            return null;
        }
    }
}

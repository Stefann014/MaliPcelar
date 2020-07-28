package com.example.malipcelar.activity.repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

import com.example.malipcelar.activity.dao.OpsteNapomeneDAO;
import com.example.malipcelar.activity.database.Database;
import com.example.malipcelar.activity.domen.OpstaNapomena;
import java.util.List;


public class OpstaNapomenaRepository {


    private OpsteNapomeneDAO opstaNapomenaDAO;
    private LiveData<List<OpstaNapomena>> allOpsteNapomene;


    public OpstaNapomenaRepository(Application application) {
        Database database = Database.getInstance(application);
        opstaNapomenaDAO = database.opsteNapomeneDAO();
        allOpsteNapomene = opstaNapomenaDAO.getAllOpsteNapomene();
    }

    // za svaku operaciju je neophodna posebna asinhrona u pozadini

    public void insert(OpstaNapomena opstaNapomena) {
        new InsertOpstaNapomenaAsyncTask(opstaNapomenaDAO).execute(opstaNapomena);
    }

    public void update(OpstaNapomena opstaNapomena) {
        new UpdateOpstaNapomenaAsyncTask(opstaNapomenaDAO).execute(opstaNapomena);
    }

    public void delete(OpstaNapomena opstaNapomena) {
        new DeleteOpstaNapomenaAsyncTask(opstaNapomenaDAO).execute(opstaNapomena);
    }

    public void deleteAllOpsteNapomene() {
        new DeleteAllOpstaNapomenaAsyncTask(opstaNapomenaDAO).execute();
    }

    public LiveData<List<OpstaNapomena>> getAllOpsteNapomene() {
        return allOpsteNapomene;
    }


/////////////////// da ne bi pukla aplikacija ovo moramo raditi u pozadini, livedata je automatski sinhronizovan

    private static class InsertOpstaNapomenaAsyncTask extends AsyncTask<OpstaNapomena, Void, Void> {
        private OpsteNapomeneDAO opsteNapomeneDAO;

        private InsertOpstaNapomenaAsyncTask(OpsteNapomeneDAO opsteNapomeneDAO) {
            this.opsteNapomeneDAO = opsteNapomeneDAO;
        }

        @Override
        protected Void doInBackground(OpstaNapomena... opsteNapomene) {
            opsteNapomeneDAO.insert(opsteNapomene[0]);
            return null;
        }
    }

    private static class UpdateOpstaNapomenaAsyncTask extends AsyncTask<OpstaNapomena, Void, Void> {
        private OpsteNapomeneDAO opsteNapomeneDAO;

        private UpdateOpstaNapomenaAsyncTask(OpsteNapomeneDAO opsteNapomeneDAO) {
            this.opsteNapomeneDAO = opsteNapomeneDAO;
        }

        @Override
        protected Void doInBackground(OpstaNapomena... opsteNapomene) {
            opsteNapomeneDAO.update(opsteNapomene[0]);
            return null;
        }
    }

    private static class DeleteOpstaNapomenaAsyncTask extends AsyncTask<OpstaNapomena, Void, Void> {
        private OpsteNapomeneDAO opsteNapomeneDAO;

        private DeleteOpstaNapomenaAsyncTask(OpsteNapomeneDAO opsteNapomeneDAO) {
            this.opsteNapomeneDAO = opsteNapomeneDAO;
        }

        @Override
        protected Void doInBackground(OpstaNapomena... opsteNapomene) {
            opsteNapomeneDAO.delete(opsteNapomene[0]);
            return null;
        }
    }

    private static class DeleteAllOpstaNapomenaAsyncTask extends AsyncTask<Void, Void, Void> {
        private OpsteNapomeneDAO opsteNapomeneDAO;

        private DeleteAllOpstaNapomenaAsyncTask(OpsteNapomeneDAO opsteNapomeneDAO) {
            this.opsteNapomeneDAO = opsteNapomeneDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            opsteNapomeneDAO.deleteAllOpsteNapomene();
            return null;
        }
    }
}

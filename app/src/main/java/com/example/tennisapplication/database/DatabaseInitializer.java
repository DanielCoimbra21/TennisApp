package com.example.tennisapplication.database;


import android.os.AsyncTask;
import android.util.Log;

import com.example.tennisapplication.database.entity.PlayerEntity;


public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addPlayer(final AppDatabase db, final String email, final String password, final String firstName,
                                  final String lastName, final String  age, final String phoneNumber, final String status, final int nbReservation, final int image) {
        PlayerEntity player = new PlayerEntity(email, password,firstName, lastName, age, phoneNumber, status, nbReservation, image);
        db.playerDao().insert(player);
    }

    private static void populateWithTestData(AppDatabase db) {
        db.playerDao().deleteAll();

        addPlayer(db, "michel.platini@fifa.com", "123","Michel", "Platini", "35", "91000000", "player", 0,0);
        addPlayer(db, "admin@admin.com", "1234","Admin", "Admin", "35", "91000000", "player", 0,0);

    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(database);
            return null;
        }

    }
}



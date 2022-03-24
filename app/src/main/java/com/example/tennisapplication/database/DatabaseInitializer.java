package com.example.tennisapplication.database;


import android.os.AsyncTask;
import android.util.Log;

import com.example.tennisapplication.database.entity.CourtEntity;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.database.entity.ReservationEntity;

import java.sql.Date;
import java.sql.Time;


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

    private static void addReservation(final AppDatabase db, final String schedule, final String date, final String playerId,final int courtNumber){
        ReservationEntity reservation = new ReservationEntity(schedule, date, playerId, courtNumber);
        db.reservationDao().insert(reservation);

    }

    private static void addCourt(final AppDatabase db, final int idCourt,final boolean openAir, final boolean reserved){
        CourtEntity court = new CourtEntity(idCourt, openAir, reserved);
        db.courtDao().insert(court);
    }

    private static void populateWithTestData(AppDatabase db) {
        db.playerDao().deleteAll();

        addPlayer(db, "michel.platini@fifa.com", "123","Michel", "Platini", "35", "91000000", "player", 0,0);
        addPlayer(db, "admin@admin.com", "1234","Admin", "Admin", "35", "91000000", "player", 0,0);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        addCourt(db, 1,true, true);
        addCourt(db, 2,false, false);


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        addReservation(db, "10.00.00", "30.03.2022", "admin@admin.com", 1);
        addReservation(db, "09.00.00", "31.03.2022", "admin@admin.com", 1);


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



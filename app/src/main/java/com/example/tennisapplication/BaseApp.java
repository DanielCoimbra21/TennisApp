package com.example.tennisapplication;

import android.app.Application;

import com.example.tennisapplication.database.AppDatabase;
import com.example.tennisapplication.database.repository.CourtRepository;
import com.example.tennisapplication.database.repository.PlayerRepository;
import com.example.tennisapplication.database.repository.ReservationRepository;

public class BaseApp extends Application {

    public static final String PREFS_NAME = "SharedPrefs";
    public static final String PREFS_USER = "LoggedIn";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDatabase(){
        return AppDatabase.getInstance(this);
    }

    public PlayerRepository getPlayerRepository() {
        return PlayerRepository.getInstance();
    }

    public CourtRepository getCourtRepository(){
        return CourtRepository.getInstance();
    }

    public ReservationRepository getReservationRepository(){
        return ReservationRepository.getInstance();
    }
}

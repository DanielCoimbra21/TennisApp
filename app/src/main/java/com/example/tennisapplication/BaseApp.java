package com.example.tennisapplication;

import android.app.Application;

import com.example.tennisapplication.database.repository.CourtRepository;
import com.example.tennisapplication.database.repository.PlayerRepository;
import com.example.tennisapplication.database.repository.ReservationRepository;

public class BaseApp extends Application {

    /*********************************************************************************************
     *                     Base App de notre application smartphone
     *
     *
     *          Lien vers le GitHub : https://github.com/DanielCoimbra21/TennisApp
     * ******************************************************************************************/

    public CourtRepository getCourtRepository() {
        return CourtRepository.getInstance();
    }

    public PlayerRepository getPlayerRepository() {
        return PlayerRepository.getInstance();
    }

    public ReservationRepository getReservationRepository() {
        return ReservationRepository.getInstance();
    }
}

package com.example.tennisapplication.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.database.async.player.CreatePlayer;
import com.example.tennisapplication.database.async.player.DeletePlayer;
import com.example.tennisapplication.database.async.player.UpdatePlayer;
import com.example.tennisapplication.database.async.reservation.CreateReservation;
import com.example.tennisapplication.database.async.reservation.DeleteReservation;
import com.example.tennisapplication.database.async.reservation.UpdateReservation;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.database.entity.ReservationEntity;
import com.example.tennisapplication.util.OnAsyncEventListener;

import java.util.List;

public class ReservationRepository {

    private static ReservationRepository instance;

    private ReservationRepository(){}

    public static ReservationRepository getInstance() {
        if (instance == null){
            synchronized (ReservationRepository.class) {
                if (instance == null){
                    instance = new ReservationRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<ReservationEntity> getReservation(final int reservationId, Application application){
        return ((BaseApp)application).getDatabase().reservationDao().getByReservationId(reservationId);
    }

    public LiveData<List<ReservationEntity>> getByPlayerEmail(final String playerEmail, Application application){
        return ((BaseApp)application).getDatabase().reservationDao().getByPlayerEmail(playerEmail);
    }

    public LiveData<List<ReservationEntity>> getAllReservation(Application application){
        return ((BaseApp)application).getDatabase().reservationDao().getAllReservation();
    }

    public LiveData<List<ReservationEntity>> getNotAvailableCourts(String schedule, String date,Application application){
        return ((BaseApp)application).getDatabase().reservationDao().getNotAvailableCourt(schedule,date);
    }

    public boolean getUnavailableCourtTime(final String schedule, final String date, Application application){
        return ((BaseApp)application).getDatabase().reservationDao().getUnavailableCourtTime(schedule, date);
    }

    public boolean getUnavailableCourts(final String schedule, final String date, final int courtNumber,Application application){
        return ((BaseApp)application).getDatabase().reservationDao().getUnavailableCourts(schedule, date, courtNumber);
    }

    public void insert(final ReservationEntity reservationEntity, OnAsyncEventListener callback, Application application){
        new CreateReservation(application, callback).execute(reservationEntity);
    }

    public void update(final ReservationEntity reservationEntity, OnAsyncEventListener callback, Application application){
        new UpdateReservation(application, callback).execute(reservationEntity);
    }

    public void delete(final ReservationEntity reservationEntity, OnAsyncEventListener callback, Application application){
        new DeleteReservation(application, callback).execute(reservationEntity);
    }

}

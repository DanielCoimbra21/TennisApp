package com.example.tennisapplication.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.database.entity.ReservationEntity;
import com.example.tennisapplication.database.firebase.ReservationListLiveData;
import com.example.tennisapplication.database.firebase.ReservationLiveData;
import com.example.tennisapplication.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ReservationRepository {

    private static ReservationRepository instance;

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

    public LiveData<ReservationEntity> getReservation(final String reservationId){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("players")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("reservations")
                .child(reservationId);
        return new ReservationLiveData(reference);
    }

    public LiveData<List<ReservationEntity>> getByPlayerEmail(final String playerEmail){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("players")
                .child(playerEmail)
                .child("reservations");
        return new ReservationListLiveData(reference, playerEmail);    }


    /*public LiveData<List<ReservationEntity>> getNotAvailableCourts(String schedule, String date,Application application){
        return ((BaseApp)application).getDatabase().reservationDao().getNotAvailableCourt(schedule,date);
    }

    public boolean getUnavailableCourtTime(final String schedule, final String date, Application application){
        return ((BaseApp)application).getDatabase().reservationDao().getUnavailableCourtTime(schedule, date);
    }

    public boolean getUnavailableCourts(final String schedule, final String date, final int courtNumber,Application application){
        return ((BaseApp)application).getDatabase().reservationDao().getUnavailableCourts(schedule, date, courtNumber);
    }*/

    public void insert(final ReservationEntity reservationEntity, OnAsyncEventListener callback){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("players")
                .child(reservationEntity.getPlayerEmail())
                .child("reservations");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("players")
                .child(reservationEntity.getPlayerEmail())
                .child("reservations")
                .child(key)
                .setValue(reservationEntity, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final ReservationEntity reservationEntity, OnAsyncEventListener callback){
        FirebaseDatabase.getInstance()
                .getReference("players")
                .child(reservationEntity.getPlayerEmail())
                .child("reservations")
                .child(reservationEntity.getIdReservation())
                .updateChildren(reservationEntity.toMapByDate(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
        FirebaseDatabase.getInstance()
                .getReference("players")
                .child(reservationEntity.getPlayerEmail())
                .child("reservations")
                .child(reservationEntity.getIdReservation())
                .updateChildren(reservationEntity.toMapByUser(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final ReservationEntity reservationEntity, OnAsyncEventListener callback){
        FirebaseDatabase.getInstance()
                .getReference("players")
                .child(reservationEntity.getPlayerEmail())
                .child("reservations")
                .child(reservationEntity.getIdReservation())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

}

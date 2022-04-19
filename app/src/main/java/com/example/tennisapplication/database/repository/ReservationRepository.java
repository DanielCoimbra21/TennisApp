package com.example.tennisapplication.database.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.database.entity.ReservationEntity;
import com.example.tennisapplication.database.firebase.ReservationListLiveData;
import com.example.tennisapplication.database.firebase.ReservationLiveData;
import com.example.tennisapplication.database.firebase.UnavailableReservationListLiveData;
import com.example.tennisapplication.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ReservationRepository {

    private static ReservationRepository instance;
    private DatabaseReference mDatabase;
    private boolean isCourtAvailable;

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
                .getReference("reservations")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(reservationId);
        return new ReservationLiveData(reference);
    }

    public LiveData<List<ReservationEntity>> getByPlayerId(final String playerId){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("reservations")
                .child(playerId);
        return new ReservationListLiveData(reference, playerId);    }



    public LiveData<List<ReservationEntity>> getAllReservations() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        isCourtAvailable = true;
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("reservations");
        return new UnavailableReservationListLiveData(reference);
    }

    public void insert(final ReservationEntity reservationEntity, OnAsyncEventListener callback){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("reservations")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("reservations")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(key)
                .setValue(reservationEntity.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final ReservationEntity reservationEntity, OnAsyncEventListener callback){
        FirebaseDatabase.getInstance()
                .getReference("reservations")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
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

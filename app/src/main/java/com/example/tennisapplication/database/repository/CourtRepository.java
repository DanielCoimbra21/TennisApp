package com.example.tennisapplication.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import com.example.tennisapplication.database.entity.CourtEntity;
import com.example.tennisapplication.database.entity.ReservationEntity;
import com.example.tennisapplication.database.firebase.CourtLiveData;
import com.example.tennisapplication.database.firebase.ReservationLiveData;
import com.example.tennisapplication.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CourtRepository {

    private static CourtRepository instance;

    public static CourtRepository getInstance() {
        if (instance == null){
            synchronized (ReservationRepository.class) {
                if (instance == null){
                    instance = new CourtRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<CourtEntity> getCourtByCourtNum(int courtNum){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("courts")
                .child(String.valueOf(courtNum));
        return new CourtLiveData(reference);
    }

    public LiveData<ReservationEntity> getReservation(final String reservationId){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("players")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("reservations")
                .child(reservationId);
        return new ReservationLiveData(reference);
    }

    public void insert(final CourtEntity court, final OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("courts")
                .child(String.valueOf(court.getCourtNum()));
        FirebaseDatabase.getInstance()
                .getReference("courts")
                .child(String.valueOf(court.getCourtNum()))
                .setValue(court.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

}

package com.example.tennisapplication.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import com.example.tennisapplication.database.entity.CourtEntity;
import com.example.tennisapplication.database.firebase.CourtLiveData;
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

    public LiveData<CourtEntity> getCourt(final String courtId){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("players")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("courts");
        return new CourtLiveData(reference);
    }

    public void insert(final CourtEntity court, final OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("courts")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Openair");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("players")
                .child(court.getIdCourt())
                .child("courts")
                .child(key)
                .setValue(court, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final CourtEntity court, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("players")
                .child(court.getIdCourt())
                .child("courts")
                .child(court.getIdCourt())
                .updateChildren(court.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

}

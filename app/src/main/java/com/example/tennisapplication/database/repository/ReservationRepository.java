package com.example.tennisapplication.database.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.database.entity.ReservationEntity;
import com.example.tennisapplication.database.firebase.ReservationListLiveData;
import com.example.tennisapplication.database.firebase.ReservationLiveData;
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



    public boolean getNotAvailableCourts(String schedule, String date, int courtNum) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        isCourtAvailable = true;
        // check if the schedule exists
        mDatabase.child("reservations").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot propertySnapshot : entrySnapshot.getChildren()) {
                        if(getCourtNum(String.valueOf(propertySnapshot.getValue())).equals(String.valueOf(courtNum))){
                            if(getDate(String.valueOf(propertySnapshot.getValue())).equals(date)) {
                                if (getSchedule(String.valueOf(propertySnapshot.getValue())).equals(schedule)) {
                                    isCourtAvailable = false;
                                    System.out.println("COURT UNAVAILABLE no° "+ getCourtNum(String.valueOf(propertySnapshot.getValue() + " isAvailable "+ isCourtAvailable)));
                                    System.out.println("isCourtAvailable : " + isCourtAvailable);
                                    return;
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getMessage());
            }
        });
        return isCourtAvailable;
    }

    public String getCourtNum(String propertyValue){
        return propertyValue.substring(10,11);
    }

    public String getDate(String propertyValue){
        String date = propertyValue;
        if(propertyValue.substring(28,29).equals(",")){
            date = date.substring(18,28);
        }
        if(propertyValue.substring(27,28).equals(",")){
            date = date.substring(18,27);
        }
        if(propertyValue.substring(26,27).equals(",")){
            date = date.substring(18,26);
        }
        return date;
    }

    public String getSchedule(String propertyValue){
        String schedule = propertyValue;
        if(propertyValue.substring(28,29).equals(",")){
            schedule = schedule.substring(39,41);
        }
        if(propertyValue.substring(27,28).equals(",")){
            schedule = schedule.substring(38,40);
        }
        if(propertyValue.substring(26,27).equals(",")){
            schedule = schedule.substring(37,39);
        }
        if (schedule.contains("}")){
            schedule = schedule.substring(0,1);
        }
        return schedule;
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

package com.example.tennisapplication.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.tennisapplication.database.entity.ReservationEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReservationListLiveData extends LiveData<List<ReservationEntity>> {


    private static final String TAG = "AccountListLiveData";

    private final DatabaseReference reference;
    private final String owner;
    private final MyValueEventListener listener = new MyValueEventListener();

    public ReservationListLiveData(DatabaseReference ref, String owner) {
        reference = ref;
        this.owner = owner;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toAccounts(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<ReservationEntity> toAccounts(DataSnapshot snapshot) {
        List<ReservationEntity> reservations = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            ReservationEntity entity = childSnapshot.getValue(ReservationEntity.class);
            entity.setIdReservation(childSnapshot.getKey());
            reservations.add(entity);
        }
        return reservations;
    }



}

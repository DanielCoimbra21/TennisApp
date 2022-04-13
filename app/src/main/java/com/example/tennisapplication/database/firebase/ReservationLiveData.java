package com.example.tennisapplication.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.tennisapplication.database.entity.ReservationEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ReservationLiveData extends LiveData<ReservationEntity> {

    private static final String TAG = "ReservationLiveData";

    private final DatabaseReference reference;
    private final String owner;
    private final ReservationLiveData.MyValueEventListener listener = new ReservationLiveData.MyValueEventListener();

    public ReservationLiveData(DatabaseReference ref) {
        reference = ref;
        owner = ref.getParent().getParent().getKey();
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
            ReservationEntity entity = dataSnapshot.getValue(ReservationEntity.class);
            entity.setIdReservation(dataSnapshot.getKey());
            entity.setPlayerEmail(owner);
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

}

package com.example.tennisapplication.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.tennisapplication.database.entity.CourtEntity;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class CourtLiveData extends LiveData<CourtEntity> {

    private static final String TAG = "CourtLiveData";

    private final DatabaseReference reference;
    private final CourtLiveData.MyValueEventListener listener = new CourtLiveData.MyValueEventListener();

    public CourtLiveData(DatabaseReference ref) {
        this.reference = ref;
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
            CourtEntity entity = dataSnapshot.getValue(CourtEntity.class);
            entity.setIdCourt(dataSnapshot.getKey());
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}

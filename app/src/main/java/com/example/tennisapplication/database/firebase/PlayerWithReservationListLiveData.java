package com.example.tennisapplication.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.database.entity.ReservationEntity;
import com.example.tennisapplication.database.pojo.PlayerWithReservation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlayerWithReservationListLiveData extends LiveData<List<PlayerWithReservation>>{

    private static final String TAG = "ClientAccountsLiveData";

    private final DatabaseReference reference;
    private final String owner;
    private final PlayerWithReservationListLiveData.MyValueEventListener listener =
            new PlayerWithReservationListLiveData.MyValueEventListener();

    public PlayerWithReservationListLiveData(DatabaseReference ref, String owner) {
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
            setValue(toClientWithAccountsList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<PlayerWithReservation> toClientWithAccountsList(DataSnapshot snapshot) {
        List<PlayerWithReservation> clientWithAccountsList = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            if (!childSnapshot.getKey().equals(owner)) {
                PlayerWithReservation playerWithReservation = new PlayerWithReservation();
                playerWithReservation.player = childSnapshot.getValue(PlayerEntity.class);
                playerWithReservation.player.setIdPlayer(childSnapshot.getKey());
                playerWithReservation.reservations = toReservations(childSnapshot.child("reservations"),
                        childSnapshot.getKey());
                clientWithAccountsList.add(playerWithReservation);
            }
        }
        return clientWithAccountsList;
    }

    private List<ReservationEntity> toReservations(DataSnapshot snapshot, String ownerId) {
        List<ReservationEntity> accounts = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            ReservationEntity entity = childSnapshot.getValue(ReservationEntity.class);
            entity.setIdReservation(childSnapshot.getKey());
            accounts.add(entity);
        }
        return accounts;
    }


}


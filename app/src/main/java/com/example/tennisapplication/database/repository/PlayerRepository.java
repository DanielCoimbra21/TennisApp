package com.example.tennisapplication.database.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.database.firebase.PlayerLiveData;
import com.example.tennisapplication.database.firebase.PlayerWithReservationListLiveData;
import com.example.tennisapplication.database.pojo.PlayerWithReservation;
import com.example.tennisapplication.util.OnAsyncEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PlayerRepository {

    private static final String TAG = "PlayerRepository";

    private static PlayerRepository instance;

    private PlayerRepository(){}

    public static PlayerRepository getInstance() {
        if (instance == null){
            synchronized (ReservationRepository.class) {
                if (instance == null){
                    instance = new PlayerRepository();
                }
            }
        }
        return instance;
    }

    public void signIn(final String email, final String password,
                       final OnCompleteListener<AuthResult> listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }



    public LiveData<PlayerEntity> getPlayer(final String playerId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("players")
                .child(playerId);
        return new PlayerLiveData(reference);
    }

    public LiveData<List<PlayerWithReservation>> getOtherPlayerWithReservations(final String email) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("players");
        return new PlayerWithReservationListLiveData(reference, email);
    }

    public void register(final PlayerEntity client, final OnAsyncEventListener callback) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                client.getEmail(),
                client.getPassword()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                client.setIdPlayer(FirebaseAuth.getInstance().getCurrentUser().getUid());
                insert(client, callback);
            } else {
                callback.onFailure(task.getException());
            }
        });
    }

//    public void register(final PlayerEntity client, final OnAsyncEventListener callback) {
//        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
//                client.getEmail(),
//                client.getPassword()
//        ).addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                System.out.println("SUCCESS AT REGISTER METHOD PLAYERREPOSITORY");
//                client.setIdPlayer(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                insert(client, callback);
//            } else {
//                System.out.println("FAILURE AT REGISTER METHOD PLAYERREPOSITORY");
//                System.out.println("Exception : " + task.getException());
//                callback.onFailure(task.getException());
//            }
//        });
//    }

    public void insert(final PlayerEntity playerEntity, OnAsyncEventListener callback){
        FirebaseDatabase.getInstance()
                .getReference("players")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(playerEntity, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                        FirebaseAuth.getInstance().getCurrentUser().delete()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        callback.onFailure(null);
                                        Log.d(TAG, "Rollback successful: User account deleted");
                                    } else {
                                        callback.onFailure(task.getException());
                                        Log.d(TAG, "Rollback failed: signInWithEmail:failure",
                                                task.getException());
                                    }
                                });
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final PlayerEntity playerEntity, OnAsyncEventListener callback){
        FirebaseDatabase.getInstance()
                .getReference("clients")
                .child(playerEntity.getIdPlayer())
                .updateChildren(playerEntity.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
        FirebaseAuth.getInstance().getCurrentUser().updatePassword(playerEntity.getPassword())
                .addOnFailureListener(
                        e -> Log.d(TAG, "updatePassword failure!", e)
                );
    }

    public void delete(final PlayerEntity playerEntity, OnAsyncEventListener callback){
        FirebaseDatabase.getInstance()
                .getReference("clients")
                .child(playerEntity.getEmail())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }


}

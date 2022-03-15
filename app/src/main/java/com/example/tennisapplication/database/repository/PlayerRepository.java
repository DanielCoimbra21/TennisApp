package com.example.tennisapplication.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.database.async.player.CreatePlayer;
import com.example.tennisapplication.database.async.player.DeletePlayer;
import com.example.tennisapplication.database.async.player.UpdatePlayer;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.util.OnAsyncEventListener;

public class PlayerRepository {

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

    public LiveData<PlayerEntity> getPlayer(final String email, Application application){
        return ((BaseApp)application).getDatabase().playerDao().getByEmail(email);
    }

    public void insert(final PlayerEntity playerEntity, OnAsyncEventListener callback, Application application){
        new CreatePlayer(application, callback).execute(playerEntity);
    }

    public void update(final PlayerEntity playerEntity, OnAsyncEventListener callback, Application application){
        new UpdatePlayer(application, callback).execute(playerEntity);
    }

    public void delete(final PlayerEntity playerEntity, OnAsyncEventListener callback, Application application){
        new DeletePlayer(application, callback).execute(playerEntity);
    }


}

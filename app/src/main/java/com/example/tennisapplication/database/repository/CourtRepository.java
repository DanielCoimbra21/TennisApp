package com.example.tennisapplication.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.database.async.court.CreateCourt;
import com.example.tennisapplication.database.async.court.DeleteCourt;
import com.example.tennisapplication.database.async.court.UpdateCourt;
import com.example.tennisapplication.database.async.player.CreatePlayer;
import com.example.tennisapplication.database.async.player.DeletePlayer;
import com.example.tennisapplication.database.async.player.UpdatePlayer;
import com.example.tennisapplication.database.entity.CourtEntity;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.util.OnAsyncEventListener;

public class CourtRepository {

    private static CourtRepository instance;

    private CourtRepository(){}

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

    public LiveData<CourtEntity> getCourt(final int id, Application application){
        return ((BaseApp)application).getDatabase().courtDao().getCourtById(id);
    }

    public void insert(final CourtEntity courtEntity, OnAsyncEventListener callback, Application application){
        new CreateCourt(application, callback).execute(courtEntity);
    }

    public void update(final CourtEntity courtEntity, OnAsyncEventListener callback, Application application){
        new UpdateCourt(application, callback).execute(courtEntity);
    }

    public void delete(final CourtEntity courtEntity, OnAsyncEventListener callback, Application application){
        new DeleteCourt(application, callback).execute(courtEntity);
    }

}

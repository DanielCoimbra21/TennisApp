package com.example.tennisapplication.database.async.player;

import android.app.Application;
import android.os.AsyncTask;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.util.OnAsyncEventListener;

public class DeletePlayer extends AsyncTask<PlayerEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeletePlayer(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(PlayerEntity... playerEntities) {
        try {
            for (PlayerEntity playerEntity : playerEntities)
                ((BaseApp) application).getDatabase().playerDao().delete(playerEntity);
        }catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        if (callback != null){
            if (exception == null) {
                callback.onSuccess();
            }else {
                callback.onFailure(exception);
            }
        }
    }
}

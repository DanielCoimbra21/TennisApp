package com.example.tennisapplication.database.async.court;

import android.app.Application;
import android.os.AsyncTask;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.database.entity.CourtEntity;
import com.example.tennisapplication.database.entity.ReservationEntity;
import com.example.tennisapplication.util.OnAsyncEventListener;

public class CreateCourt extends AsyncTask<CourtEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateCourt(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;

    }

    @Override
    protected Void doInBackground(CourtEntity... courtEntities) {
        try {
            for (CourtEntity courtEntity : courtEntities)

               ((BaseApp) application).getDatabase().courtDao().insert(courtEntity);
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

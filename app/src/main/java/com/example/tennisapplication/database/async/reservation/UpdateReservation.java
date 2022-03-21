package com.example.tennisapplication.database.async.reservation;

import android.app.Application;
import android.os.AsyncTask;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.database.entity.ReservationEntity;
import com.example.tennisapplication.util.OnAsyncEventListener;

public class UpdateReservation extends AsyncTask<ReservationEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;


    public UpdateReservation(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ReservationEntity... reservationEntities) {
        try {
            for (ReservationEntity reservationEntity : reservationEntities)
                ((BaseApp) application).getDatabase().reservationDao().update(reservationEntity);
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

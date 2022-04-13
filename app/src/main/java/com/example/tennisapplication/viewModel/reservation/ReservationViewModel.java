package com.example.tennisapplication.viewModel.reservation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.database.entity.ReservationEntity;
import com.example.tennisapplication.database.repository.PlayerRepository;
import com.example.tennisapplication.database.repository.ReservationRepository;
import com.example.tennisapplication.util.OnAsyncEventListener;
import com.example.tennisapplication.viewModel.player.PlayerViewModel;

public class ReservationViewModel extends AndroidViewModel {

    private ReservationRepository repository;

    private Application application;

    private final MediatorLiveData<ReservationEntity> observerReservation;

    public ReservationViewModel(@NonNull Application application, final int id, ReservationRepository reservationRepository) {
        super(application);

        this.application = application;

        repository = reservationRepository;

        observerReservation = new MediatorLiveData<>();
        observerReservation.setValue(null);

        LiveData<ReservationEntity> reservation = repository.getReservation(String.valueOf(id));

        observerReservation.addSource(reservation, observerReservation::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;

        private final int id;

        private final ReservationRepository repository;

        public Factory(@NonNull Application application, int id) {
            this.id = id;
            this.application = application;
            repository = ((BaseApp) application).getReservationRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //no inspection unchecked
            return (T) new ReservationViewModel(application, id, repository);
        }
    }

    public LiveData<ReservationEntity> getReservation(){return observerReservation;}

    public void createReservation(ReservationEntity reservationEntity, OnAsyncEventListener callback){
        repository.insert(reservationEntity, callback);
    }

    public void updateReservation(ReservationEntity reservationEntity, OnAsyncEventListener callback){
        repository.update(reservationEntity, callback);
    }

    public void deleteReservation(ReservationEntity reservationEntity, OnAsyncEventListener callback){
        repository.delete(reservationEntity, callback);
    }



}

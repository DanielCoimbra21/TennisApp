package com.example.tennisapplication.viewModel.reservation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.database.entity.ReservationEntity;
import com.example.tennisapplication.database.pojo.PlayerWithReservation;
import com.example.tennisapplication.database.repository.PlayerRepository;
import com.example.tennisapplication.database.repository.ReservationRepository;
import com.example.tennisapplication.util.OnAsyncEventListener;

import java.util.List;

public class ReservationListViewModel extends AndroidViewModel {


    private Application application;

    private ReservationRepository repository;
    private PlayerRepository repositoryP;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<PlayerWithReservation>> observablePlayerReservations;
    private final MediatorLiveData<List<ReservationEntity>> observableReservations;

    public ReservationListViewModel(@NonNull Application application,
                                final String ownerId,
                                PlayerRepository playerRepository,
                                ReservationRepository reservationRepository) {
        super(application);

        this.application = application;

        repository = reservationRepository;
        repositoryP = playerRepository;

        observablePlayerReservations = new MediatorLiveData<>();
        observableReservations = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observablePlayerReservations.setValue(null);
        observableReservations.setValue(null);

        LiveData<List<PlayerWithReservation>> playerReservations = repositoryP.getOtherPlayerWithReservations(ownerId);
        LiveData<List<ReservationEntity>> reservations = repository.getByPlayerEmail(ownerId);

        // observe the changes of the entities from the database and forward them
        observablePlayerReservations.addSource(playerReservations, observablePlayerReservations::setValue);
        observableReservations.addSource(reservations, observableReservations::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String ownerId;

        private final PlayerRepository playerRepository;

        private final ReservationRepository reservationRepository;

        public Factory(@NonNull Application application, String ownerId) {
            this.application = application;
            this.ownerId = ownerId;
            playerRepository = ((BaseApp) application).getPlayerRepository();
            reservationRepository = ((BaseApp) application).getReservationRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ReservationListViewModel(application, ownerId, playerRepository, reservationRepository);
        }
    }

    public LiveData<List<ReservationEntity>> getPlayerReservation(){
        return observableReservations;
    }

    public MediatorLiveData<List<PlayerWithReservation>> getObservablePlayerReservations() {
        return observablePlayerReservations;
    }

    public MediatorLiveData<List<ReservationEntity>> getObservableReservations() {
        return observableReservations;
    }

    public void deleteReservation(ReservationEntity reservation, OnAsyncEventListener callback) {
        repository.delete(reservation, callback);
    }


}

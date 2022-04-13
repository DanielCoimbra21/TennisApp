package com.example.tennisapplication.viewModel.player;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.database.repository.PlayerRepository;
import com.example.tennisapplication.util.OnAsyncEventListener;

public class PlayerViewModel extends AndroidViewModel {

    private PlayerRepository repository;

    private Application application;

    private final MediatorLiveData<PlayerEntity> observerPlayer;

    public PlayerViewModel(@NonNull Application application, final String email, PlayerRepository playerRepository) {
        super(application);

        this.application = application;

        repository = playerRepository;

        observerPlayer = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observerPlayer.setValue(null);

        LiveData<PlayerEntity> player = repository.getPlayer(email);

        // observe the changes of the client entity from the database and forward them
        observerPlayer.addSource(player, observerPlayer::setValue);
    }

    //Creator : inject the email into the ViewModel
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String email;

        private final PlayerRepository repository;

        public Factory(@NonNull Application application, String email) {
            this.email = email;
            this.application = application;
            repository = ((BaseApp) application).getPlayerRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //no inspection unchecked
            return (T) new PlayerViewModel(application, email, repository);
        }

    }

    public LiveData<PlayerEntity> getPlayer() {return observerPlayer;}

    public void createPlayer(PlayerEntity playerEntity, OnAsyncEventListener callback){
        repository.insert(playerEntity, callback);
    }

    public void updatePlayer(PlayerEntity playerEntity, OnAsyncEventListener callback){
        repository.update(playerEntity, callback);
    }

    public void deletePlayer(PlayerEntity playerEntity, OnAsyncEventListener callback){
        repository.delete(playerEntity, callback);
    }
}

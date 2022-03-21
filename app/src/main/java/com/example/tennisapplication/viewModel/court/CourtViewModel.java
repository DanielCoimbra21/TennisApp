package com.example.tennisapplication.viewModel.court;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.tennisapplication.UI.CourtTypeActivity;
import com.example.tennisapplication.database.entity.CourtEntity;
import com.example.tennisapplication.database.entity.ReservationEntity;
import com.example.tennisapplication.database.repository.CourtRepository;
import com.example.tennisapplication.database.repository.ReservationRepository;

public class CourtViewModel extends AndroidViewModel {

    private CourtRepository repository;

    private Application application;

    private final MediatorLiveData<CourtEntity> observerCourt;


    public CourtViewModel(@NonNull Application application, final int id, CourtRepository courtRepository) {
        super(application);

        this.application = application;

        repository = courtRepository;

        observerCourt = new MediatorLiveData<>();
        observerCourt.setValue(null);

        //LiveData<CourtEntity> court = repository.ge

    }
}

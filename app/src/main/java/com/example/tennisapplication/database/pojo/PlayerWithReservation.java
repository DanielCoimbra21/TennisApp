package com.example.tennisapplication.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.database.entity.ReservationEntity;

import java.util.List;

public class PlayerWithReservation {

    @Embedded
    public PlayerEntity player;

    @Relation(parentColumn = "email", entityColumn = "playerEmail", entity = ReservationEntity.class)
    public List<ReservationEntity> reservations;

}

package com.example.tennisapplication.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.database.entity.ReservationEntity;

import java.util.List;

@Dao
public interface ReservationDAO {

   @Query("SELECT * FROM reservation WHERE idReservation = :id")
    LiveData<ReservationEntity> getByReservationId(int id);

    @Query("SELECT * FROM reservation")
    LiveData<List<ReservationEntity>> getAllReservation();



    @Insert
    void insert(ReservationEntity reservationEntity) throws SQLiteConstraintException;

    @Update
    void update(ReservationEntity reservationEntity);

    @Delete
    void delete(ReservationEntity reservationEntity);

    @Query("DELETE FROM reservation")
    void deleteAllReservation();
}

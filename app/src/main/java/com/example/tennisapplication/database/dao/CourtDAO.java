package com.example.tennisapplication.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tennisapplication.database.entity.CourtEntity;
import com.example.tennisapplication.database.entity.ReservationEntity;

@Dao
public interface CourtDAO {

    @Query("SELECT * FROM courts WHERE idCourt = :id")
    LiveData<CourtEntity> getCourtById(int id);

    @Insert
    void insert(CourtEntity courtEntity) throws SQLiteConstraintException;

    @Update
    void update(CourtEntity courtEntity);

    @Delete
    void delete(CourtEntity courtEntity);

    @Query("DELETE FROM courts")
    void deleteAllCourts();


}

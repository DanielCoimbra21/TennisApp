package com.example.tennisapplication.database.dao;


import android.database.sqlite.SQLiteConstraintException;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tennisapplication.database.entity.PlayerEntity;

import java.util.List;

@Dao
public interface PlayerDAO {

    @Query("SELECT * FROM player WHERE email = :email")
    LiveData<PlayerEntity> getByEmail(String email);

    @Query("SELECT * FROM player")
    LiveData<List<PlayerEntity>> getAll();

    @Query("SELECT * FROM player WHERE idPlayer = :id")
    LiveData<PlayerEntity> getById(int id);


    @Insert
    void insert(PlayerEntity playerEntity) throws SQLiteConstraintException;

    @Update
    void update(PlayerEntity playerEntity);

    @Delete
    void delete(PlayerEntity playerEntity);

    @Query("DELETE FROM player")
    void deleteAll();
}

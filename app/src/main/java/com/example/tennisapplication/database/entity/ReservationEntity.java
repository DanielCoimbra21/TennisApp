package com.example.tennisapplication.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.util.Date;

@Entity(tableName = "reservation")
/*        ,foreignKeys =
        @ForeignKey(
                entity = PlayerEntity.class,
                parentColumns = "idPlayer",
                childColumns = "idPlayer",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
        @Index(
                value = {"player"}
        )
        }
)*/
public class ReservationEntity {

    @PrimaryKey(autoGenerate = true)
    private Long idReservation;

    @ColumnInfo(name = "schedule")
    private String schedule;

    @ColumnInfo(name = "date")
    private String date;

    public ReservationEntity() {}

    public ReservationEntity(String schedule, String date) {
        this.schedule = schedule;
        this.date = date;
    }

    public Long getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(Long idReservation) {
        this.idReservation = idReservation;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof ReservationEntity)) return false;
        ReservationEntity o = (ReservationEntity) obj;
        return o.getIdReservation().equals(this.getIdReservation());
    }
}

package com.example.tennisapplication.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.util.Date;

/*@Entity(tableName = "reservation",
        foreignKeys =
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
@Entity
public class ReservationEntity {

    @PrimaryKey(autoGenerate = true)
    private Long idReservation;

    @ColumnInfo(name = "schedule")
    private Time schedule;

    @ColumnInfo(name = "date")
    private Date date;

    public ReservationEntity() {}

    public ReservationEntity(Time schedule, Date date) {
        this.schedule = schedule;
        this.date = date;
    }

    public Long getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(Long idReservation) {
        this.idReservation = idReservation;
    }

    public Time getSchedule() {
        return schedule;
    }

    public void setSchedule(Time schedule) {
        this.schedule = schedule;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

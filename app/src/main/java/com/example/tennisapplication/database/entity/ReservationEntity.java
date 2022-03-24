package com.example.tennisapplication.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/*@Entity(tableName = "reservation"
        ,foreignKeys =
        @ForeignKey(
                entity=PlayerEntity.class,
                parentColumns = "email",
                childColumns = "playerEmail",
                onDelete = ForeignKey.CASCADE

        ),
        indices = {
        @Index(
                value = {"player"}
        )
        }
)
@Entity(tableName = "courts"
        ,foreignKeys =
@ForeignKey(
        entity=CourtEntity.class,
        parentColumns = "idCourt",
        childColumns = "courtNumber",
        onDelete = ForeignKey.CASCADE

),
        indices = {
                @Index(
                        value = {"court"}
                )
        }
)*/

@Entity(tableName = "reservation" ,foreignKeys = {
        @ForeignKey(
                entity=PlayerEntity.class,
                parentColumns = "email",
                childColumns = "playerEmail",
                onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity=CourtEntity.class,
                parentColumns = "idCourt",
                childColumns = "courtNumber",
                onDelete = ForeignKey.CASCADE
        )

}
)

public class ReservationEntity {

    @PrimaryKey(autoGenerate = true)
    private Long idReservation;

    @ColumnInfo(name = "schedule")
    private String schedule;

    @ColumnInfo(name = "date")
    private String date;


    private String playerEmail;


    private int courtNumber;

    public ReservationEntity() {}

    public ReservationEntity(String schedule, String date, String playerId, int courtNumber) {
        this.schedule = schedule;
        this.date = date;
        this.playerEmail = playerId;
        this.courtNumber = courtNumber;
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

    public String getPlayerEmail() {
        return playerEmail;
    }

    public void setPlayerEmail(String playerEmail) {
        this.playerEmail = playerEmail;
    }

    public int getCourtNumber() {
        return courtNumber;
    }

    public void setCourtNumber(int courtNumber) {
        this.courtNumber = courtNumber;
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

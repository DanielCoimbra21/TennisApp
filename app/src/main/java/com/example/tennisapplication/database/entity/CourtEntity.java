package com.example.tennisapplication.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/*@Entity(tableName = "reservation",
        foreignKeys =
        @ForeignKey(
                entity = ReservationEntity.class,
                parentColumns = "idReservation",
                childColumns = "idReservation",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
        @Index(
                value = {"reservation"}
        )}
)*/
@Entity
public class CourtEntity{

    @PrimaryKey(autoGenerate = true)
    private Long idCourt;

    @ColumnInfo(name = "openAir")
    private boolean openAir;

    @ColumnInfo(name = "reserved")
    private boolean reserved;


    public CourtEntity(){}

    public CourtEntity(boolean openAir, boolean reserved){
        this.openAir = openAir;
        this.reserved = reserved;
    }

    public Long getIdCourt() {
        return idCourt;
    }

    public void setIdCourt(Long idCourt) {
        this.idCourt = idCourt;
    }

    public boolean isOpenAir() {
        return openAir;
    }

    public void setOpenAir(boolean openAir) {
        this.openAir = openAir;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof CourtEntity)) return false;
        CourtEntity o = (CourtEntity) obj;
        return o.getIdCourt().equals(this.getIdCourt());
    }

}

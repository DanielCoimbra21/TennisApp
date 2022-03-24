package com.example.tennisapplication.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "courts")
public class CourtEntity implements Comparable{

    @PrimaryKey
    private int idCourt;

    @ColumnInfo(name = "openAir")
    private boolean openAir;

    @ColumnInfo(name = "reserved")
    private boolean reserved;


    public CourtEntity(){}

    public CourtEntity(int idCourt,boolean openAir, boolean reserved){
        this.openAir = openAir;
        this.reserved = reserved;
        this.idCourt = idCourt;
    }


    public int getIdCourt() {
        return idCourt;
    }

    public void setIdCourt(int idCourt) {
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
        if(o.getIdCourt() == this.getIdCourt()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public int compareTo(Object o) {
        return toString().compareTo(o.toString());
    }
}

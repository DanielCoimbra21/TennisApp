package com.example.tennisapplication.database.entity;


import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ReservationEntity {

    private String idReservation;

    private String schedule;

    private String date;

    private int courtNum;

    public ReservationEntity() {}

    public ReservationEntity(String schedule, String date, int courtNum) {
        this.schedule = schedule;
        this.date = date;
        this.courtNum = courtNum;
    }

    public String getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(String idReservation) {
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

    public int getCourtNum() {
        return courtNum;
    }

    public void setCourtNum(int courtNum) {
        this.courtNum = courtNum;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof ReservationEntity)) return false;
        ReservationEntity o = (ReservationEntity) obj;
        return o.getIdReservation().equals(this.getIdReservation());
    }

    // va regarder si le user a déjà réservé à cette date la
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("date", date);
        result.put("schedule", schedule);
        result.put("courtNum", courtNum);
        return result;
    }


}

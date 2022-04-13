package com.example.tennisapplication.database.entity;


import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ReservationEntity {

    private String idReservation;

    private String schedule;

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

    @Exclude
    public Map<String, Object> toMapByUser() {
        HashMap<String, Object> result = new HashMap<>();
        String dateTime = date + "/" + schedule;
        result.put(dateTime, courtNumber);
        return result;
    }

    @Exclude
    public Map<String, Object> toMapByDate() {
        HashMap<String, Object> result = new HashMap<>();
        result.put(playerEmail, courtNumber);
        return result;
    }

}

package com.example.tennisapplication.database.repository;

public class ReservationRepository {

    private static ReservationRepository instance;

    private ReservationRepository(){}

    public static ReservationRepository getInstance() {
        if (instance == null){
            synchronized (ReservationRepository.class) {
                if (instance == null){
                    instance = new ReservationRepository();
                }
            }
        }
        return instance;
    }

}
